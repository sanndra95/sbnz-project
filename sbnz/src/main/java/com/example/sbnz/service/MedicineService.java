package com.example.sbnz.service;

import com.example.sbnz.model.Medicine;

import java.util.Collection;

public interface MedicineService {


    Medicine create(Medicine medicine);

    Medicine delete(Long id);

    Collection<Medicine> getAll();

    Medicine findById(Long id);

    Boolean checkAllergies(Long id, Medicine medicine, String username);
}
