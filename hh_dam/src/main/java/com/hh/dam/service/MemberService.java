package com.hh.dam.service;

import org.springframework.stereotype.Service;
import com.hh.dam.repository.MemberRepository;

@Service
public class MyMemberDetailsService implements MemberDetailsService {

    private final MemberRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public MemberDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        UserBuilder builder = withUsername(user.getUsername());
        builder.password(user.getPassword());
        builder.roles(user.getRoles().toArray(new String[0]));
        
        return builder.build();
    }
}
