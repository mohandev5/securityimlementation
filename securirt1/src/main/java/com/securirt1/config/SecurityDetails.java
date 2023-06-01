package com.securirt1.config;

import com.securirt1.entity.User;
import com.securirt1.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class SecurityDetails implements UserDetailsService {


    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        System.out.println("im present "+name);
        //User user = userRepo.findByName(name);

//        System.out.println(user);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found: " + name);
//        }
//        UserDetail userDetails= new UserDetail(user);
//        return new org.springframework.security.core.userdetails.User(
//                user.getName(),
//                user.getPassword(),
//                Collections.emptyList());
        Optional<User>user=userRepo.findByName(name);
        return user.map(UserDetail::new).orElseThrow(()->new UsernameNotFoundException("user not found"+name));

    }
}

