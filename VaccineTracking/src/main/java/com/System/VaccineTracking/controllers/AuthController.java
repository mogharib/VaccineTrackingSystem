package com.System.VaccineTracking.controllers;

import com.System.VaccineTracking.dtos.AuthResponseDto;
import com.System.VaccineTracking.dtos.LoginDto;
import com.System.VaccineTracking.dtos.RegisterDto;
import com.System.VaccineTracking.enums.RoleEnum;
import com.System.VaccineTracking.models.Role;
import com.System.VaccineTracking.models.Users;
import com.System.VaccineTracking.repos.RoleRepo;
import com.System.VaccineTracking.repos.UserRepo;
import com.System.VaccineTracking.security.JWTGenerator;
import java.util.Collections;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth/")
public class AuthController extends BaseController{
   private final AuthenticationManager authenticationManager;
    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
     private  final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getNationalId(),
                loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDto registerDto) {
        try {
            if (userRepository.existsByNationalId(registerDto.getNationalId())) {
                return new ResponseEntity<>("National Id is already exist !", HttpStatus.BAD_REQUEST);
            }
            Users user = new Users();
            user.setNationalId(registerDto.getNationalId());
            user.setPassword(passwordEncoder.encode((registerDto.getPassword())));
            Role role = roleRepository.findByRoleName(RoleEnum.USER.value).get();
            user.setRoles(Collections.singletonList(role));
            userRepository.save(user);
        } catch (Exception e) {
          e.printStackTrace();
        }

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }
    @PostMapping("admin/register")
    public ResponseEntity<String> AdminRegister(@RequestBody @Valid RegisterDto registerDto) {
        try {
            if (userRepository.existsByNationalId(registerDto.getNationalId())) {
                return new ResponseEntity<>("National Id is already exist !", HttpStatus.BAD_REQUEST);
            }
            Users user = new Users();
            user.setNationalId(registerDto.getNationalId());
            user.setPassword(passwordEncoder.encode((registerDto.getPassword())));
            Role role = roleRepository.findByRoleName(RoleEnum.ADMIN.value).get();
            user.setRoles(Collections.singletonList(role));
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }
}
