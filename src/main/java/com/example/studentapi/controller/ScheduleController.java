package com.example.studentapi.controller;

import com.example.studentapi.model.Course;
import com.example.studentapi.service.ScheduleService;
import com.example.studentapi.service.RequestCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student-groups")
public class ScheduleController {
    
    @Autowired
    private ScheduleService scheduleService;
    
    @Autowired
    private RequestCounterService requestCounterService;
    
    @GetMapping
    public ResponseEntity<List<Course>> getStudentGroups() {
        requestCounterService.incrementAndGet();
        List<Course> courses = scheduleService.getStudentCourses();
        return ResponseEntity.ok(courses);
    }
} 