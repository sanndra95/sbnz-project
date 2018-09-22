package com.example.sbnz.events;

import com.example.sbnz.model.Patient;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.util.Date;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("15m")
public class OxygenLevelEvent {

    Date executionTime;
    int oxygenLevel;
    Patient patient;

    public OxygenLevelEvent() {
        this.executionTime = new Date();
    }

    public OxygenLevelEvent(Patient patient, int oxygenLevel) {
        this.executionTime = new Date();
        this.oxygenLevel = oxygenLevel;
        this.patient = patient;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    public int getOxygenLevel() {
        return oxygenLevel;
    }

    public void setOxygenLevel(int oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
