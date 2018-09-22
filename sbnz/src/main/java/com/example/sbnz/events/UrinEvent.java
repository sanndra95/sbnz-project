package com.example.sbnz.events;

import com.example.sbnz.model.Patient;
import org.kie.api.definition.type.Duration;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import java.util.Date;

@Role(Role.Type.EVENT)
@Duration("executionTime")
@Expires("12h")
public class UrinEvent {

    Date executionTime;
    int amount;
    Patient patient;

    public UrinEvent() {
        this.executionTime = new Date();
    }

    public UrinEvent(int amount, Patient patient) {
        this.executionTime = new Date();
        this.amount = amount;
        this.patient = patient;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
