package com.example.sbnz.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

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

	@ManyToOne
	User doctor;

	@Column
	Date date;

	@Column
	Boolean deleted;
	
	public MedicalRecord() {
		
	}

	public MedicalRecord(Long id, Disease disease, Set<Symptom> symptoms, Medicine medicine, User doctor, Date date, Boolean deleted) {
		this.id = id;
		this.disease = disease;
		this.symptoms = symptoms;
		this.medicine = medicine;
		this.doctor = doctor;
		this.date = date;
		this.deleted = deleted;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public User getDoctor() {
		return doctor;
	}

	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}
}
