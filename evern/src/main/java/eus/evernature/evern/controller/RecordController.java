package eus.evernature.evern.controller;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import eus.evernature.evern.models.Record;
import eus.evernature.evern.models.json_responses.RecordsPerHour;
import eus.evernature.evern.service.record.RecordService;

@RestController
@RequestMapping("/api/record")
public class RecordController {

    @Autowired
    RecordService recordService;

    @Autowired
    ObjectMapper mapper;

    @GetMapping
    public ResponseEntity<Record> getRecord(@RequestBody Integer id) {
        return ResponseEntity.ok(recordService.getRecord(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Record>> getRecords() {
        return ResponseEntity.ok(recordService.getRecords());
    }

    @PostMapping("/save")
    public ResponseEntity<Record> saveRecord(@RequestBody Record newRecord) {
        URI uri = URI
                .create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/record/save").toUriString());

        newRecord = recordService.saveRecord(newRecord);

        if(newRecord == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.created(uri).body(newRecord);
    }

    @GetMapping("/today")
    public ResponseEntity<List<RecordsPerHour>> getRecordsPerHour() {

        List<RecordsPerHour> records = recordService.getRecordsPerHour();

        return ResponseEntity.ok().body(records);
    }
}
