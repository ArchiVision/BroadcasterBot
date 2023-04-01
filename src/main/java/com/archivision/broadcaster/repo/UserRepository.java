package com.archivision.broadcaster.repo;


import com.archivision.broadcaster.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByTelegramUserId(Long userId);

    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN u.topics t " +
            "WHERE t.name IN :topicNames")
    Set<User> findUsersByTopicNames(@Param("topicNames") Set<String> topicNames);
}
