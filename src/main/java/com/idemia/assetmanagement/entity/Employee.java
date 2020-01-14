package com.idemia.assetmanagement.entity;

import com.sun.javafx.beans.IDProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "employee")
    private Set<Asset> asset;

}
