package com.example.sbnz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Symptom {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Column
	String name;
	
	@Column
	@Enumerated(EnumType.STRING)
	SymptomType type;
	
	public Symptom() {
		
	}

	public Symptom(Long id, String name, SymptomType type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
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

	public SymptomType getType() {
		return type;
	}

	public void setType(SymptomType type) {
		this.type = type;
	}
	
	
}
