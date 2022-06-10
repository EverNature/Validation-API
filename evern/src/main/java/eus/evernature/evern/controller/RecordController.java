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

    /** 
     * Esta función devuelve un response con el resultado de la consulta de los registros de la tabla record.
     * @param id
     * @return ResponseEntity<Record>
     */
    @GetMapping
    public ResponseEntity<Record> getRecord(@RequestBody Integer id) {
        return ResponseEntity.ok(recordService.getRecord(id));
    }

    /** 
     * @return ResponseEntity<Record>
     */
    // @GetMapping("/list")
    // public ResponseEntity<List<Record>> getRecords() {
    //     return ResponseEntity.ok(recordService.getRecords());
    // }


    /**
     * This function is used to save a record to the database
     * 
     * @param newRecord the record that is being saved
     * @return A ResponseEntity object is being returned.
     */
    @PostMapping("/save")
    public ResponseEntity<Record> saveRecord(@RequestBody String newRecordJson) {
        URI uri = URI
                .create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/record/save").toUriString());

        boolean created = recordService.createNewRecord(newRecordJson);

        if (!created) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.created(uri).build();
    }

    /** 
     * Esta función devuelve un response las imagenes realidas por las camaras por horas.
     * @return ResponseEntity<List<RecordsPerHour>>
     */
    @GetMapping("/today")
    public ResponseEntity<List<RecordsPerHour>> getRecordsPerHour() {

        List<RecordsPerHour> records = recordService.getRecordsPerHour();

        return ResponseEntity.ok().body(records);
    }

    /** 
     * Esta función devuelve un response las imagenes realidas por las camaras por horas a lo largo de todo el tiempo.
     * @return ResponseEntity<List<RecordsPerHour>>
     */
    @GetMapping("/history")
    public ResponseEntity<List<RecordsPerHour>> getRecordsHistory() {

        List<RecordsPerHour> records = recordService.getRecordsPerHourHistory();

        return ResponseEntity.ok().body(records);
    }
}
