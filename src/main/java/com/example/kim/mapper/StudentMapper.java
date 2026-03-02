package com.example.kim.mapper;

import com.example.kim.dto.CourseResponse;
import com.example.kim.dto.ProfileResponse;
import com.example.kim.dto.StudentCreateRequest;
import com.example.kim.dto.StudentResponse;
import com.example.kim.entity.Course;
import com.example.kim.entity.Profile;
import com.example.kim.entity.Student;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class StudentMapper {

    public Student toEntity(StudentCreateRequest request) {
        Student student = Student.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .age(request.getAge())
                .createdAt(LocalDateTime.now())
                .build();

        Profile profile = Profile.builder()
                .phone(request.getPhone())
                .address(request.getAddress())
                .student(student)
                .build();
        student.setProfile(profile);

        return student;
    }

    public StudentResponse toResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .age(student.getAge())
                .createdAt(student.getCreatedAt())
                .profile(toProfileResponse(student.getProfile()))
                .courses(toCourseResponses(student.getCourses()))
                .build();
    }

    private ProfileResponse toProfileResponse(Profile profile) {
        if (profile == null) {
            return null;
        }

        return ProfileResponse.builder()
                .id(profile.getId())
                .phone(profile.getPhone())
                .address(profile.getAddress())
                .build();
    }

    private List<CourseResponse> toCourseResponses(List<Course> courses) {
        if (courses == null) {
            return List.of();
        }

        return courses.stream()
                .filter(Objects::nonNull)
                .map(course -> CourseResponse.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .credits(course.getCredits())
                        .build())
                .toList();
    }
}
