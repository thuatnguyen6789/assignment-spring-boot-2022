package com.example.springbootthuatdev.service;

import com.example.springbootthuatdev.entity.Account;
import com.example.springbootthuatdev.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Transactional
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // tim account theo username trong bang accounts
        Account account = accountRepository.findAccountByUsername(username);
        // tao danh sach quyen (mot nguoi dung co the co nhieu quyen)
        // co the tao ra bang quyen rieng mapping n-n voi accounts
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if(account.getRole() == 1) {
            // them quyen theo truong role trong account
            authorities.add(new SimpleGrantedAuthority("user"));
        } else if (account.getRole() == 2) {
            authorities.add(new SimpleGrantedAuthority("admin"));
        }
        // tao doi tuong user detail theo thong tin username, password va quyen duoc lay ra o tren
        // trong do password la pass dc ma hoa
        return new User(account.getUsername(), account.getPasswordHash(), authorities);
    }
}
