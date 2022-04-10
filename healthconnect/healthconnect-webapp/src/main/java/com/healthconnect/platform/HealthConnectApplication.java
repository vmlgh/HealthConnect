package com.healthconnect.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.healthconnect.platform.repository")
@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
@EntityScan("com.healthconnect.platform")
public class HealthConnectApplication extends SpringBootServletInitializer {

	private static Class applicationClass = HealthConnectApplication.class;

	public static void main(String[] args) {
		SpringApplication.run(HealthConnectApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}
}
