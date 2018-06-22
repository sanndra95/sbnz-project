package com.example.sbnz.service;

import com.example.sbnz.model.MedicalRecord;

import java.util.Collection;

public interface MedicalRecordService {

    MedicalRecord create(MedicalRecord medicalRecord);

    Collection<MedicalRecord> getAll();

    MedicalRecord findById(Long id);
}
