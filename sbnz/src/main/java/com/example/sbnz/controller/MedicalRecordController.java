package com.example.sbnz.controller;

import com.example.sbnz.model.MedicalRecord;
import com.example.sbnz.model.Patient;
import com.example.sbnz.service.MedicalRecordService;
import com.example.sbnz.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping(value = "/api/medicalRecord")
@CrossOrigin
public class MedicalRecordController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    PatientService patientService;

    @PostMapping(value = "/create/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> create(@PathVariable Long id, @RequestBody MedicalRecord medicalRecord) {
        logger.info("> medical record: disease: {} medicine: {}  symptoms: {}", medicalRecord.getDisease().getName(), medicalRecord.getMedicine().getName(), medicalRecord.getSymptoms().size());

        medicalRecord.setDate(new Date());
        MedicalRecord md = medicalRecordService.create(medicalRecord);

        Patient patient = patientService.findById(id);

        patient.getRecords().add(md);

        Patient p = patientService.create(patient);

        logger.info("> create medical record: {} disease: {} medicine: {}  symptoms: {}", medicalRecord.getDisease().getName(), medicalRecord.getMedicine().getName(), medicalRecord.getSymptoms().size());
        logger.info("> update patient: {} {}", p.getFirstName(), p.getLastName());

        return new ResponseEntity<>(md, HttpStatus.CREATED);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<?> getAll() {
        logger.info("> getting all medical records");
        Collection<MedicalRecord> records = medicalRecordService.getAll();
        logger.info("size: {}", records.size());
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @GetMapping(value = "/getAll/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<?> getAllForThePatient(@PathVariable Long id) {
        Patient patient = patientService.findById(id);
        logger.info("> getting all medical records for the patient: {} {}", patient.getFirstName(), patient.getLastName());
        Collection<MedicalRecord> records = patient.getRecords();
        logger.info("size: {}", records.size());
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<?> getMedicalRecord(@PathVariable Long id) {
        MedicalRecord medicalRecord = medicalRecordService.findById(id);
        logger.info("getting medical record: {} {}", medicalRecord.getId(), medicalRecord.getDate());
        return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
    }


}
