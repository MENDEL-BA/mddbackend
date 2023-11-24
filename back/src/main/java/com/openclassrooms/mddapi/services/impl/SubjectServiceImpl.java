package com.openclassrooms.mddapi.services.impl;

import com.openclassrooms.mddapi.dtos.SubjectDto;
import com.openclassrooms.mddapi.models.Subject;
import com.openclassrooms.mddapi.repositories.SubjectRepository;
import com.openclassrooms.mddapi.repositories.SubscriptionRepository;
import com.openclassrooms.mddapi.services.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ModelMapper mapper;
    public SubjectServiceImpl(SubjectRepository subjectRepository, SubscriptionRepository subscriptionRepository, ModelMapper mapper) {
        this.subjectRepository = subjectRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.mapper = mapper;
    }

    @Override
    public List<SubjectDto> getAll() {
        List<Subject> subjects = subjectRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION, "title"));

        return subjects.stream()
                .map(subject -> {
                    boolean isSubscriptionActive = subject.getUsers().stream()
                            .anyMatch(user -> subscriptionRepository.existsByUserIdAndSubjectId(user.getId(), subject.getId()));
                    SubjectDto subjectDto = this.mapper.map(subject, SubjectDto.class);
                    subjectDto.setSubscription(isSubscriptionActive);
                    return subjectDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Subject getById(Long subjectId) {
        return this.subjectRepository.findById(subjectId).orElse(null);
    }
}
