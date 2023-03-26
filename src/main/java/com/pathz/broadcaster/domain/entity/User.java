package com.pathz.broadcaster.domain.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "telegram_user_id")
    private Long telegramUserId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Topic> topics = new HashSet<>();

    public void addTopic(Topic topic) {
        topics.add(topic);
        topic.setUser(this);
    }

    public void removeTopic(Topic topic) {
        topics.remove(topic);
        topic.setUser(null);
    }
}
