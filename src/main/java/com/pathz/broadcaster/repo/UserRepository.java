package com.pathz.broadcaster.repo;


import com.pathz.broadcaster.domain.entity.User;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users WHERE telegram_user_id=?", nativeQuery = true)
    User findByTelegramId(Long telegramId);
}
