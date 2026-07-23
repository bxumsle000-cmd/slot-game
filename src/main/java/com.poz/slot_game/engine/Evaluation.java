package com.poz.slot_game.engine;

import com.poz.slot_game.model.Paytable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Evaluation {

    // 找出最好的賠率
    private Paytable findMatchRule(List<String> combo) {
        for (Paytable rule : Reel.paytables) {
            boolean match = combo.subList(0, rule.count()).stream()
                    .allMatch(sym -> sym.equals(rule.symbols()));
            if (match) {
                return rule;
            }
        }
        return null;
    }

    // 透過findMatchRule，找出將Wild替換成哪一個符號會有就好的賠率
    private List<String> replaceWild(List<String> combo) {
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
            Paytable winRule = findMatchRule(candidate);
            if (winRule!=null && winRule.multiplier() > bestMultiplier) {
                bestMultiplier = winRule.multiplier();
                beatCombo = candidate;
            }
        }
        return beatCombo;
    }
    // 先替換wild ，再找出最好的賠率。
    public int evaluatePayline(List<String> combo){
        List<String> bestCombo = replaceWild(combo);
        Paytable bestRule = findMatchRule(bestCombo);
        return bestRule!= null ? bestRule.multiplier(): 0;
    }
}
