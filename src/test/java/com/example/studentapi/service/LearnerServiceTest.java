package com.example.studentapi.service;

import com.example.studentapi.model.Learner;
import com.example.studentapi.repository.LearnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LearnerServiceTest {

    @Mock
    private LearnerRepository learnerRepository;
    @Mock
    private CacheService cacheService;
    @InjectMocks
    private LearnerService learnerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveLearners_ShouldSaveAll() {
        Learner l1 = new Learner();
        l1.setFullName("Ivan Ivanov");
        Learner l2 = new Learner();
        l2.setFullName("Petr Petrov");
        when(learnerRepository.save(any(Learner.class))).thenAnswer(invocation -> invocation.getArgument(0));
        List<Learner> result = learnerService.saveLearners(List.of(l1, l2));
        assertEquals(2, result.size());
        verify(learnerRepository, times(2)).save(any(Learner.class));
    }

    @Test
    void saveLearners_ShouldReturnEmptyList_WhenInputIsNull() {
        List<Learner> result = learnerService.saveLearners(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(learnerRepository, never()).save(any());
    }

    @Test
    void saveLearners_ShouldReturnEmptyList_WhenInputIsEmpty() {
        List<Learner> result = learnerService.saveLearners(List.of());
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(learnerRepository, never()).save(any());
    }
} 