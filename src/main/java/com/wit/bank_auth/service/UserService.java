package com.wit.bank_auth.service;

import com.wit.bank_auth.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService implements UserDetailsService {
    private MemberDao memberDao;
    @Autowired
    public UserService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override  // userName eşleşmesini kontrol eder. securityConfig => AuthenticationManager methoduna password eşleşmesine baksın diye yönlendirir.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberDao.findByEmail(username)
                .orElseThrow(() -> {
                    System.out.println("User credentials are not valid.");
                    return new UsernameNotFoundException("User credentials are not valid.");  // exceptions package'de yakalasaydı döndürebilirdik, burdan hatayı console 'a döndürmedi. 26. satırı ekledik hata mjı dönsün diye
                });
    }
}
