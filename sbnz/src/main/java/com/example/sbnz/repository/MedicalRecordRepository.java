package com.example.sbnz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sbnz.model.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

}
