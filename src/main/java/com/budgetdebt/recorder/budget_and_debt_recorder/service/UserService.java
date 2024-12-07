package com.budgetdebt.recorder.budget_and_debt_recorder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.budgetdebt.recorder.budget_and_debt_recorder.model.users.Users;
import com.budgetdebt.recorder.budget_and_debt_recorder.repositories.UserRepository;


@Service
public class UserService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);

        if(user != null) {
            var springUser = User.withUsername(user.getUsername())
                                .password(user.getPassword())
                                .build();

            return springUser;
        }
        
        return null;
    }
}
