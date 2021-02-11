package com.excercise.jwtTutorial.service;

import com.excercise.jwtTutorial.entity.User;
import com.excercise.jwtTutorial.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findOneWithAuthoritiesByName(username)
                .map(user -> createUser(username,user))
                .orElseThrow(() -> new UsernameNotFoundException(username+" -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String username, User user) {
        if(!user.isActivated()){
            throw new RuntimeException(username + "-> 활성화 되어있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorityList = user.getAuthorities().stream()
                                                        .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                                                        .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getName(),
                                                                        user.getPassword(),
                                                                        grantedAuthorityList);
    }
}
