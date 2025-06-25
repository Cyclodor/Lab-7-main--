package com.example.studentapi.dto;

import lombok.Data;

@Data
public class LearnerDto {
    private Long id;
    private String fullName;
    private String givenName;
    private String familyName;
    private String enrollmentNumber;
    private CourseDto course;
} 