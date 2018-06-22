package com.example.sbnz.service;

import com.example.sbnz.model.Disease;

import java.util.Collection;

public interface DiseaseService {

    Disease create(Disease disease);

    Disease delete(Long id);

    Collection<Disease> getAll();

    Disease findById(Long id);
}
