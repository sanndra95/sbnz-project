package com.example.sbnz.service.implementation;

import com.example.sbnz.dto.DiseaseDTO;
import com.example.sbnz.model.Disease;
import com.example.sbnz.model.Symptom;
import com.example.sbnz.repository.DiseaseRepository;
import com.example.sbnz.service.DiseaseService;
import com.example.sbnz.utils.DiseaseComparator;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DiseaseServiceImplementation implements DiseaseService {

    @Autowired
    DiseaseRepository diseaseRepository;

    @Autowired
    KieContainer kieContainer;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Disease create(Disease disease) {
        return diseaseRepository.save(disease);
    }

    @Override
    public Disease delete(Long id) {
        Disease disease = diseaseRepository.getOne(id);
        diseaseRepository.deleteDiseaseById(id);
        return disease;
    }

    @Override
    public Collection<Disease> getAll() {
        return diseaseRepository.findAll();
    }

    @Override
    public Disease findById(Long id) {
        return diseaseRepository.getOne(id);
    }

    @Override
    public Collection<DiseaseDTO> getDiseasesBySymptoms(Collection<Symptom> symptoms) {

        KieSession kieSession = kieContainer.newKieSession("ksession-rules");
        for(Symptom s : symptoms) {
            kieSession.insert(s);
        }

        Collection<Disease> diseases = diseaseRepository.findAll();
        for(Disease d : diseases) {
            kieSession.insert(d);
        }

        kieSession.getAgenda().getAgendaGroup("queries-agenda").setFocus();

        QueryResults results = kieSession.getQueryResults("Diseases by symptoms they contain", symptoms);

        List<DiseaseDTO> found = new ArrayList<>();

        for(QueryResultsRow r: results) {
            Disease d = (Disease) r.get("$d");
            long count = (long) r.get("$num");
            DiseaseDTO dto = new DiseaseDTO(d, count);
            found.add(dto);
        }

        Collections.sort(found, new DiseaseComparator());

        logger.info("dto: {}", found.size());

        kieSession.getObjects();

        for (Object object : kieSession.getObjects()) {
            kieSession.delete(kieSession.getFactHandle(object));
        }

        return found;

    }
}
