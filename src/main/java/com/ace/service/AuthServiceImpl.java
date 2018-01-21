package com.ace.service;

import com.ace.entity.user.SysUser;
import com.ace.repository.AuthService;
import com.ace.repository.SysUserRepository;
import com.ace.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private SysUserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            SysUserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SysUser register(SysUser userToAdd) {
        final String username = userToAdd.getUsername();
        if (userRepository.findByUsername(username) != null) {
            return null;
        }
        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword(passwordEncoder.encode(rawPassword));
        userToAdd.setLastPasswordResetDate(new Date());
//        userToAdd.setRoles(Arrays.asList("ROLE_USER"));
        return userRepository.save(userToAdd);
    }

    @Override
    public SysUser login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Reload password post-security so we can generate token
        SysUser sysUser = (SysUser) userDetailsService.loadUserByUsername(username);
        String token = jwtTokenUtil.generateToken(sysUser);
        sysUser.setCurrentToken(token);
        return sysUser;
    }

    @Override
    public SysUser login(String mobilePhone) {
        SysUser sysUser = (SysUser) userDetailsService.loadUserByUsername("18705596666");
        String token = jwtTokenUtil.generateToken(sysUser);
        sysUser.setCurrentToken(token);
        return sysUser;
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = null;
        try {
            username = jwtTokenUtil.getUsernameFromToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SysUser user = (SysUser) userDetailsService.loadUserByUsername(username);
        try {
            if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
                return jwtTokenUtil.refreshToken(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
