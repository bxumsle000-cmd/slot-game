package com.poz.slot_game.controller;

import com.poz.slot_game.engine.Spin;
import com.poz.slot_game.model.SpinOutCome;
import com.poz.slot_game.model.SpinResponse;
import com.poz.slot_game.deposit.Wallet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class GameController {
    private Spin spin = new Spin();
    private Wallet wallet = new Wallet();

    // 取得目前餘額
    @GetMapping("/deposit")
    public Map<String,Integer> deposit() {
        return Map.of("deposit", wallet.getDeposit());
    }

    // 按下spin後，更新餘額
    @PostMapping("/spin")
    public SpinResponse bet(@RequestParam int betAmount){
        wallet.bet(betAmount);
        int afterBet = wallet.getDeposit();
        SpinOutCome outcome = spin.spinOnce();
        int winAmount = outcome.totalMultiplier() * betAmount /5 ;
        wallet.win(winAmount);
        return new SpinResponse(afterBet,wallet.getDeposit(),winAmount,outcome);
    }

    // 重置初始餘額
    @PostMapping("/reset")
    public Map<String, Integer> reset() {
        wallet.reset();
        return Map.of("deposit", wallet.getDeposit());
    }
}
