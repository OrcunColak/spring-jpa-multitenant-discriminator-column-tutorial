package com.colak.springtutorial.repository;

import com.colak.springtutorial.filter.TenantContext;
import com.colak.springtutorial.jpa.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @BeforeEach
    public void setup() {
        TenantContext.clear();
    }

    @Test
    public void testTenant1Data() {
        TenantContext.setCurrentTenant("tenant_1");

        List<Person> persons = repository.findAll();
        assertThat(persons).hasSize(2);
        assertThat(persons.getFirst().getName()).isEqualTo("John Doe");
    }

    @Test
    public void testTenant2Data() {
        TenantContext.setCurrentTenant("tenant_2");

        List<Person> persons = repository.findAll();
        assertThat(persons).hasSize(2);
        assertThat(persons.getFirst().getName()).isEqualTo("Bob Brown");
    }

}