package com.example.sbnz.service.implementation;

import com.example.sbnz.SbnzApplication;
import com.example.sbnz.dto.DiseaseDTO;
import com.example.sbnz.model.Disease;
import com.example.sbnz.model.Symptom;
import com.example.sbnz.repository.SymptomRepository;
import com.example.sbnz.service.SymptomService;
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
public class SymptomServiceImplementation implements SymptomService {

    @Autowired
    SymptomRepository symptomRepository;

    @Autowired
    KieContainer kieContainer;

    @Override
    public Symptom create(Symptom symptom) {
        return symptomRepository.save(symptom);
    }

    @Override
    public Symptom delete(Long id) {
        Symptom symptom = symptomRepository.getOne(id);
        symptomRepository.deleteSymptomById(id);
        return symptom;
    }

    @Override
    public Collection<Symptom> getAll() {
        return symptomRepository.findAll();
    }

    @Override
    public Symptom findById(Long id) {
        return symptomRepository.getOne(id);
    }

    @Override
    public Collection<Symptom> getSymptomsByDisease(Disease disease, String username) {

        KieSession kieSession = SbnzApplication.kieSessions.get("kieSession-"+username);
        if (kieSession == null) {
            KieServices ks = KieServices.Factory.get();
            KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
            kbconf.setOption(EventProcessingOption.STREAM);
            KieBase kbase = kieContainer.newKieBase(kbconf);
            kieSession = kbase.newKieSession();
        }
        kieSession.insert(disease);

        kieSession.getAgenda().getAgendaGroup("queries-agenda").setFocus();

        QueryResults results = kieSession.getQueryResults("Symptoms of a disease", disease);

        Collection<Symptom> symptoms = new HashSet<>();

        for(QueryResultsRow r: results) {
            symptoms = (HashSet<Symptom>) r.get("$symptoms");
        }

        return symptoms;
    }
}
