package com.example.sbnz.controller;

import com.example.sbnz.model.Disease;
import com.example.sbnz.model.Symptom;
import com.example.sbnz.service.SymptomService;
import com.example.sbnz.utils.DiseasePriority;
import com.example.sbnz.model.MedicalRecord;
import com.example.sbnz.model.Patient;
import com.example.sbnz.service.DiseaseService;
import com.example.sbnz.service.MedicalRecordService;
import com.example.sbnz.service.PatientService;
import com.example.sbnz.utils.TimeCheck;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
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
    KieContainer kieContainer;

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    DiseaseService diseaseService;

    @Autowired
    PatientService patientService;

    @Autowired
    SymptomService symptomService;

    @PostMapping(value = "/search/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> startResoner(@PathVariable Long id, @RequestBody MedicalRecord medicalRecord) {
        //logger.info("> medical record: disease: {} medicine: {}  symptoms: {}", medicalRecord.getDisease().getName(), medicalRecord.getMedicine().getName(), medicalRecord.getSymptoms().size());

        medicalRecord.setDate(new Date());
        medicalRecord.setDeleted(false);

        Patient patient = patientService.findById(id);

        KieSession kieSession = kieContainer.newKieSession("ksession-rules");

        Collection<Disease> diseases = diseaseService.getAll();

        logger.info("size: {}", diseases.size());
        logger.info("patient: {}", patient.getFirstName());
        logger.info("record: {}", medicalRecord.getDate());

        kieSession.insert(patient);
        kieSession.insert(medicalRecord);

        for(Disease d : diseases) {
            kieSession.insert(d);
        }

        kieSession.insert(new DiseasePriority());
        kieSession.insert(new TimeCheck());

        //kieSession.getAgenda().getAgendaGroup("diseases-agenda").setFocus();

        kieSession.fireAllRules();
        //kieSession.dispose();

        //MedicalRecord mr = medicalRecordService.create(medicalRecord);
        logger.info("symptoms: {}", medicalRecord.getSymptoms().size());

        if(medicalRecord.getDisease() != null) {
            logger.info("disease detected: {}", medicalRecord.getDisease().getName());
        }
        else {
            logger.info("no disease detected");
        }

        //patient.getRecords().add(medicalRecord);

        //Patient p = patientService.create(patient);

        //logger.info("> update patient: {} {}", p.getFirstName(), p.getLastName());

        return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
    }

    @PostMapping(value = "/create/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> create(@PathVariable Long id, @RequestBody MedicalRecord medicalRecord) {

        if(medicalRecord.getDate() == null) {
            medicalRecord.setDate(new Date());
        }

        medicalRecord.setDeleted(false);

        Patient patient = patientService.findById(id);

        MedicalRecord mr = medicalRecordService.create(medicalRecord);
        logger.info("symptoms: {}", medicalRecord.getSymptoms().size());

        if(mr.getDisease() != null) {
            logger.info("disease created: {}", mr.getDisease().getName());
        }
        else {
            logger.info("no disease created");
        }

        patient.getRecords().add(mr);

        Patient p = patientService.create(patient);

        logger.info("> update patient: {} {}", p.getFirstName(), p.getLastName());

        return new ResponseEntity<>(mr, HttpStatus.OK);
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
