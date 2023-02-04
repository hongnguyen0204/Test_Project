package com.example.test_project.Configs.UserConfig;

import com.example.test_project.Repository.UserRepository;
import com.example.test_project.Utils.HandleException.Exceptions.EmailNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceCustom implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /**
     * @param email use instead of username
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Get user in database, throw exception if not exist
        var user = userRepository.findFirstByEmail(email)
                .orElseThrow(EmailNotExistException::new);
        return new UserDetailsCustom(user);
    }
}
