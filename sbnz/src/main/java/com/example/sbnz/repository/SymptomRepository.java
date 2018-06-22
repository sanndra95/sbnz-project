package com.example.sbnz.repository;

import com.example.sbnz.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sbnz.model.Symptom;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {

    @Modifying
    @Query("update Symptom s set s.deleted = true where s.id = ?1")
    @Transactional
    void deleteSymptomById(Long id);

    @Query("select s from Symptom s where s.deleted = false")
    List<Symptom> findAll();

}
