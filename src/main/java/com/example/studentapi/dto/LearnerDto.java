package com.example.studentapi.dto;

import lombok.Data;

/**
 * DTO класс для передачи данных о студентах
 */
@Data
public class LearnerDto {
    private Long id;
    private String fullName;
    private String givenName;
    private String familyName;
    private String enrollmentNumber;
    private CourseDto course;
} 