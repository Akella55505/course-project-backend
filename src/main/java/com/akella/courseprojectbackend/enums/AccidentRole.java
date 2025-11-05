package com.akella.courseprojectbackend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccidentRole {
    CULPRIT("Винуватець"),
    VICTIM("Потерпілий");

    private final String role;
}
