package com.example.studentapi.controller;

import com.example.studentapi.model.Learner;
import com.example.studentapi.service.LearnerService;
import com.example.studentapi.model.Course;
import com.example.studentapi.service.CourseService;
import com.example.studentapi.service.RequestCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learners")
public class LearnerController {
    @Autowired
    private LearnerService learnerService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private RequestCounterService requestCounterService;

    @GetMapping
    public List<Learner> getAllLearners() {
        requestCounterService.incrementAndGet();
        return learnerService.getAllLearners();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Learner> getLearnerById(@PathVariable Long id) {
        requestCounterService.incrementAndGet();
        return learnerService.getLearnerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createLearner(@RequestBody Learner learner) {
        requestCounterService.incrementAndGet();
        if (learner.getCourse() == null || learner.getCourse().getId() == null ||
            courseService.getCourseById(learner.getCourse().getId()).isEmpty()) {
            return ResponseEntity.badRequest().body("Course with given id does not exist");
        }
        return ResponseEntity.ok(learnerService.saveLearner(learner));
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> createLearnersBulk(@RequestBody List<Learner> learners) {
        requestCounterService.incrementAndGet();
        for (Learner learner : learners) {
            if (learner.getCourse() == null || learner.getCourse().getId() == null ||
                courseService.getCourseById(learner.getCourse().getId()).isEmpty()) {
                return ResponseEntity.badRequest().body("Course with given id does not exist for one or more learners");
            }
        }
        return ResponseEntity.ok(learnerService.saveLearners(learners));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Learner> updateLearner(@PathVariable Long id, @RequestBody Learner learner) {
        requestCounterService.incrementAndGet();
        return learnerService.getLearnerById(id)
                .map(existingLearner -> {
                    learner.setId(id);
                    return ResponseEntity.ok(learnerService.saveLearner(learner));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLearner(@PathVariable Long id) {
        requestCounterService.incrementAndGet();
        return learnerService.getLearnerById(id)
                .map(learner -> {
                    learnerService.deleteLearner(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-department")
    public ResponseEntity<List<Learner>> getLearnersByCourseDepartment(@RequestParam String department) {
        requestCounterService.incrementAndGet();
        List<Learner> learners = learnerService.getLearnersByCourseDepartment(department);
        return ResponseEntity.ok(learners);
    }
} 