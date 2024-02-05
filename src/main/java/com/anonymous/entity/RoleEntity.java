package com.anonymous.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String code;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users = new ArrayList<>();

}
