package com.idemia.assetmanagement.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@ToString(exclude = "asset")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    @Id
    private String empId;

    private String firstName;

    private String lastName;

    private String location;

    private String costCenter;

    private String productLine;

    private String jobRole;

    private String technology;

}
