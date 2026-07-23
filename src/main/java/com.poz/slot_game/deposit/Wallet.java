package com.poz.slot_game.deposit;

public class Wallet {
    private static final int MaxBet = 500;   // 最大下注限制
    private static final int DefaultDeposit = 1000; //初始餘額
    private int deposit = DefaultDeposit;

    // 取得目前餘額
    public int getDeposit() {
        return deposit;
    }

    // 限制下注金額
    public void bet(int betAmount) {
        if (betAmount > MaxBet) {
            throw new IllegalArgumentException("下注金額不能大於" + MaxBet);
        }
        if (deposit < betAmount) {
            throw new IllegalStateException("餘額不足：餘額 " + deposit + "，欲下注 " + betAmount);
        } else {
            deposit -= betAmount;
        }
    }

    // 新增中獎金額至餘額
    public void win(int winAmount) {
        deposit += winAmount;
    }

    // 重置餘額為初始餘額
    public void reset() {
        deposit = DefaultDeposit;
    }
}


