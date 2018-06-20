package com.example.sbnz.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class MedicalRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@ManyToOne
	Disease disease;
	
	@ManyToMany
	Set<Symptom> symptoms = new HashSet<>();
	
	@ManyToOne
	Medicine medicine;
	
	public MedicalRecord() {
		
	}

	public MedicalRecord(Long id, Disease disease, Set<Symptom> symptoms, Medicine medicine) {
		super();
		this.id = id;
		this.disease = disease;
		this.symptoms = symptoms;
		this.medicine = medicine;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Disease getDisease() {
		return disease;
	}

	public void setDisease(Disease disease) {
		this.disease = disease;
	}

	public Set<Symptom> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(Set<Symptom> symptoms) {
		this.symptoms = symptoms;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}
	
	

}
