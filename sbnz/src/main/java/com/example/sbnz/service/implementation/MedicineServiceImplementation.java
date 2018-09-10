package com.example.sbnz.service.implementation;

import com.example.sbnz.model.Medicine;
import com.example.sbnz.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sbnz.service.MedicineService;

import java.util.Collection;

@Service
public class MedicineServiceImplementation implements MedicineService {

    @Autowired
    MedicineRepository medicineRepository;

    @Override
    public Medicine create(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    @Override
    public Medicine delete(Long id) {
        Medicine medicine = medicineRepository.getOne(id);
        medicineRepository.deleteMedicineById(id);
        return medicine;
    }

    @Override
    public Collection<Medicine> getAll() {
        return medicineRepository.findAll();
    }

    @Override
    public Medicine findById(Long id) {
        return medicineRepository.getOne(id);
    }
}
