package com.example.kim.service;

import com.example.kim.dto.StudentCreateRequest;
import com.example.kim.dto.StudentResponse;
import com.example.kim.dto.StudentUpdateRequest;

import java.util.List;

public interface StudentService {

    StudentResponse create(StudentCreateRequest request);

    StudentResponse update(Long id, StudentUpdateRequest request);

    StudentResponse getById(Long id);

    List<StudentResponse> getAll();

    void delete(Long id);
}
