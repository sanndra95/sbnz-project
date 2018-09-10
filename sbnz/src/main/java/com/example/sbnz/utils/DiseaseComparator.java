package com.example.sbnz.utils;

import com.example.sbnz.dto.DiseaseDTO;

import java.util.Comparator;

public class DiseaseComparator implements Comparator<DiseaseDTO> {
    @Override
    public int compare(DiseaseDTO d1, DiseaseDTO d2) {
        return -Long.compare(d1.getSymptomsCount(), d2.getSymptomsCount());
    }
}
