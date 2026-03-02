package com.example.kim.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private LocalDateTime createdAt;
    private ProfileResponse profile;
}
