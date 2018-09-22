package com.example.sbnz;

import com.example.sbnz.events.HeartRhythmMessageEvent;
import com.example.sbnz.events.HeartbeatEvent;
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

public class HeartRhythmTest {

    @Test
    public void testHeartbeat(){

        KieServices kieServices = KieServices.Factory.get();
        KieContainer
                kContainer = kieServices.newKieContainer(kieServices.newReleaseId("drools-spring-v2", "drools-spring-v2-kjar", "0.0.1-SNAPSHOT"));


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
        Patient p = new Patient(1L, "Sandra");
        SessionPseudoClock clock = kieSession.getSessionClock();
        for (int index = 0; index < 30; index++) {
            HeartbeatEvent heartbeatEvent = new HeartbeatEvent( p);
            kieSession.insert(heartbeatEvent);
        }
        kieSession.getAgenda().getAgendaGroup("heart-rhythm-agenda").setFocus();
        clock.advanceTime(10, TimeUnit.SECONDS);
        int fired = kieSession.fireAllRules();
        assertEquals(1L, fired);
        Collection<?> coll = kieSession.getObjects(new ClassObjectFilter(HeartRhythmMessageEvent.class));
        assertEquals(1,coll.size());
    }

    private void realTimeClockTest(KieSession kieSession){
        Thread t = new Thread() {
            @Override
            public void run() {
                Patient p = new Patient(1L, "Sandra");
                for(int index = 0; index < 30; index++){
                    HeartbeatEvent heartbeatEvent = new HeartbeatEvent(p);
                    kieSession.insert(heartbeatEvent);
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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kieSession.getAgenda().getAgendaGroup("heart-rhythm-agenda").setFocus();
        kieSession.fireUntilHalt();
        Collection<?> coll = kieSession.getObjects(new ClassObjectFilter(HeartRhythmMessageEvent.class));
        assertEquals(1L, coll.size());
    }
}

