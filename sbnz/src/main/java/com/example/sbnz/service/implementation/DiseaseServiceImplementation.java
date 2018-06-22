package com.example.sbnz.service.implementation;

import com.example.sbnz.model.Disease;
import com.example.sbnz.repository.DiseaseRepository;
import com.example.sbnz.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DiseaseServiceImplementation implements DiseaseService {

    @Autowired
    DiseaseRepository diseaseRepository;

    @Override
    public Disease create(Disease disease) {
        return diseaseRepository.save(disease);
    }

    @Override
    public Disease delete(Long id) {
        Disease disease = diseaseRepository.findOne(id);
        diseaseRepository.deleteDiseaseById(id);
        return disease;
    }

    @Override
    public Collection<Disease> getAll() {
        return diseaseRepository.findAll();
    }

    @Override
    public Disease findById(Long id) {
        return diseaseRepository.findOne(id);
    }
}
