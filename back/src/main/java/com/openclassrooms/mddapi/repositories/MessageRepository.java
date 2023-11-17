package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}