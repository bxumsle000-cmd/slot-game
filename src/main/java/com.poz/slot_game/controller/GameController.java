package com.poz.slot_game.controller;

import com.poz.slot_game.engine.Spin;
import com.poz.slot_game.model.SpinOutCome;
import com.poz.slot_game.model.SpinResponse;
import com.poz.slot_game.service.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class GameController {

    private final WalletService wallet = new WalletService();
    private final Spin spin = new Spin();

    /**
     * 查目前餘額。前端頁面載入時呼叫，用來顯示 #deposit。
     */
    @GetMapping("/deposit")
    public Map<String, Integer> deposit() {
        return Map.of("deposit", wallet.getDeposit());
    }

    /**
     * 下注並轉一次。流程：扣下注 → 轉盤 → 依倍率算贏分 → 加回餘額。
     * 若餘額不足，wallet.bet() 會丟例外，由下方 handleBadBet 轉成 400。
     */
    @PostMapping("/spin")
    public SpinResponse spin(@RequestParam int bet) {
        wallet.bet(bet);                                    // 先扣下注金額
        SpinOutCome outcome = spin.spinOutCome();           // 轉盤
        int win = outcome.totalMultiplier() * bet /5;          // 倍率 × 下注 = 贏分
        wallet.win(win);                                    // 贏分加回餘額
        return new SpinResponse(wallet.getDeposit(), win, outcome);
    }
}
