package com.example.sbnz.service;

import com.example.sbnz.model.Disease;
import com.example.sbnz.model.Symptom;

import java.util.Collection;

public interface SymptomService {

    Symptom create(Symptom symptom);

    Symptom delete(Long id);

    Collection<Symptom> getAll();

    Symptom findById(Long id);

    Collection<Symptom> getSymptomsByDisease(Disease disease);
}
