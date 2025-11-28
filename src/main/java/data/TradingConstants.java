package data;

import java.math.BigDecimal;

public class TradingConstants {

    //  TRADE
    private String nameFirstRow;
    private double profitFirstRow;
    private String currentValueFirstRow;
    private String changeFirstRow;
    private String adjustmentsFirstRow;
    private String overnightFundingFirstRow;
    private String net;

    private double buyPrice;
    private double sellPrice;
    private String percents;
    private BigDecimal highAverage;
    private BigDecimal lowAverage;
    private BigDecimal highMax;
    private String expectedProfit;


    public void setExpectedProfit(String expectedProfit) { this.expectedProfit = expectedProfit; }

    public String getExpectedProfit() { return expectedProfit; }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setPercents(String percents) {
        this.percents = percents;
    }

    public String getPercents() {
        return percents;
    }

    public void setHighAverage(BigDecimal highAverage) {
        this.highAverage = highAverage;
    }

    public BigDecimal getHighAverage() {
        return highAverage;
    }

    public void setLowAverage(BigDecimal lowAverage) {
        this.lowAverage = lowAverage;
    }

    public BigDecimal getLowAverage() {
        return lowAverage;
    }

    public void setHighMax(BigDecimal highMax) { this.highMax = highMax; }

    public BigDecimal getHighMax() { return highMax; }

    public void setOvernightFundingFirstRow(String overnightFundingFirstRow) {
        this.overnightFundingFirstRow = overnightFundingFirstRow;
    }

    public String getOvernightFundingFirstRow() {
        return overnightFundingFirstRow;
    }

    public void setAdjustmentsFirstRow(String adjustmentsFirstRow) {
        this.adjustmentsFirstRow = adjustmentsFirstRow;
    }

    public String getAdjustmentsFirstRow() {
        return adjustmentsFirstRow;
    }

    public void setChangeFirstRow(String changeFirstRow) {
        this.changeFirstRow = changeFirstRow;
    }

    public String getChangeFirstRow() {
        return changeFirstRow;
    }

    public void setCurrentValueFirstRow(String currentValueFirstRow) {
        this.currentValueFirstRow = currentValueFirstRow;
    }

    public String getCurrentValueFirstRow() {
        return currentValueFirstRow;
    }

    public void setProfitFirstRow(double profitFirstRow) {
        this.profitFirstRow = profitFirstRow;
    }

    public double getProfitFirstRow() {
        return profitFirstRow;
    }

    public void setNameFirstRow(String nameFirstRow) {
        this.nameFirstRow = nameFirstRow;
    }

    public String getNameFirstRow() {
        return nameFirstRow;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getNet() {
        return net;
    }
}