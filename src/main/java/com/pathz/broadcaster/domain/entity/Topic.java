package com.pathz.broadcaster.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "name")
@Entity
@ToString(exclude = {"users"})
@Table(name = "topic")
public class Topic {
    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true)
    private String name;

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(mappedBy = "topics")
    private Set<User> users = new HashSet<>();
}