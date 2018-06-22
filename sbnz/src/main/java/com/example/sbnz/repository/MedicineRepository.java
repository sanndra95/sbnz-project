package com.example.sbnz.repository;

import com.example.sbnz.model.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sbnz.model.Medicine;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    @Modifying
    @Query("update Medicine m set m.deleted = true where m.id = ?1")
    @Transactional
    void deleteMedicineById(Long id);

    @Query("select m from Medicine m where m.deleted = false")
    List<Medicine> findAll();
}
