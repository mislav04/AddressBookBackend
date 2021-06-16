package mislav.addressbook.controllers.v1;

import junit.framework.TestCase;
import mislav.addressbook.api.v1.model.ContactDTO;
import mislav.addressbook.api.v1.model.UserDTO;
import mislav.addressbook.services.ContactService;
import mislav.addressbook.services.UserService;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends TestCase {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

    }

    public void testGetUserById() throws Exception {
        UserDTO user1 = new UserDTO();
        user1.setEmail("john@jones.com");
        user1.setPassword("1234abc@");

        when(userService.getUserById(anyLong())).thenReturn(user1);

        mockMvc.perform(get("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password", equalTo("1234abc@")))
                .andExpect(jsonPath("$.email", equalTo("john@jones.com")));
    }

    public void testGetUserByEmail() throws Exception{
        UserDTO user1 = new UserDTO();
        user1.setEmail("john@jones.com");
        user1.setPassword("1234abc@");

        when(userService.getUserByEmail(anyString())).thenReturn(user1);

        mockMvc.perform(get("/api/v1/users/by-email/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password", equalTo("1234abc@")))
                .andExpect(jsonPath("$.email", equalTo("john@jones.com")));
    }
}