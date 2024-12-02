package alexjuro.de.kleiderschrank.controller;

import alexjuro.de.kleiderschrank.dto.UserDTO;
import alexjuro.de.kleiderschrank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public ResponseEntity<UserDTO> createUser(/*@Valid*/ @RequestBody UserDTO userDTO) throws Exception{
        userDTO.setCreatedAt(new Date());
        userService.saveUser(userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

//    @GetMapping(
//            consumes = {
//                    MediaType.APPLICATION_JSON_VALUE,
//                    MediaType.APPLICATION_XML_VALUE},
//            produces = {
//                    MediaType.APPLICATION_JSON_VALUE,
//                    MediaType.APPLICATION_XML_VALUE
//            })
    @GetMapping()
    public ResponseEntity<UserDTO> getUser(@RequestParam String uid) throws Exception{
        UserDTO userDTO = userService.getUser(uid);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
