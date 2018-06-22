package com.example.sbnz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sbnz.model.Component;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface ComponentRepository extends JpaRepository<Component, Long> {

    @Modifying
    @Query("update Component c set c.deleted = true where c.id = ?1")
    @Transactional
    void deleteComponentById(Long id);

    @Query("select c from Component c where c.deleted = false")
    List<Component> findAll();
}
