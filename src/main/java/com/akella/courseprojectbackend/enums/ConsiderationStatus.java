package com.akella.courseprojectbackend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConsiderationStatus {
    REGISTERED("Зареєстровано"),
    SENT("Передано до суду"),
    REVIEWED("Розглянуто");

    private final String status;
}
