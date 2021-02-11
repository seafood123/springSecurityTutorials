package com.excercise.jwtTutorial.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtil {

    private SecurityUtil(){

    }

    public static Optional<String> getCurrentUserName(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null){
            System.out.println("security 정보가 없ㅅ브니다.");
            return Optional.empty();
        }

        String userName = null;
        if(authentication.getPrincipal() instanceof UserDetails){
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            userName = springSecurityUser.getUsername();
        }else if(authentication.getPrincipal() instanceof String){
            userName = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(userName);
    }
}
