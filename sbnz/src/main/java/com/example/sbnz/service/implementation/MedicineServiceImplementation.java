package com.example.sbnz.service.implementation;

import com.example.sbnz.model.Component;
import com.example.sbnz.model.Medicine;
import com.example.sbnz.model.Patient;
import com.example.sbnz.repository.MedicineRepository;
import com.example.sbnz.repository.PatientRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sbnz.service.MedicineService;

import java.util.Collection;

@Service
public class MedicineServiceImplementation implements MedicineService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MedicineRepository medicineRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    KieContainer kieContainer;

    @Override
    public Medicine create(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    @Override
    public Medicine delete(Long id) {
        Medicine medicine = medicineRepository.getOne(id);
        medicineRepository.deleteMedicineById(id);
        return medicine;
    }

    @Override
    public Collection<Medicine> getAll() {
        return medicineRepository.findAll();
    }

    @Override
    public Medicine findById(Long id) {
        return medicineRepository.getOne(id);
    }

    @Override
    public Boolean checkAllergies(Long id, Medicine medicine) {
        Patient patient = patientRepository.findOne(id);

        logger.info("pacijent: {} {}", patient.getFirstName(), patient.getLastName());
        logger.info("alergican na lekove: {} ", patient.getMedicineAllergies().size());
        logger.info("alergican na komponente: {}", patient.getComponentAllergies().size());
        logger.info("lek: {}", medicine.getName());

        KieSession kieSession = kieContainer.newKieSession("ksession-rules");

        kieSession.insert(patient);
        kieSession.insert(medicine);

        for(Component c: medicine.getComponents()) {
            kieSession.insert(c);
        }

        kieSession.getAgenda().getAgendaGroup("allergies-agenda").setFocus();

        int num = kieSession.fireAllRules();
        logger.info("rules fired: {}", num);

        kieSession.getObjects();

        for (Object object : kieSession.getObjects()) {
            kieSession.delete(kieSession.getFactHandle(object));
        }

        if(num > 0) {
            return true;
        }
        return false;


    }
}
