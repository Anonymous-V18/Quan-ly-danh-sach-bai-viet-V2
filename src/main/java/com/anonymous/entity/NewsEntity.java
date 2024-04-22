package com.anonymous.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Entity
@Table(name = "new")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsEntity extends BaseEntity {

    String title;
    String thumbnail;
    String shortDescription;
    String content;

    @ManyToOne
    @JoinColumn(name = "category_id")
    CategoryEntity category;

}
