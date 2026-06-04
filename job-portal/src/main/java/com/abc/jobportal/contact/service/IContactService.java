package com.abc.jobportal.contact.service;

import com.abc.jobportal.dto.ContactRequestDto;

public interface IContactService {

    boolean saveContact(ContactRequestDto contactRequestDto);

}
