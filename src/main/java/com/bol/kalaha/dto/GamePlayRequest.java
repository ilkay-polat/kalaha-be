package com.bol.kalaha.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GamePlayRequest(
    @NotNull
    @Min(value = 1, message = "Minimum value for a pit is 1")
    @Max(value = 6, message = "Maximum value for a pit is 6")
    int pitNumber) {
}