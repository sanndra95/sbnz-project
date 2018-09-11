package com.example.sbnz.service;

import com.example.sbnz.dto.ReportDTO;
import com.example.sbnz.model.Patient;

import java.util.Collection;

public interface PatientService {

    Patient create(Patient patient);

    Patient findById(Long id);

    Collection<Patient> getAll();

    Collection<ReportDTO> getReport1();

    Collection<ReportDTO> getReport2();

    Collection<Patient> getReport3();
}
