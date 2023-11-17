package com.openclassrooms.mddapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "SUBJECTS", uniqueConstraints = {
        @UniqueConstraint(columnNames = "title")
})
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "title")
    @Size(max = 255)
    private String title;

    @NotBlank
    @Column(name = "description", length = 2000)
    @Size(max = 500)
    private String description;

    @ManyToMany(mappedBy = "subjects")
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject subject)) return false;
        return Objects.equals(id, subject.id) && Objects.equals(title, subject.title) && Objects.equals(description, subject.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description);
    }
}
