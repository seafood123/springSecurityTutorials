package com.excercise.jwtTutorial.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="AUTHORITY")
public class Authority {

    @Id
    @Column(name="authority_name",length=50)
    private String authorityName;
}
