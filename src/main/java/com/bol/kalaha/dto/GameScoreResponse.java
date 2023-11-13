package com.bol.kalaha.dto;

import com.bol.kalaha.enums.PlayerNo;

import java.util.List;

public record GameScoreResponse(PlayerNo winnerPlayer,
                                List<Integer> scores) {
}