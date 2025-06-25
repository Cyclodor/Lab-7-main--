package com.example.studentapi.controller;

import com.example.studentapi.model.Course;
import com.example.studentapi.service.CourseService;
import com.example.studentapi.service.RequestCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private RequestCounterService requestCounterService;

    @GetMapping
    public List<Course> getAllCourses() {
        requestCounterService.incrementAndGet();
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        requestCounterService.incrementAndGet();
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        requestCounterService.incrementAndGet();
        return courseService.saveCourse(course);
    }

    @PostMapping("/bulk")
    public List<Course> createCoursesBulk(@RequestBody List<Course> courses) {
        requestCounterService.incrementAndGet();
        return courseService.saveCourses(courses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        requestCounterService.incrementAndGet();
        return courseService.getCourseById(id)
                .map(existingCourse -> {
                    course.setId(id);
                    return ResponseEntity.ok(courseService.saveCourse(course));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        requestCounterService.incrementAndGet();
        return courseService.getCourseById(id)
                .map(course -> {
                    courseService.deleteCourse(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Course>> getCoursesByNameContaining(@RequestParam String courseName) {
        requestCounterService.incrementAndGet();
        List<Course> courses = courseService.getCoursesByNameContaining(courseName);
        return ResponseEntity.ok(courses);
    }
} 