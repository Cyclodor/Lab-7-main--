package com.example.studentapi.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class BsuirGroupDto {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("faculty")
    private String faculty;
    
    @JsonProperty("speciality")
    private String speciality;
    
    @JsonProperty("course")
    private Integer course;
    
    @JsonProperty("studentsCount")
    private Integer studentsCount;
    
    @JsonProperty("active")
    private Boolean active;
} 