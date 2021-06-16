package mislav.addressbook.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private String firstName;
    private String lastName;
    private String contactType;
    private String contact;
    private Long createdBy;
    private long id;
    private Boolean isFavourite;
    private String dateOfBirth;
}
