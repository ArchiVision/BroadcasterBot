package com.archivision.broadcaster.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString(exclude = {"topics"})
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "telegram_user_id")
    private Long telegramUserId;

    @Setter(AccessLevel.PRIVATE)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_topic",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private Set<Topic> topics = new HashSet<>();

    public boolean addTopic(Topic topic) {
        if (!topics.contains(topic)) {
            topics.add(topic);
            topic.getUsers().add(this);
            return true;
        }
        return false;
    }

    public void removeTopic(String topicName) {
        Topic topic = new Topic();
        topic.setName(topicName);
        topics.remove(topic);
        topic.getUsers().remove(this);
    }

    public void removeTopic(Topic topic) {
        topics.remove(topic);
        topic.getUsers().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
