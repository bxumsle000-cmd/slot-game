package com.poz.slot_game.model;

import java.util.List;

public record SpinOutCome(List<Integer> stops,
                          List<List<String>> windows,
                          List<List<String>> grid,
                          List<Integer> paylineMultipliers,
                          int totalMultiplier) {}
