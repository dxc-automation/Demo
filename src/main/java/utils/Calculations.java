package utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

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

    public static float closeAtProfitCalc(int number, int percent) {
        float percentFloat = (float)percent / 100;
        float outValueFloat = (number * percentFloat);
        float result = outValueFloat + number;
        System.out.println(Math.round(result));
        return result;
    }

    public static double closeAtLossCalc(int number, int percent) {
        float percentFloat = (float)percent / 100;
        float outValueFloat = (number * percentFloat);
        float result = outValueFloat - number;
        System.out.println(Math.round(result));
        return result;
    }

    public static BigDecimal parseToBigDecimal(String value) throws ParseException {
        DecimalFormat format = new DecimalFormat("#,##0.00");
        format.setParseBigDecimal(true);

        BigDecimal decimal = (BigDecimal) format.parse(value);
        return decimal;
    }

    public static double convertDollarsToDouble(String value) throws ParseException {
        System.out.println("Parsing: " + value);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
        Number parsed = numberFormat.parse(value);

        return parsed.doubleValue();
    }

    public static double convertLevaToDouble(String value) throws ParseException {
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("BGN");
        dfs.setGroupingSeparator('.');
        dfs.setDecimalSeparator(',');
        df.setDecimalFormatSymbols(dfs);
        final Number parsed = df.parse(value);
        System.out.println(parsed);
        return parsed.doubleValue();
    }

    public static double convertStringToDouble(String value) throws ParseException {
        double price = 0;
        Locale locale = new Locale("bg", "BG");
        NumberFormat format = NumberFormat.getInstance(locale);
        Number parsed = format.parse(value);
        System.out.println("Coverting: " + parsed.doubleValue());
        try {
            price = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return price;
    }

    public static double calculateDifference(double v1, double v2) {
        double average = (v1 + v2) / 2;
        if (average == 0) {
            throw new IllegalArgumentException("The average of V1 and V2 cannot be zero.");
        }
        return Math.abs((v1 - v2) / average) * 100;
    }
}
