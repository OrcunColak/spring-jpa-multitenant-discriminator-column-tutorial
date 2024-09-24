package com.colak.springtutorial.jpa;


import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.TenantId;


@Entity
@Table(name = "person")
@EntityListeners(TenantAwareEntityListener.class)

@Getter
public class Person implements TenantAware {
    @Id
    private Long id;
    private String name;
    private Integer age;
    private String email;

    @TenantId
    private String tenantId;
}
