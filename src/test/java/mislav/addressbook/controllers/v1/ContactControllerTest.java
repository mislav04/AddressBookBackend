package mislav.addressbook.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import mislav.addressbook.api.v1.model.ContactDTO;
import mislav.addressbook.domain.Contact;
import mislav.addressbook.services.ContactService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ContactControllerTest extends TestCase {

    @Mock
    ContactService contactService;

    @InjectMocks
    ContactController contactController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();

    }

    public void testGetListOfContacts() throws Exception {
        ContactDTO contact1 = new ContactDTO();
        contact1.setContact("john@jones.com");
        contact1.setContactType("Email");
        contact1.setFirstName("John");
        contact1.setLastName("Jones");

        ContactDTO contact2 = new ContactDTO();
        contact2.setContact("mike@perry.com");
        contact2.setContactType("Email");
        contact2.setFirstName("Mike");
        contact2.setLastName("Perry");

        when(contactService.getAllContacts()).thenReturn(Arrays.asList(contact1, contact2));

        mockMvc.perform(get("/api/v1/contacts/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contacts", hasSize(2)));
    }

    public void testGetContactById() throws Exception {
        ContactDTO contact1 = new ContactDTO();
        contact1.setContact("john@jones.com");
        contact1.setContactType("Email");
        contact1.setFirstName("John");
        contact1.setLastName("Jones");

        when(contactService.getContactById(anyLong())).thenReturn(contact1);

        mockMvc.perform(get("/api/v1/contacts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("John")))
                .andExpect(jsonPath("$.lastName", equalTo("Jones")))
                .andExpect(jsonPath("$.contactType", equalTo("Email")))
                .andExpect(jsonPath("$.contact", equalTo("john@jones.com")));
    }

    public void testGetListOfContactsByUser() throws Exception {
        ContactDTO contact1 = new ContactDTO();
        contact1.setContact("john@jones.com");
        contact1.setContactType("Email");
        contact1.setFirstName("John");
        contact1.setLastName("Jones");
        contact1.setCreatedBy(1L);

        when(contactService.getAllContactsByCreatedBy(anyLong())).thenReturn(Arrays.asList(contact1));

        mockMvc.perform(get("/api/v1/contacts/by-user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contacts", hasSize(1)));
    }

    public void testCreateNewCustomer() throws Exception {
        ContactDTO contact1 = new ContactDTO();
        contact1.setContact("john@jones.com");
        contact1.setContactType("Email");
        contact1.setFirstName("John");
        contact1.setLastName("Jones");
        contact1.setCreatedBy(1L);

        when(contactService.createNewContact(contact1)).thenReturn(contact1);

        mockMvc.perform(post("/api/v1/contacts/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(contact1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo("John")))
                .andExpect(jsonPath("$.lastName", equalTo("Jones")))
                .andExpect(jsonPath("$.contactType", equalTo("Email")))
                .andExpect(jsonPath("$.contact", equalTo("john@jones.com")));
    }

    public void testUpdateContactById() throws Exception {
        ContactDTO contact1 = new ContactDTO();
        contact1.setContact("john@jones.com");
        contact1.setContactType("Email");
        contact1.setFirstName("John");
        contact1.setLastName("Jones");
        contact1.setCreatedBy(1L);

        when(contactService.updateContactByDTO(any(ContactDTO.class))).thenReturn(contact1);

        mockMvc.perform(put("/api/v1/contacts/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(contact1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("John")))
                .andExpect(jsonPath("$.lastName", equalTo("Jones")))
                .andExpect(jsonPath("$.contactType", equalTo("Email")))
                .andExpect(jsonPath("$.contact", equalTo("john@jones.com")));
    }

    public void testDeleteContact() throws Exception {
        mockMvc.perform(delete("/api/v1/contacts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(contactService).deleteContactById(anyLong());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}