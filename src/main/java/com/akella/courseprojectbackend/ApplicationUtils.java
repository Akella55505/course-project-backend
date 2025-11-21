package com.akella.courseprojectbackend;

import com.akella.courseprojectbackend.enums.Role;
import org.springframework.security.core.context.SecurityContextHolder;

public class ApplicationUtils {
    public static Role getRoleFromContext() {
        return Role.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority());
    }

    public static String getEmailFromContext() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
