package com.poz.slot_game.service;

import org.springframework.stereotype.Service;

public class WalletService {

    private static final int INITIAL_DEPOSIT = 1000;

    private int deposit = INITIAL_DEPOSIT;

    public int getDeposit() {
        return deposit;
    }

    public void bet(int amount) {
        if (amount > deposit) {
            throw new IllegalStateException("餘額不足：餘額 " + deposit + "，欲下注 " + amount);
        }
        deposit -= amount;
    }

    public void win(int amount) {
        deposit += amount;
    }
}
