package com.poz.slot_game.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.poz.slot_game.model.Paytable;

public class Reel {


    public  static final Map<String, Integer> reelConfig = new LinkedHashMap<>();
    static {
        reelConfig.put("Blank", 4);
        reelConfig.put("Cherry", 11);
        reelConfig.put("Lemon", 11);
        reelConfig.put("BAR", 6);
        reelConfig.put("Seven", 6);
        reelConfig.put("Wild", 1);
    }

    public static final List<List<Integer>> Paylines = List.of(
            List.of(0, 0, 0, 0, 0),
            List.of(1, 1, 1, 1, 1),
            List.of(2, 2, 2, 2, 2),
            List.of(0, 1, 2, 1, 0),
            List.of(2, 1, 0, 1, 2)
    );
    public static final List<Paytable> paytables = List.of(
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

    private static List<String> buildStrip() {
        List<List<String>> buckets = new ArrayList<>();
        reelConfig.forEach((sym, cnt) -> buckets.add(Collections.nCopies(cnt, sym)));
        int maxLength = Collections.max(reelConfig.values());
        List<String> symbols = new ArrayList<>();
        for (int i = 0; i < maxLength; i++) {
            for (List<String> bucket : buckets) {
                if (bucket.size() > i) {
                    symbols.add(bucket.get(i));
                }
            }
        }
        return symbols;
    }

    public  static final List<List<String>> reelStrips = List.of(
            buildStrip(),buildStrip(),buildStrip(),buildStrip(),buildStrip());

}

