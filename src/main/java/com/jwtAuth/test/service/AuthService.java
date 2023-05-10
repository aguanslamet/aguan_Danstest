package com.jwtAuth.test.service;

import com.jwtAuth.test.dto.JWTResponse;
import com.jwtAuth.test.dto.ResponseStatus;
import com.jwtAuth.test.dto.UserDetailsDto;
import com.jwtAuth.test.dto.UserRequest;
import com.jwtAuth.test.model.Users;
import com.jwtAuth.test.repository.UserRepository;
import com.jwtAuth.test.utils.JWTAuthEntryPoint;
import com.jwtAuth.test.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JWTUtil jwtUtil;
    public ResponseStatus register(UserRequest request) {
        boolean userExsist = userRepository.findByEmail(request.getEmail()).isPresent();
        if (userExsist){
            throw new RuntimeException( String.format("User with Email '%s' already",request.getEmail()));
        }
        Users users = new Users();
        String encode = bCryptPasswordEncoder.encode(request.getPassword());
        users.setPassword(encode);
        users.setEmail(request.getEmail());
        users.setUsername(request.getUsername());
        userRepository.save(users);

        return ResponseStatus.builder()
                .responeCode("200")
                .responseMessage("Success")
                .build();
    }

    public ResponseEntity<JWTResponse> login(UserRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        UserDetailsDto userDetails = (UserDetailsDto) authentication.getPrincipal();

        return ResponseEntity.ok(new JWTResponse(jwt,userDetails.getId(),userDetails.getUsername(),userDetails.getEmail()));
    }
}
