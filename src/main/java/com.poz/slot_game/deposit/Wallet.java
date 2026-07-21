package com.poz.slot_game.deposit;

public class Wallet {
    private static final int MaxBet = 500;
    private static final int DefaultDeposit = 1000;
    private int deposit = DefaultDeposit;

    public int getDeposit(){
        return  deposit;
    }

    public void bet(int betAmount){
        if(betAmount > MaxBet){
            throw new IllegalArgumentException("下注金額不能大於"+MaxBet);
        }
        if(deposit < betAmount){
            throw new IllegalStateException("餘額不足：餘額 " + deposit + "，欲下注 " + betAmount);
        } else {
            deposit -= betAmount;
        }
    }

    public void win(int winAmount){
        deposit += winAmount;
    }
}
