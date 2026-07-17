package com.poz.slot_game.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Collections;

public class reel {
    public static void main(String[] args) {
        for(List<String> reelStrip : reelStrips){
            System.out.println(reelStrip);
        }
    }

    private static final record Paytable(String symbols, int count, int multiplier) {
    }

    private static final Map<String, Integer> reelConfig = Map.of("Blank", 4,
            "Cherry", 11,
            "Lemon", 11,
            "BAR", 6,
            "Seven", 6,
            "Wild", 1);

    private static final int[][] Paylines = {
            {0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1},
            {2, 2, 2, 2, 2},
            {0, 1, 2, 1, 0},
            {2, 1, 0, 1, 2}
    };
    private static final List<Paytable> reelStrip = List.of(
            new Paytable("Seven", 5, 500),
            new Paytable("Seven", 4, 100),
            new Paytable("BAR", 5, 100),
            new Paytable("Seven", 3, 50),
            new Paytable("Cherry", 5, 50),
            new Paytable("BAR", 4, 25),
            new Paytable("Lemon", 5, 25),
            new Paytable("BAR", 3, 10),
            new Paytable("Cherry", 4, 10),
            new Paytable("Cherry", 3, 5),
            new Paytable("Lemon", 4, 5),
            new Paytable("Lemon", 3, 3)
    );

    public static List buildStrip() {
        List<List<String>> buckets = new ArrayList<>();
        reelConfig.forEach((sym, cnt) -> buckets.add(Collections.nCopies(cnt, sym)));
        int maxLength = Collections.max(reelConfig.values());
        List symbols = new ArrayList<>();
        for (int i = 0; i < maxLength; i++) {
            for (List<String> bucket : buckets) {
                if (bucket.size() > i) {
                    symbols.add(bucket.get(i));
                }
            }
        }
        return symbols;
    }

    public static List<List<String>> reelStrips = List.of(
            buildStrip(),buildStrip(),buildStrip(),buildStrip(),buildStrip());
}

