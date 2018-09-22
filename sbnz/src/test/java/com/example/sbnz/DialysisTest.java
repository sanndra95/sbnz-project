package com.example.sbnz;

import com.example.sbnz.events.EmergencyDialysisMessageEvent;
import com.example.sbnz.events.HeartbeatEvent;
import com.example.sbnz.events.UrinEvent;
import com.example.sbnz.model.Disease;
import com.example.sbnz.model.MedicalRecord;
import com.example.sbnz.model.Patient;
import org.drools.core.ClassObjectFilter;
import org.drools.core.ClockType;
import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DialysisTest {

    @Test
    public void testDialysis(){

        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.newKieContainer(kieServices.newReleaseId("drools-spring-v2", "drools-spring-v2-kjar", "0.0.1-SNAPSHOT"));


        KieBaseConfiguration kconf = kieServices.newKieBaseConfiguration();
        kconf.setOption(EventProcessingOption.STREAM);
        KieBase kieBase = kContainer.newKieBase(kconf);

        KieSessionConfiguration kconfig1 = kieServices.newKieSessionConfiguration();
        kconfig1.setOption(ClockTypeOption.get(ClockType.REALTIME_CLOCK.getId()));
        KieSession kSession1 = kieBase.newKieSession(kconfig1, null);

        KieSessionConfiguration kconfig2 = kieServices.newKieSessionConfiguration();
        kconfig2.setOption(ClockTypeOption.get(ClockType.PSEUDO_CLOCK.getId()));
        KieSession kSession2 = kieBase.newKieSession(kconfig2, null);

        realTimeClockTest(kSession1);
        pseudoClockTest(kSession2);

    }

    private void pseudoClockTest(KieSession kieSession){
        SessionPseudoClock clock = kieSession.getSessionClock();
        Patient p = new Patient(1L, "Sandra");
        Disease disease = new Disease();
        disease.setName("Hronicna bubrezna bolest");
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setDisease(disease);
        p.getRecords().add(medicalRecord);
        kieSession.insert(p);
        for(int index = 0; index < 15; index++) {
            HeartbeatEvent heartbeatEvent = new HeartbeatEvent(p);
            kieSession.insert(heartbeatEvent);
        }
        for(int index = 0; index < 13; index++) {
            UrinEvent urinEvent = new UrinEvent(1, p);
            kieSession.insert(urinEvent);
            clock.advanceTime(10, TimeUnit.SECONDS);
            int fired = kieSession.fireAllRules();
            assertEquals(0L, fired);
        }

        kieSession.getAgenda().getAgendaGroup("dialysis-agenda").setFocus();
        clock.advanceTime(12, TimeUnit.HOURS);
        int fired = kieSession.fireAllRules();
        assertEquals(1L, fired);
        Collection<?> coll = kieSession.getObjects(new ClassObjectFilter(EmergencyDialysisMessageEvent.class));
        assertEquals(1L, coll.size());
    }

    private void realTimeClockTest(KieSession kieSession){
        Thread t = new Thread() {
            @Override
            public void run() {
                Patient p = new Patient(1L, "Sandra");
                Disease disease = new Disease();
                disease.setName("Hronicna bubrezna bolest");
                MedicalRecord medicalRecord = new MedicalRecord();
                medicalRecord.setDisease(disease);
                p.getRecords().add(medicalRecord);
                kieSession.insert(p);
                for(int index = 0; index < 15; index++) {
                    HeartbeatEvent heartbeatEvent = new HeartbeatEvent(p);
                    kieSession.insert(heartbeatEvent);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for(int index = 0; index < 13; index++) {
                    UrinEvent urinEvent = new UrinEvent(1, p);
                    kieSession.insert(urinEvent);
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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kieSession.getAgenda().getAgendaGroup("dialysis-agenda").setFocus();
        kieSession.fireUntilHalt();
        Collection<?> coll = kieSession.getObjects(new ClassObjectFilter(EmergencyDialysisMessageEvent.class));
        assertEquals(1L, coll.size());
    }
}
