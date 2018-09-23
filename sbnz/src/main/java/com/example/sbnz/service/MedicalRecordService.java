package com.example.sbnz.service;

import com.example.sbnz.model.MedicalRecord;
import com.example.sbnz.model.Patient;

import java.util.Collection;

public interface MedicalRecordService {

    MedicalRecord create(MedicalRecord medicalRecord);

    Collection<MedicalRecord> getAll();

    MedicalRecord findById(Long id);

    MedicalRecord search(Patient patient, MedicalRecord medicalRecord, String username);
}
