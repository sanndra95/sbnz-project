package com.example.sbnz.service.implementation;

import com.example.sbnz.configuration.WebSocketController;
import com.example.sbnz.dto.ReportDTO;
import com.example.sbnz.events.Simulation;
import com.example.sbnz.model.Disease;
import com.example.sbnz.model.Medicine;
import com.example.sbnz.model.Patient;
import com.example.sbnz.repository.DiseaseRepository;
import com.example.sbnz.repository.MedicineRepository;
import com.example.sbnz.repository.PatientRepository;
import com.example.sbnz.service.PatientService;
import com.example.sbnz.utils.TimeCheck;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PatientServiceImplementation implements PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DiseaseRepository diseaseRepository;

    @Autowired
    MedicineRepository medicineRepository;

    @Autowired
    KieContainer kieContainer;

    @Autowired
    WebSocketController webSocketController;

    @Autowired
    Simulation sim;

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

    @Override
    public Collection<ReportDTO> getReport1() {
        KieServices ks = KieServices.Factory.get();
        KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
        kbconf.setOption(EventProcessingOption.STREAM);
        KieBase kbase = kieContainer.newKieBase(kbconf);
        KieSession kieSession = kbase.newKieSession();

        Collection<Patient> allPatients = patientRepository.findAll();
        Collection<Disease> allDiseases = diseaseRepository.findAll();
        Collection<Medicine> allMedicine = medicineRepository.findAll();

        for(Patient p: allPatients) {
            kieSession.insert(p);
        }

        for(Disease d: allDiseases) {
            kieSession.insert(d);
        }

        for(Medicine m: allMedicine) {
            kieSession.insert(m);
        }

        kieSession.insert(new TimeCheck());

        kieSession.getAgenda().getAgendaGroup("reports-agenda").setFocus();

        QueryResults results = kieSession.getQueryResults("Spisak pacijenata sa mogucim hronicnim oboljenjima");

        List<ReportDTO> found = new ArrayList<>();

        for(QueryResultsRow r: results) {
            Patient p = (Patient) r.get("$p");
            Disease d = (Disease) r.get("$d");
            System.err.println(d.getName());
            ReportDTO dto = new ReportDTO(p.getFirstName(), p.getLastName(), d.getName());
            found.add(dto);
        }

        kieSession.getObjects();

        for (Object object : kieSession.getObjects()) {
            kieSession.delete(kieSession.getFactHandle(object));
        }

        return found;
    }

    @Override
    public Collection<ReportDTO> getReport2() {
        KieServices ks = KieServices.Factory.get();
        KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
        kbconf.setOption(EventProcessingOption.STREAM);
        KieBase kbase = kieContainer.newKieBase(kbconf);
        KieSession kieSession = kbase.newKieSession();

        Collection<Patient> allPatients = patientRepository.findAll();
        Collection<Disease> allDiseases = diseaseRepository.findAll();
        Collection<Medicine> allMedicine = medicineRepository.findAll();

        System.err.println(allPatients.size());
        System.err.println(allDiseases.size());
        System.err.println(allMedicine.size());

        for(Patient p: allPatients) {
            kieSession.insert(p);
        }

        for(Disease d: allDiseases) {
            kieSession.insert(d);
        }

        for(Medicine m: allMedicine) {
            kieSession.insert(m);
        }

        kieSession.insert(new TimeCheck());

        kieSession.getAgenda().getAgendaGroup("reports-agenda").setFocus();

        QueryResults results = kieSession.getQueryResults("Spisak mogucih zavisnika");

        System.err.println(results.size());

        List<ReportDTO> found = new ArrayList<>();

        for(QueryResultsRow r: results) {
            Patient p = (Patient) r.get("$p");
            Medicine m = (Medicine) r.get("$m");
            System.err.println(m.getName());
            ReportDTO dto = new ReportDTO(p.getFirstName(), p.getLastName(), m.getName());
            found.add(dto);
        }

        kieSession.getObjects();

        for (Object object : kieSession.getObjects()) {
            kieSession.delete(kieSession.getFactHandle(object));
        }

        return found;
    }

    @Override
    public Collection<Patient> getReport3() {
        KieServices ks = KieServices.Factory.get();
        KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
        kbconf.setOption(EventProcessingOption.STREAM);
        KieBase kbase = kieContainer.newKieBase(kbconf);
        KieSession kieSession = kbase.newKieSession();

        Collection<Patient> allPatients = patientRepository.findAll();
        Collection<Disease> allDiseases = diseaseRepository.findAll();
        Collection<Medicine> allMedicine = medicineRepository.findAll();

        for(Patient p: allPatients) {
            kieSession.insert(p);
        }

        for(Disease d: allDiseases) {
            kieSession.insert(d);
        }

        for(Medicine m: allMedicine) {
            kieSession.insert(m);
        }

        kieSession.insert(new TimeCheck());

        kieSession.getAgenda().getAgendaGroup("reports-agenda").setFocus();

        QueryResults results = kieSession.getQueryResults("Spisak pacijenata sa oslabljenim imunitetom");

        List<Patient> found = new ArrayList<>();

        for(QueryResultsRow r: results) {
            Patient p = (Patient) r.get("$p");
            found.add(p);
        }

        Set<Patient> s = new HashSet<>();
        s.addAll(found);
        found.clear();
        found.addAll(s);


        kieSession.getObjects();

        for (Object object : kieSession.getObjects()) {
            kieSession.delete(kieSession.getFactHandle(object));
        }

        return found;
    }

    @Override
    public void monitoring() throws InterruptedException {

        KieServices ks = KieServices.Factory.get();
        KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
        kbconf.setOption(EventProcessingOption.STREAM);
        KieBase kbase = kieContainer.newKieBase(kbconf);
        KieSession kieSession = kbase.newKieSession();



        Thread.sleep(1000);
        while (true) {
            sim.oxygenEvent(kieSession, webSocketController);
            sim.heartRhythmEvent(kieSession, webSocketController);
            sim.dialysisEvent(kieSession, webSocketController);
        }

    }
}
