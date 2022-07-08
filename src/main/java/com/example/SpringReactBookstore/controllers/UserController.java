package com.example.SpringReactBookstore.controllers;

import com.example.SpringReactBookstore.config.JwtUtil;
import com.example.SpringReactBookstore.models.AuthenticationRequest;
import com.example.SpringReactBookstore.models.AuthenticationResponse;
import com.example.SpringReactBookstore.models.UserDTO;
import com.example.SpringReactBookstore.services.UserDetailService;
import com.example.SpringReactBookstore.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@CrossOrigin
public class UserController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UserDetailService userDetailService;

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        }
        catch (BadCredentialsException exception){
            throw new RuntimeException("Email or Password is incorrect");
        }

        UserDetails userDetails = userDetailService.loadUserByUsername(request.getEmail());

        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse("Beer " + token));
    }

    @PostMapping("/register")
    public ResponseEntity<UUID> register(@Validated @RequestBody UserDTO userDTO){

        UUID uuid = userService.addUser(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(uuid);
    }
}
