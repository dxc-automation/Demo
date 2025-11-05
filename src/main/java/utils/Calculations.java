package utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Calculations {

    public static BigDecimal calculateBalanceAfterSingleSpin(String balanceBefore, String stake, String lastWinAmount) {
        String formattedBalance = new DecimalFormat("0.####").format(Double.parseDouble(balanceBefore.replace("$", "")));

        BigDecimal balance = new BigDecimal(formattedBalance);
        BigDecimal stakeAmount = new BigDecimal(stake.replace("$", ""));
        BigDecimal resultAfterSpin = balance.subtract(stakeAmount);

        if (lastWinAmount.equalsIgnoreCase("$0.00")) {
            return resultAfterSpin;
        } else {
            BigDecimal lastWin = new BigDecimal(lastWinAmount.replace("$", ""));
            return resultAfterSpin.add(lastWin);
        }
    }

    public static BigDecimal calculateBalanceAfterMultipleSpins(String balanceBefore, String stake, int numberOfSpins, String lastWinAmount) {
        String formattedBalance = new DecimalFormat("0.####").format(Double.parseDouble(balanceBefore.replace("$", "")));

        BigDecimal balance = new BigDecimal(formattedBalance);
        BigDecimal spins = new BigDecimal(numberOfSpins);
        BigDecimal stakeAmount = new BigDecimal(stake.replace("$", "")).multiply(spins);
        BigDecimal resultAfterSpin = balance.subtract(stakeAmount);

        if (lastWinAmount.equalsIgnoreCase("$0.00")) {
            return resultAfterSpin;
        } else {
            BigDecimal lastWin = new BigDecimal(lastWinAmount.replace("$", ""));
            return resultAfterSpin.add(lastWin);
        }
    }
}
