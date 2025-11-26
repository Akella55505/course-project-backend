package com.akella.courseprojectbackend.dto;

public record AccidentStatisticsStreetsDto(String street, Long violationCount, String topViolation, Long accidentCount,
                                           Long accidentCountPedestrian, Long violationCountRank,
                                           Long accidentCountRank, Long accidentCountPedestrianRank) {}
