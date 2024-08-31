package com.project.vehicle_parking.security;

import com.project.vehicle_parking.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;

@Slf4j
public class Session {

    public static User getUser(){
        if (SecurityContextHolder.getContext().getAuthentication() == null){
            return null;
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User){
            return (User) principal;
        }
        return null;
    }

    public static void setUser(User user){
        ArrayList<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user,null,authList);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
