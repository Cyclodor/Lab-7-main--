package com.example.studentapi.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BsuirGroupDtoTest {

    @Test
    void testBsuirGroupDtoCreation() {
        BsuirGroupDto dto = new BsuirGroupDto();
        
        dto.setId(1L);
        dto.setName("Test Group");
        dto.setFaculty("Computer Science");
        dto.setSpeciality("Software Engineering");
        dto.setCourse(3);
        dto.setStudentsCount(25);
        dto.setActive(true);
        
        assertEquals(1L, dto.getId());
        assertEquals("Test Group", dto.getName());
        assertEquals("Computer Science", dto.getFaculty());
        assertEquals("Software Engineering", dto.getSpeciality());
        assertEquals(3, dto.getCourse());
        assertEquals(25, dto.getStudentsCount());
        assertTrue(dto.getActive());
    }

    @Test
    void testBsuirGroupDtoEquality() {
        BsuirGroupDto dto1 = new BsuirGroupDto();
        dto1.setId(1L);
        dto1.setName("Test Group");
        
        BsuirGroupDto dto2 = new BsuirGroupDto();
        dto2.setId(1L);
        dto2.setName("Test Group");
        
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
} 