package com.anonymous.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "new")
@Data
@EqualsAndHashCode(callSuper = true)
public class NewsEntity extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "shortdescription")
    private String shortDescription;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

}
