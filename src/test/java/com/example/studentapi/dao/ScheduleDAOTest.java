package com.example.studentapi.dao;

import com.example.studentapi.dto.BsuirGroupDto;
import com.example.studentapi.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class ScheduleDAOTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ScheduleDAO scheduleDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStudentCourses_Success() {
        BsuirGroupDto[] bsuirGroups = new BsuirGroupDto[2];
        
        BsuirGroupDto group1 = new BsuirGroupDto();
        group1.setName("Java Programming");
        group1.setFaculty("Computer Science");
        bsuirGroups[0] = group1;
        
        BsuirGroupDto group2 = new BsuirGroupDto();
        group2.setName("Python Development");
        group2.setFaculty("Information Technology");
        bsuirGroups[1] = group2;
        
        when(restTemplate.getForObject(any(String.class), eq(BsuirGroupDto[].class)))
            .thenReturn(bsuirGroups);

        List<Course> result = scheduleDAO.getStudentCourses();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Java Programming", result.get(0).getCourseName());
        assertEquals("Computer Science", result.get(0).getDepartment());
        assertEquals("Python Development", result.get(1).getCourseName());
        assertEquals("Information Technology", result.get(1).getDepartment());
    }

    @Test
    void testGetStudentCourses_EmptyResponse() {
        when(restTemplate.getForObject(any(String.class), eq(BsuirGroupDto[].class)))
            .thenReturn(new BsuirGroupDto[0]);

        List<Course> result = scheduleDAO.getStudentCourses();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetStudentCourses_NullResponse() {
        when(restTemplate.getForObject(any(String.class), eq(BsuirGroupDto[].class)))
            .thenReturn(null);

        List<Course> result = scheduleDAO.getStudentCourses();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
} 