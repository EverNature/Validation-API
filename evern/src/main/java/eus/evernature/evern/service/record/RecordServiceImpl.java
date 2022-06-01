package eus.evernature.evern.service.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eus.evernature.evern.models.Prediction;
import eus.evernature.evern.models.Record;
import eus.evernature.evern.models.json_responses.RecordsPerHour;
import eus.evernature.evern.repository.RecordRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@Transactional
@Slf4j
public class RecordServiceImpl implements RecordService {

    @Autowired
    RecordRepository recordRepository;

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
}
