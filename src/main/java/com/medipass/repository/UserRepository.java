package com.medipass.repository;

import com.medipass.entity.User;
import com.medipass.enums.Role;
import com.medipass.enums.UserStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByRole(Role role);
    List<User> findByEstado(UserStatus estado);
    List<User> findByRoleAndEstado(Role role, UserStatus estado);
    long countByEstado(UserStatus estado);
    long countByRole(Role role);
    long countByRoleAndEstado(Role role, UserStatus estado);
    List<User> findByRoleAndFechaCreacionBetween(Role role, java.time.LocalDateTime from, java.time.LocalDateTime to);
}
