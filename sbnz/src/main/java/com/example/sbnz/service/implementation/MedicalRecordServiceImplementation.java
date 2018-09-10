package com.example.sbnz.service.implementation;

import com.example.sbnz.model.MedicalRecord;
import com.example.sbnz.repository.MedicalRecordRepository;
import com.example.sbnz.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MedicalRecordServiceImplementation implements MedicalRecordService {

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Override
    public MedicalRecord create(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    @Override
    public Collection<MedicalRecord> getAll() {
        return medicalRecordRepository.findAll();
    }

    @Override
    public MedicalRecord findById(Long id) {
        return medicalRecordRepository.getOne(id);
    }
}
