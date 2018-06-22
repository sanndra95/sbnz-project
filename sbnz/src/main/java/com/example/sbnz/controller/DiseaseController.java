package com.example.sbnz.controller;

import com.example.sbnz.model.*;
import com.example.sbnz.service.DiseaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequestMapping(value = "/api/disease")
@CrossOrigin
public class DiseaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DiseaseService diseaseService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody Disease disease) {
        logger.info("> disease: {} {} {}", disease.getName(), disease.getDiseaseGroup(), disease.getSymptoms());

        disease.setDeleted(false);

        Disease d = diseaseService.create(disease);

        logger.info("> create disease: {}", d.getName());
        return new ResponseEntity<>(d, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        logger.info("> deleting disease with id: {} ", id);

        Disease disease = diseaseService.delete(id);
        logger.info("> deleted: {}", disease.getName());
        return new ResponseEntity<>(disease, HttpStatus.OK);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<?> getAll() {
        logger.info("> getting all diseases");
        Collection<Disease> diseases = diseaseService.getAll();
        logger.info("size: {}", diseases.size());
        return new ResponseEntity<>(diseases, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<?> getDisease(@PathVariable Long id) {
        Disease disease = diseaseService.findById(id);
        logger.info("getting disease: {}", disease.getName());
        return new ResponseEntity<>(disease, HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Disease disease) {
        Disease d = diseaseService.findById(disease.getId());
        logger.info("> updating disease: {}", d.getName());
        d.setName(disease.getName());
        d.setDiseaseGroup(disease.getDiseaseGroup());
        d.setSymptoms(disease.getSymptoms());
        Disease updated = diseaseService.create(d);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
