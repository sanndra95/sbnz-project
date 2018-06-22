package com.example.sbnz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sbnz.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
