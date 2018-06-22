package com.example.sbnz.service.implementation;

import com.example.sbnz.model.Symptom;
import com.example.sbnz.repository.SymptomRepository;
import com.example.sbnz.service.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SymptomServiceImplementation implements SymptomService {

    @Autowired
    SymptomRepository symptomRepository;

    @Override
    public Symptom create(Symptom symptom) {
        return symptomRepository.save(symptom);
    }

    @Override
    public Symptom delete(Long id) {
        Symptom symptom = symptomRepository.findOne(id);
        symptomRepository.deleteSymptomById(id);
        return symptom;
    }

    @Override
    public Collection<Symptom> getAll() {
        return symptomRepository.findAll();
    }

    @Override
    public Symptom findById(Long id) {
        return symptomRepository.findOne(id);
    }
}
