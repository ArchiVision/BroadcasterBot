package com.archivision.broadcaster.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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