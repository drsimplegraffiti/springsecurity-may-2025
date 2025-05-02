package com.drsimple.jwtsecurity.wallet;


public class TransactionLimits {
    private boolean withdrawalEnabled;
    private boolean depositEnabled;
    private double maxDailyWithdrawal;
    private double maxDepositAmount;

    public boolean isWithdrawalEnabled() {
        return withdrawalEnabled;
    }

    public void setWithdrawalEnabled(boolean withdrawalEnabled) {
        this.withdrawalEnabled = withdrawalEnabled;
    }

    public boolean isDepositEnabled() {
        return depositEnabled;
    }

    public void setDepositEnabled(boolean depositEnabled) {
        this.depositEnabled = depositEnabled;
    }

    public double getMaxDailyWithdrawal() {
        return maxDailyWithdrawal;
    }

    public void setMaxDailyWithdrawal(double maxDailyWithdrawal) {
        this.maxDailyWithdrawal = maxDailyWithdrawal;
    }

    public double getMaxDepositAmount() {
        return maxDepositAmount;
    }

    public void setMaxDepositAmount(double maxDepositAmount) {
        this.maxDepositAmount = maxDepositAmount;
    }
}
