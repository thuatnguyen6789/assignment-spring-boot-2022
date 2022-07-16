package com.example.springbootthuatdev.entity;

import com.example.springbootthuatdev.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")

public class User extends BaseEntity {
    @Id
    private int id;
    private String username;
}
