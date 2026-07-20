package com.poz.slot_game.model;

import java.util.*;
import java.util.stream.Collectors;

public class Evaluation {
    public record spinOutCome(
            List<Integer> stops,
            List<List<String>> windows,
            List<List<String>> grid,
            List<Integer> paylineMultipliers,
            int totalMultiplier
    ) {
    }

    public spinOutCome spinOutCome() {
        // stops [5]
        Random rand = new Random();
        List<Integer> stops = Reel.reelStrips.stream()
                .map(strip -> rand.nextInt(strip.size()))
                .collect(Collectors.toList());

        // windows  3*5
        List<List<String>> windows = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<String> window = reelWindow(Reel.reelStrips.get(i), stops.get(i));
            windows.add(window);
        }

        // grid  5*3
        List<List<String>> grid = windowToGrid(windows);

        // combo *5
        List<List<String>> combos = comboByPayline(Reel.Paylines,grid);
        int totalMultiplier = 0;
        // paylineMultipliers + totalMultiplier
        List<Integer> paylineMultipliers = new ArrayList<>();
        for(List<String> combo: combos){
            paylineMultipliers.add(evaluatePayline(combo));
        }
        totalMultiplier = paylineMultipliers.stream().reduce(0,Integer::sum);

        return new spinOutCome(stops, windows, grid, paylineMultipliers, totalMultiplier);
    }


    // 3*5
    public List<String> reelWindow(List<String> strip, int stop) {
        List<String> out = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int s = (stop + i) % strip.size();
            out.add(strip.get(s));
        }
        return out;
    }

    // 5*3
    public List<List<String>> windowToGrid(List<List<String>> windows) {
        int rows = windows.size();
        int cols = windows.get(0).size();
        List<List<String>> grid = new ArrayList<>();
        for (int j = 0; j < cols; j++) {
            List<String> newRow = new ArrayList<>();
            for (int i = 0; i < rows; i++) {
                newRow.add(windows.get(i).get(j));
            }
            grid.add(newRow);
        }

        return grid;
    }

    // 找出轉盤結果的5條combo
    public List<List<String>> comboByPayline(List<List<Integer>> paylines, List<List<String>> grid) {
        List<List<String>> combos = new ArrayList<>();
        for(List<Integer> line: paylines){
            List<String> combo = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                int col = i;
                int row = line.get(col);
                combo.add(grid.get(row).get(col));
        }
        combos.add(combo);
        }
        return combos;
    }

    // 找出最好的賠率
    public Reel.Paytable findMatchRule(List<String> combo) {
        for (Reel.Paytable rule : Reel.paytables) {
            boolean match = combo.subList(0, rule.count()).stream()
                    .allMatch(sym -> sym.equals(rule.symbols()));
            if (match) {
                return rule;
            }
        }
        return null;
    }

    // 替換 Wild
    public List<String> replaceWild(List<String> combo) {
        if (!combo.contains("Wild")) {
            return combo;
        }
        Set<String> symbolType = Reel.reelConfig.keySet();
        int bestMultiplier = 0;
        List<String> beatCombo = combo;
        for (String symbol : symbolType) {
            List<String> candidate = combo.stream()
                    .map(s -> s.equals("Wild") ? symbol : s)
                    .collect(Collectors.toList());
            Reel.Paytable winRule = findMatchRule(candidate);
            if (winRule!=null && winRule.multiplier() > bestMultiplier) {
                bestMultiplier = winRule.multiplier();
                beatCombo = candidate;
            }
        }
        return beatCombo;
    }

    public int evaluatePayline(List<String> combo){
        List<String> bestCombo = replaceWild(combo);
        Reel.Paytable bestRule = findMatchRule(bestCombo);
        return bestRule!= null ? bestRule.multiplier(): 0;
    }
}
