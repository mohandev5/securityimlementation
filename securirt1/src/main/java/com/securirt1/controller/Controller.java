package com.securirt1.controller;
import com.securirt1.config.Security;
import com.securirt1.dto.AuthRequests;
import com.securirt1.entity.User;
import com.securirt1.repo.UserRepo;
import com.securirt1.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
     private UserRepo userRepo;
    @Autowired
     private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/welcome")
    public String heelo(){
        return "Hello,Welcome you all";
    }

    @GetMapping("/data")
    public String showdata(){
        return "It is a private data,you dont have any access";
    }

   // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }


    @PostMapping("/new")
    public ResponseEntity<String>createUser(@RequestBody User user){
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        userRepo.save(user);
        return ResponseEntity.ok("user created successfully");
    }

    @PostMapping("/authenticate")
    public String authenticateJwt(@RequestBody AuthRequests authRequests){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequests.getName(), authRequests.getPassword()));
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequests.getName());
        }
        else {
            throw new UsernameNotFoundException(("invalid username"));
        }
    }
}
