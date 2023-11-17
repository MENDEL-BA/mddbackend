package com.openclassrooms.mddapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "SUBSCRIPTIONS")
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NumberFormat
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @NumberFormat
    @Column(name = "subject_id")
    private Long subjectId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscription that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(subjectId, that.subjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, subjectId);
    }
}
