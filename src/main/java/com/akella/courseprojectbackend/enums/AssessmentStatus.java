package com.akella.courseprojectbackend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssessmentStatus {
    IN_REVIEW("На розгляді"),
    ASSESSED("Оцінено"),
    AGREED("Узгоджено");

    private final String status;
}
