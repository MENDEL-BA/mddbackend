package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findBySubjectInOrderByCreatedAt(Set<Subject> subjectSet);
}