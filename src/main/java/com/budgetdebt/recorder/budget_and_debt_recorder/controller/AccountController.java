package com.budgetdebt.recorder.budget_and_debt_recorder.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budgetdebt.recorder.budget_and_debt_recorder.model.auth.LoginDto;
import com.budgetdebt.recorder.budget_and_debt_recorder.model.auth.RegisterDto;
import com.budgetdebt.recorder.budget_and_debt_recorder.model.users.Users;
import com.budgetdebt.recorder.budget_and_debt_recorder.repositories.UserRepository;
import com.nimbusds.jose.jwk.source.ImmutableSecret;

import jakarta.validation.Valid;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@RestController
@RequestMapping("/account")
public class AccountController {
    
    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${security.jwt.issuer}")
    private String jwtIssuer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/profile")
    public ResponseEntity<Object> profile(Authentication auth) {
        var response = new HashMap<String, Object>();
        response.put("Username", auth.getName());
        response.put("Authorities", auth.getAuthorities());
        var user = userRepository.findByUsername(auth.getName());
        response.put("User", user);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDto registerDto, BindingResult result) {
        if(result.hasErrors()) {
            var errorList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();

            for(int i = 0; i < errorList.size(); i++){
                var error = (FieldError) errorList.get(i);
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errorsMap);
        }

        var bCryptEncoder = new BCryptPasswordEncoder();

        Users user = new Users();
        user.setFullname(registerDto.getFullname());
        user.setRole(registerDto.getRole());
        user.setUsername(registerDto.getUsername());
        user.setDate_created(LocalDateTime.now());
        user.setPassword(bCryptEncoder.encode(registerDto.getPassword()));

        try {
            var otherUser = userRepository.findByUsername(registerDto.getUsername());
            if(otherUser != null) {
                return ResponseEntity.badRequest().body("Username already in use.");
            }
            
            userRepository.save(user);

            String jwtToken = createJwtToken(user);

            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", user);

            return ResponseEntity.ok(response);
        }
        catch (Exception ex) {
            System.out.println("There is an exception : ");
            ex.printStackTrace();
        }

        return ResponseEntity.badRequest().body("Error");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDto loginDto, BindingResult result) {
        if(result.hasErrors()) {
            var errorList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();

            for(int i = 0; i < errorList.size(); i++){
                var error = (FieldError) errorList.get(i);
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errorsMap);
        }

            try {
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(), 
                        loginDto.getPassword())
                );

                Users user = userRepository.findByUsername(loginDto.getUsername());

                String jwtToken = createJwtToken(user);

                var response = new HashMap<String, Object>();
                response.put("token", jwtToken);
                response.put("user", user);

                return ResponseEntity.ok(response);
            }

            catch(Exception ex) {
                System.out.println("There is an exception : ");
                ex.printStackTrace();
            }

            return ResponseEntity.badRequest().body("Bad username or password");
        
                
    }

    private String createJwtToken(Users users) {
        if (jwtSecretKey == null || jwtSecretKey.isEmpty()) {
            throw new IllegalStateException("JWT Secret Key is not configured properly");
        }
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                        .issuer(jwtIssuer)
                        .issuedAt(now)
                        .expiresAt(now.plusSeconds(24 * 3600))
                        .subject(users.getUsername())
                        .claim("role", users.getRole())
                        .build();
        var encoder = new NimbusJwtEncoder(
            new ImmutableSecret<>(jwtSecretKey.getBytes()));
        var header = JwsHeader.with(MacAlgorithm.HS256).build();
        var params = JwtEncoderParameters.from(header, claims);

        return encoder.encode(params).getTokenValue();
    }

}
