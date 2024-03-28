package com.gplayus.backend.domain;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Base {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected LocalDateTime createdDate;

    @CreatedBy
    @Column(length = 100, nullable = false, updatable = false)
    protected String createdBy;

    @LastModifiedDate
    @Column(nullable = false)
    protected LocalDateTime modifiedDate;

    @LastModifiedBy
    @Column(length = 100, nullable = false)
    protected String modifiedBy;
}
