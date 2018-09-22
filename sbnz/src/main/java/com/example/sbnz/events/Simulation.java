package com.example.sbnz.events;

import com.example.sbnz.configuration.WebSocketController;
import com.example.sbnz.model.Disease;
import com.example.sbnz.model.MedicalRecord;
import com.example.sbnz.model.Patient;

import org.drools.core.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

@Component
public class Simulation {

    public void oxygenEvent(final KieSession session, WebSocketController controller) throws InterruptedException {
        Thread t = new Thread(){
            @Override
            public void run() {
                Patient p = new Patient(1L, "Sandra");
                for (int index = 0; index < 10; index++) {
                    OxygenLevelEvent oxygenEvent = new OxygenLevelEvent(p, 65);
                    System.out.println(oxygenEvent.getExecutionTime());
                    session.insert(oxygenEvent);
                    try {
                        Thread.sleep(100000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(1000*5);
        } catch(InterruptedException e) {
        }

        session.getAgenda().getAgendaGroup("oxygen-agenda").setFocus();
        session.fireUntilHalt();

        Collection<OxygenLevelMessageEvent> events = (Collection<OxygenLevelMessageEvent>) session
                .getObjects(new ClassObjectFilter(OxygenLevelMessageEvent.class));

        Iterator<OxygenLevelMessageEvent> it = events.iterator();
        while(it.hasNext()) {
            OxygenLevelMessageEvent ev = it.next();
            //System.err.println(ev);
            controller.onReceivedMesage(ev.getMessage());
            break;
        }

    }

    public void heartRhythmEvent(final KieSession session, WebSocketController controller) {
        Thread t = new Thread() {
            @Override
            public void run() {
                Patient p = new Patient(1L, "Sandra");
                for(int index = 0; index < 30; index++){
                    HeartbeatEvent heartbeatEvent = new HeartbeatEvent(p);
                    session.insert(heartbeatEvent);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(1000*5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        session.getAgenda().getAgendaGroup("heart-rhythm-agenda").setFocus();
        session.fireUntilHalt();
        Collection<HeartRhythmMessageEvent> coll = (Collection<HeartRhythmMessageEvent>)
                session.getObjects(new ClassObjectFilter(HeartRhythmMessageEvent.class));

        Iterator<HeartRhythmMessageEvent> it = coll.iterator();
        while(it.hasNext()) {
            HeartRhythmMessageEvent ev = it.next();
            //System.err.println(ev);
            controller.onReceivedMesage(ev.getMessage());
            break;
        }
    }

    public void dialysisEvent(final KieSession session, WebSocketController controller) {
        Thread t = new Thread() {
            @Override
            public void run() {
                Patient p = new Patient(1L, "Sandra");
                Disease disease = new Disease();
                disease.setName("Hronicna bubrezna bolest");
                MedicalRecord medicalRecord = new MedicalRecord();
                medicalRecord.setDisease(disease);
                p.getRecords().add(medicalRecord);
                session.insert(p);
                for(int index = 0; index < 15; index++) {
                    HeartbeatEvent heartbeatEvent = new HeartbeatEvent(p);
                    session.insert(heartbeatEvent);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for(int index = 0; index < 13; index++) {
                    UrinEvent urinEvent = new UrinEvent(1, p);
                    session.insert(urinEvent);
                    try {
                        Thread.sleep(1000*3600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(1000*5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        session.getAgenda().getAgendaGroup("dialysis-agenda").setFocus();
        session.fireUntilHalt();
        Collection<EmergencyDialysisMessageEvent> coll = (Collection<EmergencyDialysisMessageEvent>)
                session.getObjects(new ClassObjectFilter(EmergencyDialysisMessageEvent.class));

        Iterator<EmergencyDialysisMessageEvent> it = coll.iterator();
        while(it.hasNext()) {
            EmergencyDialysisMessageEvent ev = it.next();
            //System.err.println(ev);
            controller.onReceivedMesage(ev.getMessage());
            break;
        }
    }
}
