package com.example.sbnz.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Disease {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Column
	String name;
	
	@ManyToMany
	Set<Symptom> symptoms = new HashSet<>();
	
	@Column
	@Enumerated(EnumType.STRING)
	DiseaseGroup diseaseGroup;
	
	public Disease() {
		
	}

	public Disease(Long id, String name, Set<Symptom> symptoms, DiseaseGroup diseaseGroup) {
		super();
		this.id = id;
		this.name = name;
		this.symptoms = symptoms;
		this.diseaseGroup = diseaseGroup;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Symptom> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(Set<Symptom> symptoms) {
		this.symptoms = symptoms;
	}

	public DiseaseGroup getDiseaseGroup() {
		return diseaseGroup;
	}

	public void setDiseaseGroup(DiseaseGroup diseaseGroup) {
		this.diseaseGroup = diseaseGroup;
	}

}
