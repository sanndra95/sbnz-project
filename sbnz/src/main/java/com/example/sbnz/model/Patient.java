package com.example.sbnz.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


@Entity
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Column
	String firstName;
	
	@Column
	String lastName;
	
	@OneToMany(fetch = FetchType.EAGER)
	Set<MedicalRecord> records = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	Set<Medicine> medicineAllergies = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	Set<Component> componentAllergies = new HashSet<>();
	
	public Patient() {
		
	}

	public Patient(Long id, String firstName, String lastName, Set<MedicalRecord> records,
			Set<Medicine> medicineAllergies, Set<Component> componentAllergies) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.records = records;
		this.medicineAllergies = medicineAllergies;
		this.componentAllergies = componentAllergies;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Set<MedicalRecord> getRecords() {
		return records;
	}


	public void setRecords(Set<MedicalRecord> records) {
		this.records = records;
	}

	public Set<Medicine> getMedicineAllergies() {
		return medicineAllergies;
	}

	public void setMedicineAllergies(Set<Medicine> medicineAllergies) {
		this.medicineAllergies = medicineAllergies;
	}

	public Set<Component> getComponentAllergies() {
		return componentAllergies;
	}

	public void setComponentAllergies(Set<Component> componentAllergies) {
		this.componentAllergies = componentAllergies;
	}

}
