package com.trandung.elearn.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";
    
    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "vi";
    public static final String ANONYMOUS_USER = "anonymoususer";

    public interface ENTITY_STATUS {

        Integer DISABLED = -1;
        Integer DELETED = 0;
        Integer ACTIVE = 1;
    }

    private Constants() {
    }
}
