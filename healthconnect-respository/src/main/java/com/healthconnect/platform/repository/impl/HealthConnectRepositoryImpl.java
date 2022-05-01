package com.healthconnect.platform.repository.impl;

import com.healthconnect.platform.repository.HealthConnectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Repository
public class HealthConnectRepositoryImpl implements HealthConnectRepository {

    @Autowired
    private EntityManagerFactory em;

    @Override
    public EntityManager getEntityManager() {
        return em.createEntityManager();
    }
}

