package com.openclassrooms.mddapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "MESSAGES")
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @NotBlank
    @Size(max = 500)
    @NotBlank
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @ToString.Exclude
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @ToString.Exclude
    private Post post;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message message1)) return false;
        return Objects.equals(id, message1.id) && Objects.equals(createdAt, message1.createdAt) && Objects.equals(message, message1.message) && Objects.equals(post, message1.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, message, post);
    }
}
