package com.example.sbnz.controller;

import com.example.sbnz.dto.ReportDTO;
import com.example.sbnz.model.Patient;
import com.example.sbnz.model.User;
import com.example.sbnz.security.JwtTokenUtil;
import com.example.sbnz.service.PatientService;
import com.example.sbnz.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@RequestMapping(value = "/api/patient")
@CrossOrigin
public class PatientController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PatientService patientService;

    @Autowired
    UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> create(@RequestBody Patient patient) {
        logger.info("> patient: {} {} {} {} {}", patient.getFirstName(), patient.getLastName(), patient.getRecords(), patient.getMedicineAllergies(), patient.getComponentAllergies());

        Patient p = patientService.create(patient);

        logger.info("> create patient: {}", p.getFirstName());
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<?> getAll() {
        logger.info("> getting all patient");
        Collection<Patient> patients = patientService.getAll();
        logger.info("size: {}", patients.size());
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<?> getPatient(@PathVariable Long id) {
        Patient patient = patientService.findById(id);
        logger.info("> getting patient: {} {}", patient.getFirstName(), patient.getLastName());
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> update(@RequestBody Patient patient) {
        Patient p = patientService.findById(patient.getId());
        logger.info("> updating patient: {} {}", p.getFirstName(), p.getLastName());
        p.setFirstName(patient.getFirstName());
        p.setLastName(patient.getLastName());
        p.setMedicineAllergies(patient.getMedicineAllergies());
        p.setComponentAllergies(patient.getComponentAllergies());
        Patient updated = patientService.create(p);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping(value = "/getReport1", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> getReport1(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User u = userService.findByUsername(username);

        Collection<ReportDTO> patients = patientService.getReport1(u.getEmail());
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping(value = "/getReport2", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> getReport2(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User u = userService.findByUsername(username);

        Collection<ReportDTO> patients = patientService.getReport2(u.getEmail());
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping(value = "/getReport3", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> getReport3(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User u = userService.findByUsername(username);

        Collection<Patient> patients = patientService.getReport3(u.getEmail());
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping(value = "/monitor", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> simulate(HttpServletRequest request) throws InterruptedException {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User u = userService.findByUsername(username);

        patientService.monitoring(u.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
