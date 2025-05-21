package io.gituhub.jfelixy.petadoptionapi.application.users;


import io.gituhub.jfelixy.petadoptionapi.domain.enums.user.RoleEnum;
import io.gituhub.jfelixy.petadoptionapi.domain.exception.DuplicatedTupleException;
import io.gituhub.jfelixy.petadoptionapi.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.Role;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserMapper userMapper;

//    @PostMapping
//    public ResponseEntity save(@RequestBody UserDTO dto){
//
//        try{
//            if (dto.getRole() == null) {
//                dto.setRole(RoleEnum.USER);
//            }
//            User user = userMapper.mapToUser(dto);
//            userService.save(user);
//            return ResponseEntity.status(HttpStatus.CREATED).build();
//        }catch (DuplicatedTupleException e){
//            Map<String, String> jsonResult = Map.of("error", e.getMessage());
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(jsonResult);
//        }
//    }
@PostMapping
public ResponseEntity save(@RequestParam("photo") MultipartFile file,
        @RequestParam("username") String username,
        @RequestParam("email") String email,
        @RequestParam("password") String password)
        {

    try {
        RoleEnum userRole = null;
        userRole = RoleEnum.USER;


        User user = userMapper.mapToUser(file, username, email, password);

        //UserDTO.builder().photo(file).username(username).email(email).role(userRole).password(password).build();

        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }catch (DuplicatedTupleException e){
        Map<String, String> jsonResult = Map.of("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(jsonResult);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}

    @PostMapping("/auth")
    public ResponseEntity authenticate (@RequestBody CredentialsDTO credentialsDTO){
        var token = userService.authenticate(credentialsDTO.getEmail(), credentialsDTO.getPassword());
        if (token == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else{
            return ResponseEntity.ok(token);
        }
    }

}
