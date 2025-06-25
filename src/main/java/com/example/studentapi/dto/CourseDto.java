package com.example.studentapi.dto;

import lombok.Data;

@Data
public class CourseDto {
    private Long id;
    private String courseName;
    private String department;
    private Integer learnersCount;
} 