package mislav.addressbook.services;

import mislav.addressbook.api.v1.model.ContactDTO;

import java.util.List;

public interface ContactService {

    List<ContactDTO> getAllContacts();

    ContactDTO getContactById(Long id);

    List<ContactDTO> getAllContactsByCreatedBy(Long createdBy);

    ContactDTO createNewContact(ContactDTO contactDTO);

    ContactDTO updateContactByDTO(ContactDTO contactDTO);

    void deleteContactById(Long id);
}
