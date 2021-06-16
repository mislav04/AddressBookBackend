package mislav.addressbook.api.v1.mapper;

import mislav.addressbook.api.v1.model.UserDTO;
import mislav.addressbook.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);
}
