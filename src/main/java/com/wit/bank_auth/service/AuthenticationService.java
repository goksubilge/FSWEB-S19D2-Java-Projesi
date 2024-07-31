package com.wit.bank_auth.service;

import com.wit.bank_auth.dao.MemberDao;
import com.wit.bank_auth.dao.RoleDao;
import com.wit.bank_auth.entity.Member;
import com.wit.bank_auth.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.management.relation.RoleList;
import java.util.*;

public class AuthenticationService {
    private MemberDao memberDao;
    private RoleDao roleDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(MemberDao memberDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    // Default min role
    public Member register(String email, String password) {
        //Kullanıcı varsa:
        Optional<Member> foundMember = memberDao.findByEmail(email);
        if (foundMember.isPresent()) {
            throw new RuntimeException("User with given email already exist, please login: " + email);
        }

        // kullanıcı yoksa kayda devam et:
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleDao.findByAuthority("USER").get(); // "USER" authority 'sini bulamazsa diye önlem almalıyım
        // Role userRole = roleDao.findByAuthority("ADMIN").get();
        List<Role> roleList = new ArrayList<>();
        roleList.add(userRole);
        // roleList.add(adminRole);

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(encodedPassword);
        member.setAuthorities(roleList);
        return memberDao.save(member);
    }
}
