package com.example.sbnz.service.implementation;

import com.example.sbnz.model.Patient;
import com.example.sbnz.repository.PatientRepository;
import com.example.sbnz.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PatientServiceImplementation implements PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Override
    public Patient create(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient findById(Long id) {
        return patientRepository.getOne(id);
    }

    @Override
    public Collection<Patient> getAll() {
        return patientRepository.findAll();
    }
}
