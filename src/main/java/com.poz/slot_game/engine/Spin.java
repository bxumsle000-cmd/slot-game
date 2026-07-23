package com.poz.slot_game.engine;

import com.poz.slot_game.model.SpinOutCome;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Spin {

    private Evaluation evaluation = new Evaluation();

    public SpinOutCome spinOnce() {
        // stops [5] : 生成隨機停格的位置
        Random rand = new Random();
        List<Integer> stops = Reel.reelStrips.stream()
                .map(strip -> rand.nextInt(strip.size()))
                .collect(Collectors.toList());

        // windows：以「每條 reel」為單位（直的一欄），每個元素是該 reel 由上到下 3 格 symbol
        // → 結構是 [reel0[3格], reel1[3格], reel2[3格], reel3[3格], reel4[3格]]（5欄 x 3格，column-major）
        List<List<String>> windows = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<String> window = reelWindow(Reel.reelStrips.get(i), stops.get(i));
            windows.add(window);
        }

        // grid：把 windows 轉置成「每一列(row)」為單位（橫的一排）
        // → 結構是 [row0[5格], row1[5格], row2[5格]]（3列 x 5格，row-major），方便之後用 payline 的 row index 查表
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

    // 取出「單一 reel」從 stop 開始往下 3 格 symbol（含環繞），代表這條 reel 由上到下看到的畫面
    private List<String> reelWindow(List<String> strip, int stop) {
        List<String> out = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int s = (stop + i) % strip.size();
            out.add(strip.get(s));
        }
        return out;
    }

    // 將 column-major 的 windows（以 reel 為單位）轉置成 row-major 的 grid（以橫排 row 為單位）
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
