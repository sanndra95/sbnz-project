package com.example.sbnz;

import com.example.sbnz.events.OxygenLevelEvent;
import com.example.sbnz.events.OxygenLevelMessageEvent;
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

public class OxygenTest {

    @Test
    public void testOxygen(){

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

        realTimeTest(kSession1);
        pseudoClockTest(kSession2);

    }


    private void pseudoClockTest(KieSession kieSession){
        Patient p = new Patient(1L, "Sandra");
        SessionPseudoClock clock = kieSession.getSessionClock();
        for (int index = 0; index < 10; index++) {
            OxygenLevelEvent oxygenEvent = new OxygenLevelEvent( p, 65);
            kieSession.insert(oxygenEvent);
            clock.advanceTime(10, TimeUnit.MILLISECONDS);
            int fired = kieSession.fireAllRules();
            assertEquals(fired, 0L);
        }
        kieSession.getAgenda().getAgendaGroup("oxygen-agenda").setFocus();
        clock.advanceTime(15, TimeUnit.MINUTES);
        int fired = kieSession.fireAllRules();
        assertEquals(fired, 1L);
        Collection<?> coll = kieSession.getObjects(new ClassObjectFilter(OxygenLevelMessageEvent.class));
        assertEquals(1,coll.size());
    }

    private void realTimeTest(KieSession kieSession){
        Thread t = new Thread(){
            @Override
            public void run() {
                Patient p = new Patient(1L, "Sandra");
                for (int index = 0; index < 10; index++) {
                    OxygenLevelEvent oxygenEvent = new OxygenLevelEvent(p, 65);
                    System.out.println(oxygenEvent.getExecutionTime());
                    kieSession.insert(oxygenEvent);
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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kieSession.getAgenda().getAgendaGroup("oxygen-agenda").setFocus();
        kieSession.fireUntilHalt();
        Collection<?> coll = kieSession.getObjects(new ClassObjectFilter(OxygenLevelMessageEvent.class));
        assertEquals(1L, coll.size());

    }
}

