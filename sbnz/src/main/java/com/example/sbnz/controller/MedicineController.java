package com.example.sbnz.controller;

import com.example.sbnz.model.Component;
import com.example.sbnz.model.Disease;
import com.example.sbnz.model.Medicine;
import com.example.sbnz.model.Patient;
import com.example.sbnz.service.DiseaseService;
import com.example.sbnz.service.MedicineService;
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

@RestController
@RequestMapping(value = "/api/medicine")
@CrossOrigin
public class MedicineController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MedicineService medicineService;

    @Autowired
    PatientService patientService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody Medicine medicine) {
        logger.info("> medicine: {} {} {}", medicine.getName(), medicine.getType(), medicine.getComponents());

        medicine.setDeleted(false);

        Medicine m = medicineService.create(medicine);

        logger.info("> create medicine: {}", m.getName());
        return new ResponseEntity<>(m, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        logger.info("> deleting medicine with id: {} ", id);

        Medicine medicine = medicineService.delete(id);
        logger.info("> deleted: {}", medicine.getName());
        return new ResponseEntity<>(medicine, HttpStatus.OK);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<?> getAll() {
        logger.info("> getting all medicine");
        Collection<Medicine> medicine = medicineService.getAll();
        logger.info("size: {}", medicine.size());
        return new ResponseEntity<>(medicine, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<?> getMedicine(@PathVariable Long id) {
        Medicine medicine = medicineService.findById(id);
        logger.info("getting medicine: {}", medicine.getName());
        return new ResponseEntity<>(medicine, HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Medicine medicine) {
        Medicine m = medicineService.findById(medicine.getId());
        logger.info("> updating medicine: {}", m.getName());
        m.setName(medicine.getName());
        m.setType(medicine.getType());
        m.setComponents(medicine.getComponents());
        Medicine updated = medicineService.create(m);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PostMapping(value = "/checkAllergies/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> checkForAllergies(@PathVariable Long id, @RequestBody Medicine medicine) {

        Boolean allergic = medicineService.checkAllergies(id, medicine);
        return new ResponseEntity<>(allergic, HttpStatus.OK);
    }

}
