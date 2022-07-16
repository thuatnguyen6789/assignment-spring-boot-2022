package com.example.springbootthuatdev.entity;

import com.example.springbootthuatdev.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Id
    private String id;
    private String name;
}
