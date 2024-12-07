package alexjuro.de.kleiderschrank.service;

import alexjuro.de.kleiderschrank.domain.Closet;
import alexjuro.de.kleiderschrank.domain.User;
import alexjuro.de.kleiderschrank.dto.ClosetDTO;
import alexjuro.de.kleiderschrank.dto.UserDTO;
import alexjuro.de.kleiderschrank.exceptions.AlreadyExistsException;
import alexjuro.de.kleiderschrank.exceptions.NotAuthenticatedException;
import alexjuro.de.kleiderschrank.repository.UserRepository;
import com.google.api.gax.rpc.UnauthenticatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserDTO saveUser() throws Exception{
        log.info("saving user");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDTO u = (UserDTO) authentication.getPrincipal();

            Optional<User> userOptional = userRepository.findByUid(u.getUid());
            if (userOptional.isPresent()) {
                log.info("user already exists");
                throw new AlreadyExistsException("The user already exists");
            }

            User user = User
                    .builder()
                    .uid(u.getUid())
                    .name(u.getName())
                    .email(u.getEmail())
                    .createdAt(new Date())
                    .closet(new Closet())
                    .build();
            userRepository.save(user);

            return u;
        } else {
            log.info("user is not authenticated");
            throw new NotAuthenticatedException("The user is not authenticated");
        }
    }

    // only for internal queries
    public UserDTO getUser(String uid){
        Optional<User> user = userRepository.findByUid(uid);
        if (user.isEmpty()) {
            return null;
        }else {
            User foundUser = user.get();
            return UserDTO
                    .builder()
                    .uid(foundUser.getUid())
                    .name(foundUser.getName())
                    .email(foundUser.getEmail())
                    .createdAt(foundUser.getCreatedAt())
                    .closet(ClosetDTO.builder().build())
                    .build();
        }
    }

    public UserDTO getOwnUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDTO u = (UserDTO) authentication.getPrincipal();
            return u;
        } else {
            log.info("user is not authenticated");
            throw new NotAuthenticatedException("The user is not authenticated");
        }
    }
}
