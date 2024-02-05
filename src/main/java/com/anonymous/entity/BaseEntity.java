package com.anonymous.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "createdby")
    @CreatedBy
    private String createdBy;
    @Column(name = "createddate")
    @CreatedDate
    private Date createdDate;
    @Column(name = "modifiedby")
    @LastModifiedBy
    private String modifiedBy;
    @Column(name = "modifieddate")
    @LastModifiedDate
    private Date modifiedDate;

}
