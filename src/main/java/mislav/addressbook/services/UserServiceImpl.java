package mislav.addressbook.services;

import mislav.addressbook.api.v1.mapper.UserMapper;
import mislav.addressbook.api.v1.model.UserDTO;
import mislav.addressbook.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements  UserService{

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> {
                    UserDTO userDTO = userMapper.userToUserDTO(user);
                    return userDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::userToUserDTO)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return userMapper.userToUserDTO(userRepository.findByEmail(email));
    }
}
