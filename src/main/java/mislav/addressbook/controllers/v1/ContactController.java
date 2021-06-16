package mislav.addressbook.controllers.v1;

import mislav.addressbook.api.v1.model.ContactDTO;
import mislav.addressbook.api.v1.model.ContactListDTO;
import mislav.addressbook.services.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/contacts/")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<ContactListDTO> getListOfContacts(){

        return new ResponseEntity<ContactListDTO>(new ContactListDTO(contactService.getAllContacts()),
                HttpStatus.OK);

    }

    @GetMapping({"{id}"})
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id){
        return new ResponseEntity<ContactDTO>(contactService.getContactById(id), HttpStatus.OK);
    }

    @GetMapping({"by-user/{userId}"})
    public ResponseEntity<ContactListDTO> getListOfContactsByUser(@PathVariable Long userId){

        return new ResponseEntity<ContactListDTO>(new ContactListDTO(contactService.getAllContactsByCreatedBy(userId)),
                HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<ContactDTO> createNewContact(@RequestBody ContactDTO contactDTO){
        return new ResponseEntity<ContactDTO>(contactService.createNewContact(contactDTO),
                HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ContactDTO> updateContactById(@RequestBody ContactDTO contactDTO){
        ContactDTO updatedContact = contactService.updateContactByDTO(contactDTO);
        return new ResponseEntity<ContactDTO>(updatedContact, HttpStatus.OK);
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<Void> deleteContact(@PathVariable Long id){

        contactService.deleteContactById(id);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
