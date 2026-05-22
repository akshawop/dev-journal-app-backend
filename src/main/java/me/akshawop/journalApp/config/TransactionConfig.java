package me.akshawop.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    // postgres transaction manager
    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager postgresTransactionManager(EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}
