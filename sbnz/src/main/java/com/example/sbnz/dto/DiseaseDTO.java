package com.example.sbnz.dto;

import com.example.sbnz.model.Disease;

public class DiseaseDTO {

    Disease disease;
    long symptomsCount;

    public DiseaseDTO() {

    }

    public DiseaseDTO(Disease disease, long symptomsCount) {
        this.disease = disease;
        this.symptomsCount = symptomsCount;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public long getSymptomsCount() {
        return symptomsCount;
    }

    public void setSymptomsCount(long symptomsCount) {
        this.symptomsCount = symptomsCount;
    }
}
