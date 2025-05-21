package io.gituhub.jfelixy.petadoptionapi.application.users;


import io.gituhub.jfelixy.petadoptionapi.domain.entity.Pet;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.user.RoleEnum;
import io.gituhub.jfelixy.petadoptionapi.domain.exception.DuplicatedTupleException;
import io.gituhub.jfelixy.petadoptionapi.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.management.relation.Role;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getUserImage (@PathVariable String id) {
        // Find pet by ID
        var possibleResponsePet = userService.getById(id);

        if (possibleResponsePet.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }

        User responseUser = possibleResponsePet.get();
        byte[] userPhoto = responseUser.getPhoto();

        if (userPhoto == null) {
            return ResponseEntity.notFound().build(); // Return 404 if no image
        }

        // Return image bytes with HTTP headers
        return new ResponseEntity<>(userPhoto, HttpStatus.OK);
    }


    private HttpHeaders createHeader(Pet responsePet){
        MediaType mediaType = detectMediaType(responsePet.getPhoto());
        HttpHeaders header = new HttpHeaders();
        header.setContentType(mediaType);
        header.setContentDisposition(ContentDisposition.inline().filename(responsePet.getName()).build());
        header.setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).mustRevalidate());
        header.setContentLength(responsePet.getPhoto().length);
        return header;
    }

    private MediaType detectMediaType(byte[] imageBytes){
        try (InputStream is = new ByteArrayInputStream(imageBytes)) {
            String mimeType = URLConnection.guessContentTypeFromStream(is);
            if (mimeType == null) {
                return MediaType.APPLICATION_OCTET_STREAM;
            }
            return MediaType.parseMediaType(mimeType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
