package eus.evernature.evern.service.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import eus.evernature.evern.models.Animal;
import eus.evernature.evern.models.Camera;
import eus.evernature.evern.models.Prediction;
import eus.evernature.evern.models.Record;
import eus.evernature.evern.models.DTO.PredictionDTO;
import eus.evernature.evern.models.DTO.RecordDTO;
import eus.evernature.evern.models.json_responses.RecordsPerHour;
import eus.evernature.evern.repository.RecordRepository;
import eus.evernature.evern.service.animal.AnimalService;
import eus.evernature.evern.service.camera.CameraService;
import eus.evernature.evern.service.image.ImageService;
import eus.evernature.evern.service.prediction.PredictionService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@Transactional
@Slf4j
public class RecordServiceImpl implements RecordService {

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    CameraService cameraService;

    @Autowired
    AnimalService animalService;

    @Autowired
    PredictionService predictionService;

    @Override
    public Record saveRecord(Record record) {
        log.info("Saving record into database");

        try {
            record = recordRepository.save(record);
        } catch (Exception e) {
            log.error("Could not save record in database: {}", e.getMessage());
            return null;
        }

        return record;
    }

    @Override
    public void addPredictionToRecord(Integer recordId, Prediction prediction) {
        log.info("Adding prediction to record: {}", recordId);
        Record record = recordRepository.getById(recordId);
        record.getPredictions().add(prediction);
        recordRepository.save(record);
    }

    @Override
    public Record getRecord(Integer recordId) {
        log.info("Loaging record {} from database", recordId);
        Record record = recordRepository.getById(recordId);

        if (record == null) {
            log.error("Record not found in the database instance");
            throw new UsernameNotFoundException("Record not found in the database instance");
        } else {
            log.error("Record found in the database instance");
        }

        return record;
    }

    @Override
    public List<Record> getRecords() {
        log.info("Loading all the records from database");
        return recordRepository.findAll();
    }

    @Override
    public Page<Record> findPaginated(int page, int size) {
        return recordRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public List<RecordsPerHour> getRecordsPerHour() {
        List<Record> records = recordRepository.findAll();

        List<Record> todayRecords = records.stream().filter(r -> checkIfDateIsToday(r)).collect(Collectors.toList());

        List<RecordsPerHour> recordsPerHours = getRecordsPerHourList(todayRecords);

        return recordsPerHours;
    }

    private List<RecordsPerHour> getRecordsPerHourList(List<Record> todayRecords) {
        List<RecordsPerHour> recordsPerHours = new ArrayList<>();
        Map<Integer, Integer> hourRecordMap = new HashMap<>();
        DateTime recordDate;
        Integer hour;

        for (Record r : todayRecords) {
            recordDate = new DateTime(r.getRecordDate());
            hour = recordDate.getHourOfDay();

            if (hourRecordMap.get(hour) != null) {
                int ammount = hourRecordMap.get(hour);
                hourRecordMap.put(hour, ++ammount);
            }
        }

        for (int i = 0; i < 12; i++) {
            RecordsPerHour rph = new RecordsPerHour();
            Integer numImages = hourRecordMap.get(i);

            rph.setHora(i);
            rph.setNumImagenes(numImages == null ? 0 : numImages);
            recordsPerHours.add(rph);
        }

        return recordsPerHours;
    }

    private boolean checkIfDateIsToday(Record record) {
        DateTime now = new DateTime();

        DateTime recordDate = new DateTime(record.getRecordDate());

        if (now.getYear() == recordDate.getYear() && now.getMonthOfYear() == recordDate.getMonthOfYear()
                && now.getDayOfMonth() == recordDate.getDayOfMonth()) {
            return true;
        }

        return false;

    }

    public Record convertDtoToDataClass(RecordDTO recordDto) {

        Record record = new Record();

        Optional<Camera> camera = cameraService.getCameraById(recordDto.getCameraId());

        if (camera.isPresent()) {
            record.setCamera(camera.get());
        }

        String fullImgPath = imageService.saveImage(recordDto.getImage());

        record.setImgPath(fullImgPath);

        return record;
    }

    @Override
    public boolean createNewRecord(String json) {
        RecordDTO recordDto = new Gson().fromJson(json, RecordDTO.class);

        Record record = convertDtoToDataClass(recordDto);

        record = mapAndAddPredictionsToRecord(record, recordDto);

        saveRecord(record);

        return true;
    }

    private Record mapAndAddPredictionsToRecord(Record record, RecordDTO recordDto) {
        List<Prediction> predictions = new ArrayList<>();

        for (PredictionDTO predictionDto : recordDto.getPredictions()) {
            Prediction prediction = convertToPredictionClass(predictionDto);

            prediction = predictionService.savePrediction(prediction);

            predictions.add(prediction);
        }

        record.setPredictions(predictions);

        return record;
    }

    private Prediction convertToPredictionClass(PredictionDTO predictionDto) {
        Prediction prediction = new Prediction();

        Float confidence = predictionDto.getConfidence();
        String message = predictionDto.getMessage();
        boolean isPredicted = predictionDto.getIsPredicted();

        Optional<Animal> animal = animalService.getAnimalByName(predictionDto.getDetectedAnimal());

        if (animal.isPresent()) {
            prediction.setDetectedAnimal(animal.get());
        }

        prediction.setConfidence(confidence);
        prediction.setMessage(message);
        prediction.setIsPredicted(isPredicted);
        prediction.setPredictedImagePath(imageService.saveImage(predictionDto.getImage()));

        return prediction;
    }
}
