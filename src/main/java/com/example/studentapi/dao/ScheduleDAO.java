package com.example.studentapi.dao;

import com.example.studentapi.dto.BsuirGroupDto;
import com.example.studentapi.model.Course;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ScheduleDAO {
    private final RestTemplate restTemplate;

    public ScheduleDAO() {
        this.restTemplate = new RestTemplate();
    }

    public List<Course> getStudentCourses() {
        String url = "https://iis.bsuir.by/api/v1/student-groups";
        try {
            BsuirGroupDto[] bsuirGroups = restTemplate.getForObject(url, BsuirGroupDto[].class);
            if (bsuirGroups != null) {
                return Arrays.stream(bsuirGroups)
                    .map(this::convertToCourse)
                    .collect(Collectors.toList());
            }
            return Collections.emptyList();
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private Course convertToCourse(BsuirGroupDto bsuirGroup) {
        Course course = new Course();
        course.setCourseName(bsuirGroup.getName());
        course.setDepartment(bsuirGroup.getFaculty());
        return course;
    }
}