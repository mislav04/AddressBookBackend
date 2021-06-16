package mislav.addressbook.api.v1.mapper;

import junit.framework.TestCase;
import mislav.addressbook.api.v1.model.ContactDTO;
import mislav.addressbook.domain.Contact;


public class ContactMapperTest extends TestCase {

    ContactMapper contactMapper = ContactMapper.INSTANCE;

    public static final long ID = 1L;

    public void testContactToContactDTO() {
        //given
        Contact contact = new Contact();
        contact.setContact("email@email.com");
        contact.setIsFavourite(true);
        contact.setId(1L);

        //when
        ContactDTO contactDTO = contactMapper.contactToContactDTO(contact);

        //then
        assertEquals(1L, contactDTO.getId());
        assertEquals("email@email.com", contactDTO.getContact());
        assertEquals(java.util.Optional.of(true), java.util.Optional.of(contactDTO.getIsFavourite()));
    }

    public void testContactDtoToContact() {
        //given
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setContact("email@email.com");
        contactDTO.setIsFavourite(false);
        contactDTO.setId(1L);

        //when
        Contact contact = contactMapper.contactDtoToContact(contactDTO);

        //then
        assertEquals(java.util.Optional.of(1L), java.util.Optional.of(contact.getId()));
        assertEquals("email@email.com", contact.getContact());
        assertEquals(java.util.Optional.of(false), java.util.Optional.of(contact.getIsFavourite()));
    }
}