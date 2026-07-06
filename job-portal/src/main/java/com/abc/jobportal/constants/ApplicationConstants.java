package com.abc.jobportal.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationConstants {

    public static final String JWT_SECRET_KEY = "JWT_SECRET";
    public static final String JWT_SECRET_DEFAULT_VALUE = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
    public static final String JWT_HEADER = "Authorization";
    public static final String ROLE_JOB_SEEKER = "ROLE_JOB_SEEKER";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final int EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    public static final String ACTIVE_STATUS = "ACTIVE";

    public static final String  NEW_MESSAGE = "NEW";
    public static final String  CLOSED_MESSAGE = "CLOSED";

    public static final String  SYSTEM = "SYSTEM";
}
