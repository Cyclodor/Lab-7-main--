package com.example.studentapi.integration;

import com.example.studentapi.model.Course;
import com.example.studentapi.model.Learner;
import com.example.studentapi.repository.CourseRepository;
import com.example.studentapi.repository.LearnerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class StudentApiIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LearnerRepository learnerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testCreateCourseAndLearner_ShouldWorkCorrectly() throws Exception {
        // Create course
        Course course = new Course();
        course.setCourseName("Java Programming");
        course.setDepartment("Computer Science");

        String courseJson = objectMapper.writeValueAsString(course);

        String courseResponse = mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(courseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value("Java Programming"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Course savedCourse = objectMapper.readValue(courseResponse, Course.class);

        // Create learner
        Learner learner = new Learner();
        learner.setFullName("John Doe");
        learner.setGivenName("John");
        learner.setFamilyName("Doe");
        learner.setEnrollmentNumber("12345");
        learner.setCourse(savedCourse);

        String learnerJson = objectMapper.writeValueAsString(learner);

        mockMvc.perform(post("/api/learners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(learnerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("John Doe"))
                .andExpect(jsonPath("$.course.id").value(savedCourse.getId()));
    }

    @Test
    void testSearchLearnersByDepartment_ShouldReturnLearners() throws Exception {
        // Create course
        Course course = new Course();
        course.setCourseName("Java Programming");
        course.setDepartment("Computer Science");
        Course savedCourse = courseRepository.save(course);

        // Create learner
        Learner learner = new Learner();
        learner.setFullName("John Doe");
        learner.setGivenName("John");
        learner.setFamilyName("Doe");
        learner.setEnrollmentNumber("12345");
        learner.setCourse(savedCourse);
        learnerRepository.save(learner);

        // Test search
        mockMvc.perform(get("/api/learners/by-department")
                .param("department", "Computer Science"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("John Doe"));
    }

    @Test
    void testSearchCoursesByName_ShouldReturnCourses() throws Exception {
        // Create course
        Course course = new Course();
        course.setCourseName("Java Programming");
        course.setDepartment("Computer Science");
        courseRepository.save(course);

        // Test search
        mockMvc.perform(get("/api/courses/search")
                .param("courseName", "Java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseName").value("Java Programming"));
    }

    @Test
    void testCacheOperations_ShouldWorkCorrectly() throws Exception {
        // Test cache info
        mockMvc.perform(get("/api/cache/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size").exists());

        // Test clear cache
        mockMvc.perform(delete("/api/cache/clear"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cache cleared successfully"));
    }
} 