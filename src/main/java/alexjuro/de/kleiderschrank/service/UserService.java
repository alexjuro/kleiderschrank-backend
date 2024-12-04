package alexjuro.de.kleiderschrank.service;

import alexjuro.de.kleiderschrank.domain.Closet;
import alexjuro.de.kleiderschrank.domain.User;
import alexjuro.de.kleiderschrank.dto.ClosetDTO;
import alexjuro.de.kleiderschrank.dto.UserDTO;
import alexjuro.de.kleiderschrank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void saveUser(UserDTO userDTO) {
        User user = User
                .builder()
                .uid(userDTO.getUid())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .createdAt(userDTO.getCreatedAt())
                .closet(new Closet())
                .build();
        userRepository.save(user);
    }

    //TODO: search the user by the send cookie
    public UserDTO getUser(String uid){
        Optional<User> user = userRepository.findByUid(uid);
        if (user.isEmpty()) {
            return null;
        }else {
            User foundUser = user.get();
            return UserDTO
                    .builder()
                    .id(foundUser.getId())
                    .uid(foundUser.getUid())
                    .name(foundUser.getName())
                    .email(foundUser.getEmail())
                    .createdAt(foundUser.getCreatedAt())
                    .closet(new ClosetDTO())
                    .build();
        }

    }
}
