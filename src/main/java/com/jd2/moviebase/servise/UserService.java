package com.jd2.moviebase.servise;


import com.jd2.moviebase.dto.UserDTO;
import com.jd2.moviebase.model.User;
import com.jd2.moviebase.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    private UserDTO convertToDTO(User user) {
        return new UserDTO();
    }
}
