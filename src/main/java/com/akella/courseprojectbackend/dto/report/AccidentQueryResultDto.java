package com.akella.courseprojectbackend.dto.report;

import lombok.Data;

public record AccidentQueryResultDto(Long reportCount,
                                     String reportStreet,
                                     Long streetCount,
                                     String reportCauses,
                                     Long causesCount,
                                     String reportType,
                                     Long typeCount,
                                     String reportDaytime,
                                     Long daytimeCount,
                                     Long reportDriverId,
                                     Long driverCount) {
}
