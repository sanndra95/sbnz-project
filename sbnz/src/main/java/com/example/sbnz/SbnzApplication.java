package com.example.sbnz;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class SbnzApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbnzApplication.class, args);
	}

	@Bean
	public KieContainer kieContainer() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("drools-spring-v2", "drools-spring-v2-kjar", "0.0.1-SNAPSHOT"));

		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(10_000);

		return kContainer;
	}
}
