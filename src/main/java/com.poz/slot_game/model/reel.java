package com.poz.slot_game.model;

import java.util.Map;
import java.util.List;

public class reel {
    public Map REEL_CONFIG = Map.of("Blank", 4,
            "Cherry", 11,
            "Lemon", 11,
            "BAR", 6,
            "Seven", 6,
            "Wild", 1);

    public int[][] PAYLINES = {
            {0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1},
            {2, 2, 2, 2, 2},
            {0, 1, 2, 1, 0},
            {2, 1, 0, 1, 2}
            };
}

