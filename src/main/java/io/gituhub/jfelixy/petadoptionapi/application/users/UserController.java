package io.gituhub.jfelixy.petadoptionapi.application.users;


import io.gituhub.jfelixy.petadoptionapi.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping
    public ResponseEntity save(@RequestBody UserDTO dto){
        try{
            User user = userMapper.mapToUser(dto);
            userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (DuplicatedTupleException e){
            Map<String, String> jsonResult = Map.of("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(jsonResult);
        }
    }



}
