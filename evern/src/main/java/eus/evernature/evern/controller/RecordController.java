package eus.evernature.evern.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import eus.evernature.evern.models.Record;
import eus.evernature.evern.models.JsonResponses.RecordsPerHour;
import eus.evernature.evern.service.record.RecordService;

@RestController
@RequestMapping("/api/record")
public class RecordController {

    @Autowired
    RecordService recordService;

    @GetMapping
    public ResponseEntity<Record> getRecord(@RequestBody Integer id) {
        return ResponseEntity.ok(recordService.getRecord(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Record>> getRecords() {
        return ResponseEntity.ok(recordService.getRecords());
    }

    @PostMapping("/save")
    public ResponseEntity<Record> saveRecord(@RequestBody Record record) {
        URI uri = URI
                .create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/record/save").toUriString());

        record = recordService.saveRecord(record);

        if(record == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.created(uri).body(record);
    }

    @GetMapping("/today")
    public ResponseEntity<List<RecordsPerHour>> getRecordsPerHour() {

        List<RecordsPerHour> records = recordService.getRecordsPerHour();

        return ResponseEntity.ok().body(records);
    }
}
