package com.System.VaccineTracking.security;

import com.System.VaccineTracking.models.Users;
import com.System.VaccineTracking.repos.UserRepo;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetails implements UserDetailsService {

    private final UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String nationalId) throws UsernameNotFoundException {
        Users user = userRepo.findByNationalId(nationalId).orElseThrow(() -> new UsernameNotFoundException("Not Found"));
        return new User(user.getNationalId(), user.getPassword(), getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Users user) {
        String[] userRoles = user.getRoles().stream().map((role) -> role.getRoleName()).toArray(String[]::new);
        {
            Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
            return authorities;
        }
    }
}
