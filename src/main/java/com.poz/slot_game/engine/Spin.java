package com.poz.slot_game.engine;

import com.poz.slot_game.model.Paytable;
import com.poz.slot_game.model.SpinOutCome;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Spin {

    private Evaluation evaluation = new Evaluation();

    public SpinOutCome spinOutCome() {
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
            paylineMultipliers.add(evaluation.evaluatePayline(combo));
        }
        totalMultiplier = paylineMultipliers.stream().reduce(0,Integer::sum);

        return new SpinOutCome(stops, windows, grid, paylineMultipliers, totalMultiplier);
    }


    // 3*5
    private List<String> reelWindow(List<String> strip, int stop) {
        List<String> out = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int s = (stop + i) % strip.size();
            out.add(strip.get(s));
        }
        return out;
    }

    // 5*3
    private List<List<String>> windowToGrid(List<List<String>> windows) {
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
    private List<List<String>> comboByPayline(List<List<Integer>> paylines, List<List<String>> grid) {
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


}
