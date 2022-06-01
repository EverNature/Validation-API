package eus.evernature.evern.service.record;

import java.util.List;

import org.springframework.data.domain.Page;

import eus.evernature.evern.models.Prediction;
import eus.evernature.evern.models.Record;
import eus.evernature.evern.models.json_responses.RecordsPerHour;

public interface RecordService {
    Record saveRecord(Record record);
    void addPredictionToRecord(Integer recordId, Prediction prediction);
    Record getRecord(Integer recordId);
    List<Record> getRecords();
    Page<Record> findPaginated(int page, int size);
    List<RecordsPerHour> getRecordsPerHour();
}
