package com.akella.courseprojectbackend.dto.register;

import com.akella.courseprojectbackend.enums.AccidentRole;
import com.akella.courseprojectbackend.type.Media;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

public record AccidentRegisterDto(Date date, Time time, String addressStreet, String addressNumber, String causes,
                                  String type, Media media, List<Map<Long, AccidentRole>> personsRoles, List<Long> vehicleIds) {
}
