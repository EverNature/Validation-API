package eus.evernature.evern.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import eus.evernature.evern.models.Record;
import eus.evernature.evern.service.record.RecordService;

@RequestMapping("/api")
public class RecordController {
    
    @Autowired
    RecordService recordService;


    @GetMapping("/record")
    public ResponseEntity<Record> getRecord(@RequestBody Integer id) {
        return ResponseEntity.ok(recordService.getRecord(id));
    }

    @PostMapping("/record/save")
    public ResponseEntity<Record> saveRecord(@RequestBody Record record) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/record/save").toUriString());
        return ResponseEntity.created(uri).body(recordService.saveRecord(record));
    }


}
