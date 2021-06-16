package mislav.addressbook.services;

import junit.framework.TestCase;
import mislav.addressbook.api.v1.mapper.ContactMapper;
import mislav.addressbook.api.v1.mapper.UserMapper;
import mislav.addressbook.api.v1.model.ContactDTO;
import mislav.addressbook.api.v1.model.UserDTO;
import mislav.addressbook.domain.Contact;
import mislav.addressbook.domain.User;
import mislav.addressbook.repositories.ContactRepository;
import mislav.addressbook.repositories.UserRepository;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceImplTest extends TestCase {

    @Mock
    UserRepository userRepository;

    UserMapper userMapper = UserMapper.INSTANCE;

    UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        userService = new UserServiceImpl(userMapper, userRepository);
    }

    public void testGetAllUsers() throws Exception {
        User user1 = new User();
        user1.setEmail("john@jones.com");
        user1.setPassword("123");

        User user2 = new User();
        user2.setEmail("mike@perry.com");
        user2.setPassword("123");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        //when
        List<UserDTO> userDTOS = userService.getAllUsers();

        //then
        assertEquals(2, userDTOS.size());
    }

    public void testGetUserById() throws Exception {
        User user1 = new User();
        user1.setEmail("john@jones.com");
        user1.setPassword("123");

        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(user1));

        //when
        UserDTO userDTO = userService.getUserById(1L);

        assertEquals("john@jones.com", userDTO.getEmail());
        assertEquals("123", userDTO.getPassword());
    }

    public void testGetUserByEmail() throws Exception {
        User user1 = new User();
        user1.setEmail("john@jones.com");
        user1.setPassword("123");

        when(userRepository.findByEmail("john@jones.com")).thenReturn(user1);

        //when
        UserDTO userDTO = userService.getUserByEmail("john@jones.com");

        assertEquals("john@jones.com", userDTO.getEmail());
        assertEquals("123", userDTO.getPassword());
    }
}