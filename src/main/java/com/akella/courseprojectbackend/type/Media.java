package com.akella.courseprojectbackend.type;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Media(ArrayList<String> photos, ArrayList<String> videos) {}
