package com.example.sbnz.dto;

import com.example.sbnz.model.Symptom;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SymptomList {

    Set<Symptom> symptoms = new HashSet<>();

    public SymptomList(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public Set<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
    }
}
