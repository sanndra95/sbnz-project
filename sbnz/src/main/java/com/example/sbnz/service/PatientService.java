package com.example.sbnz.service;

import com.example.sbnz.dto.ReportDTO;
import com.example.sbnz.model.Patient;

import java.util.Collection;

public interface PatientService {

    Patient create(Patient patient);

    Patient findById(Long id);

    Collection<Patient> getAll();

    Collection<ReportDTO> getReport1(String username);

    Collection<ReportDTO> getReport2(String username);

    Collection<Patient> getReport3(String username);

    void monitoring(String username) throws InterruptedException;
}
