package mislav.addressbook.repositories;

import mislav.addressbook.api.v1.model.ContactDTO;
import mislav.addressbook.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByCreatedBy(Long createdBy);
}
