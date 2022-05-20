package eus.evernature.evern.service.record;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eus.evernature.evern.models.Prediction;
import eus.evernature.evern.models.Record;
import eus.evernature.evern.repository.PredictionRepository;
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

    @Autowired
    PredictionRepository PredictionRepository;
    
    @Override
    public Record saveRecord(Record record) {
        log.info("Saving record into database");

        try {
            record =  recordRepository.save(record);
        } catch (Exception e) {
            log.error("Record not found in database with error: {}", e.getMessage());
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
        
        if(record == null) {
            log.error("Record not found in the database instance");
            throw new UsernameNotFoundException("Record not found in the database instance");
        }

        log.error("Record found in the database instance");

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
}
