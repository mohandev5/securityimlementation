package com.securirt1.config;


import com.securirt1.filetr.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Security {

    private final SecurityDetails securityDetails;
    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    public Security(SecurityDetails securityDetails) {
        this.securityDetails = securityDetails;
    }
//    private UserDetailsService SecurityDetails;

//    private UserDetailsService UserDetails;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
////        UserDetails user = User.withUsername("mohan")
////                .password(passwordEncoder().encode("mohan123"))
////                .roles("USER")
////                .build();
////        UserDetails admin = User.withUsername("virat")
////                .password(passwordEncoder.encode("virat123"))
////                .roles("ADMIN")
////                .build();
////        return  new InMemoryUserDetailsManager(user,admin);
//        return SecurityDetails;
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new SecurityDetails();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
           return httpSecurity.csrf().disable().authorizeRequests()
                   .requestMatchers("/welcome","/new","/authenticate").permitAll()
                   .and()
                   .authorizeRequests()
                   .requestMatchers("/data","/hello").authenticated()
                   .and().sessionManagement()
                   .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                   .and().authenticationProvider(authenticationProvider())
                   .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();

                   //.httpBasic(Customizer.withDefaults()).build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//         httpSecurity.csrf(csr->csr.disable())
//               .authorizeRequests(
//                       auth->auth.requestMatchers("/welcome","/new").permitAll()
//                               .requestMatchers("/data").authenticated()
//               ).userDetailsService(securityDetails)
//                // .headers(heads->heads.frameOptions().sameOrigin())
//                 .httpBasic(Customizer.withDefaults());
//        return httpSecurity.build();
//    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();
    }

}
