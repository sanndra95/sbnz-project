package com.example.sbnz.events;

import com.example.sbnz.model.Patient;
import org.kie.api.definition.type.Duration;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.util.Date;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("1m")
public class HeartbeatEvent {

    Patient patient;
    Date executionTime;


    public HeartbeatEvent() {
        this.executionTime = new Date();
    }

    public HeartbeatEvent(Patient patient) {
        this.patient = patient;
        this.executionTime = new Date();
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }
}
