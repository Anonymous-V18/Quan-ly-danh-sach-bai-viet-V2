package com.anonymous.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryEntity extends BaseEntity {

    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category")
    private List<NewsEntity> news = new ArrayList<>();

}
