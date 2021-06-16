package mislav.addressbook.controllers.v1;

import mislav.addressbook.api.v1.model.UserDTO;
import mislav.addressbook.api.v1.model.UserListDTO;
import mislav.addressbook.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/users/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"{id}"})
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        return new ResponseEntity<UserDTO>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping({"by-email/{email}"})
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserDTO userDTO = userService.getUserByEmail(email);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }
}
