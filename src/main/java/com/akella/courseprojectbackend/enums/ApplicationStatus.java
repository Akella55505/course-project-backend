package com.akella.courseprojectbackend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplicationStatus {
    IN_REVIEW("На розгляді"),
    DENIED("Відхилено"),
    PROCESSED("Оброблено");

    private final String status;
}
