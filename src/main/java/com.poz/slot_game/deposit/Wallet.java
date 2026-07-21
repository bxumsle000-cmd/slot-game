package com.poz.slot_game.deposit;

public class Wallet {
    private static final int DefaultDeposit = 1000;
    private int deposit = DefaultDeposit;

    public int getDeposit(){
        return  deposit;
    }

    public void bet(int betAmount){
        if(deposit>betAmount){
            deposit-= betAmount;
        } else {
            throw new IllegalStateException("餘額不足：餘額 " + deposit + "，欲下注 " + betAmount);
        }
    }

    public void win(int winAmount){
        deposit += winAmount;
    }
}
