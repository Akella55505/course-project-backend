package com.akella.courseprojectbackend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    POLICE("police_role"),
    INSURANCE("insurance_role"),
    MEDIC("medic_role"),
    COURT("court_role"),
    USER("user_role"),
    ADMIN("admin_role");

    private final String databaseRole;
}
