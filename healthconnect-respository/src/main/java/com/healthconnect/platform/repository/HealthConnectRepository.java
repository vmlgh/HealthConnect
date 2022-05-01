package com.healthconnect.platform.repository;

import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;

@NoRepositoryBean
public interface HealthConnectRepository{

    EntityManager getEntityManager();
}

