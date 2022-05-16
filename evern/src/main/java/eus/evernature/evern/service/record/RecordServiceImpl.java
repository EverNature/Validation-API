package eus.evernature.evern.service.record;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
        return recordRepository.save(record);
    }

    @Override
    public void addPredictionToRecord(Integer recordId, Prediction prediction) {
        Record record = recordRepository.getById(recordId);
        record.getPredictions().add(prediction);
        recordRepository.save(record);
    }

    @Override
    public Record getRecord(Integer recordId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Record> getRecords() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
