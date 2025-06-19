package com.tsira.notifications.security.user.repository;

import com.tsira.notifications.Integration.users.dto.IntegrationUsersGetDto;
import com.tsira.notifications.security.user.repository.entity.AppUser;
import com.tsira.notifications.security.user.repository.enums.UserStatus;
import com.tsira.notifications.security.user.repository.enums.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmailAndStatus(String email, UserStatus userStatus);

    AppUser findByEmail(String username);

    @Query("""
            SELECT
                u.id            AS id,
                u.firstName     AS firstName,
                u.lastName      AS lastName,
                u.email         AS email,
                u.status        AS status
            FROM AppUser u
            WHERE u.userType = :userType
                  AND (:search is null
                     or u.firstName ilike %:search%
                     or u.lastName ilike %:search%
                     or u.email ilike %:search%)
            """)
    Page<IntegrationUsersGetDto> getIntegrationUsers(Pageable pageable, UserType userType, String search);
}
