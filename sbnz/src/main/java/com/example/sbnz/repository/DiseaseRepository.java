package com.example.sbnz.repository;

import com.example.sbnz.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sbnz.model.Disease;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    @Modifying
    @Query("update Disease d set d.deleted = true where d.id = ?1")
    @Transactional
    void deleteDiseaseById(Long id);

    @Query("select d from Disease d where d.deleted = false")
    List<Disease> findAll();
}
