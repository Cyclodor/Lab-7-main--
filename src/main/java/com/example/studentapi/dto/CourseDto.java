package com.example.studentapi.dto;

import lombok.Data;

/**
 * DTO класс для передачи данных о курсах
 */
@Data
public class CourseDto {
    private Long id;
    private String courseName;
    private String department;
    private Integer learnersCount;
} 