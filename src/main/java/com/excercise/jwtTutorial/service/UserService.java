package com.excercise.jwtTutorial.service;

import com.excercise.jwtTutorial.dto.UserDto;
import com.excercise.jwtTutorial.entity.Authority;
import com.excercise.jwtTutorial.entity.User;
import com.excercise.jwtTutorial.repository.UserRepository;
import com.excercise.jwtTutorial.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User signup(UserDto userDto){
        System.out.println(passwordEncoder.encode(userDto.getPassword()));
        if(userRepository.findOneWithAuthoritiesByName(userDto.getUsername()).orElse(null)!=null){
            throw new RuntimeException("이미 가입된 유저입니다.");
        }

        Authority authority = Authority.builder()
                                .authorityName("ROLE_USER")
                                .build();

        User user = User.builder()
                .name(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        userRepository.save(user);


        return user;
    }

    @Transactional
    public Optional<User> getUserWithAuthorities(String username){
        return userRepository.findOneWithAuthoritiesByName(username);
    }

    @Transactional
    public Optional<User> getMyUserAuthorities(){
        return SecurityUtil.getCurrentUserName().flatMap(userRepository::findOneWithAuthoritiesByName);
    }


}
