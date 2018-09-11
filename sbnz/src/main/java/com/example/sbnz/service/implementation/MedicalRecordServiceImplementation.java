package com.example.sbnz.service.implementation;

import com.example.sbnz.model.Disease;
import com.example.sbnz.model.MedicalRecord;
import com.example.sbnz.model.Patient;
import com.example.sbnz.repository.DiseaseRepository;
import com.example.sbnz.repository.MedicalRecordRepository;
import com.example.sbnz.service.MedicalRecordService;
import com.example.sbnz.utils.DiseasePriority;
import com.example.sbnz.utils.TimeCheck;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MedicalRecordServiceImplementation implements MedicalRecordService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    KieContainer kieContainer;

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Autowired
    DiseaseRepository diseaseRepository;

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

    @Override
    public MedicalRecord search(Patient patient, MedicalRecord medicalRecord) {
        KieSession kieSession = kieContainer.newKieSession("ksession-rules");

        Collection<Disease> diseases = diseaseRepository.findAll();

        logger.info("size: {}", diseases.size());
        logger.info("patient: {}", patient.getFirstName());
        logger.info("record: {}", medicalRecord.getDate());

        kieSession.insert(patient);
        kieSession.insert(medicalRecord);

        for(Disease d : diseases) {
            kieSession.insert(d);
        }

        kieSession.insert(new DiseasePriority());
        kieSession.insert(new TimeCheck());

        kieSession.getAgenda().getAgendaGroup("diseases-agenda").setFocus();

        kieSession.fireAllRules();

        logger.info("symptoms: {}", medicalRecord.getSymptoms().size());

        if(medicalRecord.getDisease() != null) {
            logger.info("disease detected: {}", medicalRecord.getDisease().getName());
        }
        else {
            logger.info("no disease detected");
        }

        kieSession.getObjects();

        for (Object object : kieSession.getObjects()) {
            kieSession.delete(kieSession.getFactHandle(object));
        }

        return medicalRecord;
    }
}
