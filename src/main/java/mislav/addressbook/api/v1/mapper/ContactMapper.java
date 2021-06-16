package mislav.addressbook.api.v1.mapper;

import mislav.addressbook.api.v1.model.ContactDTO;
import mislav.addressbook.api.v1.model.UserDTO;
import mislav.addressbook.domain.Contact;
import mislav.addressbook.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactMapper {
    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    ContactDTO contactToContactDTO(Contact contact);
    Contact contactDtoToContact(ContactDTO contactDTO);
}
