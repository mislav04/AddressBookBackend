package mislav.addressbook.bootstrap;

import mislav.addressbook.domain.Contact;
import mislav.addressbook.domain.User;
import mislav.addressbook.repositories.ContactRepository;
import mislav.addressbook.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Bootstrap implements CommandLineRunner{

    private final UserRepository userRepository;
    private final ContactRepository contactRepository;

    public Bootstrap(UserRepository userRepository, ContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        loadContacts();
        loadUsers();

        List<Contact> contacts = contactRepository.findAllByCreatedBy(1L);
        System.out.println(contacts);
    }

    private void loadUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("sss.g@gmail.com");
        user1.setPassword("12345");
        userRepository.save(user1);

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("aaa.g@gmail.com");
        user2.setPassword("12345");
        userRepository.save(user2);


        User user = userRepository.findByEmail("sss.g@gmail.com");
        System.out.println("Users Loaded: " + userRepository.count());
        System.out.println("Found user: " + user.getEmail());
    }

    private void loadContacts() {
        Contact contact1 = new Contact();
        contact1.setId(1L);
        contact1.setContact("dgbdföb@dgsfdg.com");
        contact1.setContactType("Email");
        contact1.setFirstName("Pero");
        contact1.setLastName("Peric");
        contact1.setCreatedBy(1L);
        contactRepository.save(contact1);

        Contact contact2 = new Contact();
        contact2.setId(2L);
        contact2.setContact("sssss@dgsfdg.com");
        contact2.setContactType("Email");
        contact2.setFirstName("Pero2");
        contact2.setLastName("Peric2");
        contact1.setCreatedBy(2L);
        contactRepository.save(contact2);

        Contact contact3 = new Contact();
        contact3.setId(3L);
        contact3.setContact("sssss@dgsfdg.com");
        contact3.setContactType("Email");
        contact3.setFirstName("Ivan");
        contact3.setLastName("Ivanovic");
        contact3.setCreatedBy(1L);
        contactRepository.save(contact3);
    }
}
