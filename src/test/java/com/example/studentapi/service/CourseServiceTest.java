package com.example.studentapi.service;

import com.example.studentapi.model.Course;
import com.example.studentapi.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private CacheService cacheService;
    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCourses_ShouldSaveAll() {
        Course c1 = new Course();
        c1.setCourseName("Math");
        Course c2 = new Course();
        c2.setCourseName("Physics");
        when(courseRepository.save(any(Course.class))).thenAnswer(invocation -> invocation.getArgument(0));
        List<Course> result = courseService.saveCourses(List.of(c1, c2));
        assertEquals(2, result.size());
        verify(courseRepository, times(2)).save(any(Course.class));
    }

    @Test
    void saveCourses_ShouldReturnEmptyList_WhenInputIsNull() {
        List<Course> result = courseService.saveCourses(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(courseRepository, never()).save(any());
    }

    @Test
    void saveCourses_ShouldReturnEmptyList_WhenInputIsEmpty() {
        List<Course> result = courseService.saveCourses(List.of());
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(courseRepository, never()).save(any());
    }
} 