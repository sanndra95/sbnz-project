package com.example.sbnz.events;

import com.example.sbnz.model.Patient;
import org.kie.api.definition.type.Duration;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import java.util.Date;

@Role(Role.Type.EVENT)
@Expires("15m")
public class HeartRhythmMessageEvent {

    Patient patient;
    String message;

    public HeartRhythmMessageEvent() {
    }

    public HeartRhythmMessageEvent(Patient patient, String message) {
        this.patient = patient;
        this.message = message;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
