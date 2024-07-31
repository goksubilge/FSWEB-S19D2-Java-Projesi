package com.wit.bank_auth.dao;

import com.wit.bank_auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleDao extends JpaRepository<Role, Integer> {
    @Query("SELECT r FROM role WHERE r.authority=authority")
    Optional<Role> findByAuthority(String authority);
}
