package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.SubjectDto;
import com.openclassrooms.mddapi.models.Subject;

import java.util.List;

public interface SubjectService {
    List<SubjectDto> getAll();
    Subject getById(Long subjectId);
}
