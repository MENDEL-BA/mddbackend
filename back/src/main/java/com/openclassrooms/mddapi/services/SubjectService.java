package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> getAll();
    Subject getById(Long subjectId);
}
