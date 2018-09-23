package com.example.sbnz.controller;

import com.example.sbnz.model.*;
import com.example.sbnz.security.JwtTokenUtil;
import com.example.sbnz.service.SymptomService;
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
@RequestMapping(value = "/api/symptom")
@CrossOrigin
public class SymptomController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SymptomService symptomService;

    @Autowired
    UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody Symptom symptom) {
        logger.info("> symptom: {} {}", symptom.getName(), symptom.getType());

        symptom.setDeleted(false);

        Symptom s = symptomService.create(symptom);

        logger.info("> create symptom: {}", s.getName());
        return new ResponseEntity<>(s, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        logger.info("> deleting symptom with id: {} ", id);

        Symptom symptom =symptomService.delete(id);
        logger.info("> deleted: {}", symptom.getName());
        return new ResponseEntity<>(symptom, HttpStatus.OK);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<?> getAll() {
        logger.info("> getting all symptoms");
        Collection<Symptom> symptoms = symptomService.getAll();
        logger.info("size: {}", symptoms.size());
        return new ResponseEntity<>(symptoms, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<?> getSymptom(@PathVariable Long id) {
        Symptom symptom = symptomService.findById(id);
        logger.info("getting symptom: {}", symptom.getName());
        return new ResponseEntity<>(symptom, HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Symptom symptom) {
        Symptom s = symptomService.findById(symptom.getId());
        logger.info("> updating symptom: {}", s.getName());
        s.setName(symptom.getName());
        s.setType(symptom.getType());
        Symptom updated = symptomService.create(s);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PostMapping(value = "/getByDisease", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> getSymptomsByDisease(@RequestBody Disease disease, HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User u = userService.findByUsername(username);

        Collection<Symptom> symptoms = symptomService.getSymptomsByDisease(disease, u.getEmail());
        return new ResponseEntity<>(symptoms, HttpStatus.OK);
    }
}
