package com.wit.bank_auth.dao;

import com.wit.bank_auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberDao extends JpaRepository<Member, Integer> {
    @Query("SELECT m FROM Member u WHERE m.email=:email")
        // JPQL (javadaki yazım şekli)=> SELECT * FROM member WHERE email=EmailParametresi
    Optional<Member> findByEmail(String email);

}
