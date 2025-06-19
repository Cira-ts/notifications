package com.tsira.notifications.security.token.repository;

import com.tsira.notifications.security.token.repository.entity.AdminUserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdminUserTokenRepository extends JpaRepository<AdminUserToken, Long> {
    @Query("""
            select t
            from AdminUserToken t
            inner join AppUser u on t.user.id = u.id
            where u.id = :id and (t.expired = false or t.revoked = false)
            """)
    List<AdminUserToken> findAllValidTokenByUser(Long id);

    Optional<AdminUserToken> findByToken(String token);
}
