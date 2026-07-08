package com.abc.jobportal.user.service;

import com.abc.jobportal.dto.UserDto;

import java.util.Optional;

public interface IUserService {

    Optional<UserDto> searchUserByEmail(String email);

    UserDto elevateToEmployer(Long userId);

    UserDto assignCompanyToEmployer(Long userId, Long companyId);
}
