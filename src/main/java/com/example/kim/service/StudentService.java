package com.example.kim.service;

import com.example.kim.dto.StudentCreateRequest;
import com.example.kim.dto.StudentResponse;
import com.example.kim.dto.StudentUpdateRequest;
import com.example.kim.entity.Profile;
import com.example.kim.entity.Student;
import com.example.kim.exception.BusinessException;
import com.example.kim.exception.DuplicateEmailException;
import com.example.kim.exception.StudentNotFoundException;
import com.example.kim.mapper.StudentMapper;
import com.example.kim.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Transactional
    public StudentResponse create(StudentCreateRequest request) {
        validateUniqueEmail(request.getEmail());

        Student student = studentMapper.toEntity(request);
        Student saved = studentRepository.save(student);
        return studentMapper.toResponse(saved);
    }

    @Transactional
    public StudentResponse update(Long id, StudentUpdateRequest request) {
        Student student = findStudentOrThrow(id);

        if (request.getEmail() != null && !request.getEmail().equals(student.getEmail())) {
            validateUniqueEmail(request.getEmail());
            student.setEmail(request.getEmail());
        }

        Optional.ofNullable(request.getFirstName()).ifPresent(student::setFirstName);
        Optional.ofNullable(request.getLastName()).ifPresent(student::setLastName);
        Optional.ofNullable(request.getAge()).ifPresent(student::setAge);

        Profile profile = student.getProfile();
        if (profile != null) {
            Optional.ofNullable(request.getPhone()).ifPresent(profile::setPhone);
            Optional.ofNullable(request.getAddress()).ifPresent(profile::setAddress);
        }

        Student updated = studentRepository.save(student);
        return studentMapper.toResponse(updated);
    }

    @Transactional(readOnly = true)
    public StudentResponse getById(Long id) {
        Student student = findStudentOrThrow(id);
        return studentMapper.toResponse(student);
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getAll() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toResponse)
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        Student student = findStudentOrThrow(id);
        studentRepository.delete(student);
    }

    private Student findStudentOrThrow(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    private void validateUniqueEmail(String email) {
        if (studentRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email);
        }
    }
}
