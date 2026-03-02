package com.example.kim.repository;

import com.example.kim.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    long countByStudentId(Long studentId);
}
