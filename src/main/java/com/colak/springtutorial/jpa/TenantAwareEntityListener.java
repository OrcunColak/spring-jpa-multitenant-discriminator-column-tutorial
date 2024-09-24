package com.colak.springtutorial.jpa;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component

@RequiredArgsConstructor
@Slf4j
public class TenantAwareEntityListener {

    private final CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolver;


    @PrePersist
    @PreUpdate
    @PreRemove
    @PostLoad
    public void checkTenant(Object entity) {
        if (!(entity instanceof TenantAware tenantAware)) {
            return;
        }
        String currentTenantIdentifier = currentTenantIdentifierResolver.resolveCurrentTenantIdentifier();

        if (!currentTenantIdentifier.equals(tenantAware.getTenantId())) {
            log.warn("Entity's tenantId does not match current tenant: currentTenantIdentifier={}, entity={}", currentTenantIdentifier, entity);
            throw new IllegalArgumentException("Entity's tenantId does not match current tenant");
        }
    }
}
