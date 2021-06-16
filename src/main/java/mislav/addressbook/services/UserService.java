package mislav.addressbook.services;

import mislav.addressbook.api.v1.model.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO getUserById(Long id);

    UserDTO getUserByEmail(String email);

    List<UserDTO> getAllUsers();
}
