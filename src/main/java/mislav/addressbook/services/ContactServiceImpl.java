package mislav.addressbook.services;

import mislav.addressbook.api.v1.mapper.ContactMapper;
import mislav.addressbook.api.v1.model.ContactDTO;
import mislav.addressbook.domain.Contact;
import mislav.addressbook.repositories.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService{

    private final ContactMapper contactMapper;
    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactMapper contactMapper, ContactRepository contactRepository) {
        this.contactMapper = contactMapper;
        this.contactRepository = contactRepository;
    }

    @Override
    public List<ContactDTO> getAllContacts() {
        return contactRepository
                .findAll()
                .stream()
                .map(contact -> {
                    ContactDTO contactDTO = contactMapper.contactToContactDTO(contact);
                    return contactDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ContactDTO getContactById(Long id) {
        return contactRepository
                .findById(id)
                .map(contactMapper::contactToContactDTO)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public List<ContactDTO> getAllContactsByCreatedBy(Long createdBy) {
        return contactRepository.findAllByCreatedBy(createdBy)
                .stream()
                .map(contact -> {
                    ContactDTO contactDTO = contactMapper.contactToContactDTO(contact);
                    return  contactDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ContactDTO createNewContact(ContactDTO contactDTO) {
        Contact contact = contactMapper.contactDtoToContact(contactDTO);
        contact.setIsFavourite(false);
        Contact savedContact = contactRepository.save(contact);

        ContactDTO returnDto = contactMapper.contactToContactDTO(savedContact);

        return returnDto;
    }

    @Override
    public ContactDTO updateContactByDTO(ContactDTO contactDTO) {
        Contact contact = contactMapper.contactDtoToContact(contactDTO);
        contact.setId(contact.getId());
        Contact savedContact = contactRepository.save(contact);

        ContactDTO returnDto = contactMapper.contactToContactDTO(savedContact);

        return returnDto;
    }

    @Override
    public void deleteContactById(Long id) {
        contactRepository.deleteById(id);
    }
}
