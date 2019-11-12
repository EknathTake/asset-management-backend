package com.idemia.assetmanagement.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    private String empId;

    private String firstName;

    private String lastName;

    private String location;

    private String costCenter;

    private String productLine;

    private String jobRole;

    private String technology;

}
