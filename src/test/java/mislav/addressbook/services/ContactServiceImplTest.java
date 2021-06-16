package mislav.addressbook.services;

import junit.framework.TestCase;
import mislav.addressbook.api.v1.mapper.ContactMapper;
import mislav.addressbook.api.v1.model.ContactDTO;
import mislav.addressbook.domain.Contact;
import mislav.addressbook.repositories.ContactRepository;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ContactServiceImplTest extends TestCase {

    @Mock
    ContactRepository contactRepository;

    ContactMapper contactMapper = ContactMapper.INSTANCE;

    ContactService contactService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        contactService = new ContactServiceImpl(contactMapper, contactRepository);
    }

    public void testGetAllContacts() throws Exception {
        Contact contact1 = new Contact();
        contact1.setContact("john@jones.com");
        contact1.setContactType("Email");
        contact1.setFirstName("John");
        contact1.setLastName("Jones");

        Contact contact2 = new Contact();
        contact2.setContact("mike@perry.com");
        contact2.setContactType("Email");
        contact2.setFirstName("Mike");
        contact2.setLastName("Perry");

        when(contactRepository.findAll()).thenReturn(Arrays.asList(contact1, contact2));

        //when
        List<ContactDTO> contactDTOS = contactService.getAllContacts();

        //then
        assertEquals(2, contactDTOS.size());
    }

    public void testGetContactById() throws Exception {
        Contact contact1 = new Contact();
        contact1.setContact("john@jones.com");
        contact1.setContactType("Email");
        contact1.setFirstName("John");
        contact1.setLastName("Jones");

        when(contactRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(contact1));

        //when
        ContactDTO contactDTO = contactService.getContactById(1L);

        assertEquals("John", contactDTO.getFirstName());
        assertEquals("Email", contactDTO.getContactType());
    }

    public void testGetAllContactsByCreatedBy() throws Exception {
        Contact contact1 = new Contact();
        contact1.setContact("john@jones.com");
        contact1.setContactType("Email");
        contact1.setFirstName("John");
        contact1.setLastName("Jones");

        when(contactRepository.findAllByCreatedBy(anyLong())).thenReturn(Arrays.asList(contact1));

        //when
        List<ContactDTO> contactDTOS = contactService.getAllContactsByCreatedBy(1L);

        //then
        assertEquals(1, contactDTOS.size());
    }

    public void testCreateNewContact() throws Exception {
        //given
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setFirstName("John");
        contactDTO.setLastName("Johnes");

        Contact savedContact = new Contact();
        savedContact.setFirstName(contactDTO.getFirstName());
        savedContact.setLastName(contactDTO.getLastName());
        savedContact.setId(1l);

        when(contactRepository.save(any(Contact.class))).thenReturn(savedContact);

        //when
        ContactDTO savedDto = contactService.createNewContact(contactDTO);

        //then
        assertEquals(contactDTO.getFirstName(), savedDto.getFirstName());
        assertEquals(contactDTO.getLastName(), savedDto.getLastName());
        assertEquals(contactDTO.getIsFavourite(), savedDto.getIsFavourite());
    }

    public void testDeleteContactById() throws Exception {
        Long id = 1L;

        contactService.deleteContactById(id);

        verify(contactRepository, times(1)).deleteById(anyLong());
    }
}