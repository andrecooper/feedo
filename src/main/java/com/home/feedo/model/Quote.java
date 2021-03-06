package com.home.feedo.model;

import java.math.BigDecimal;
import java.util.Date;

public class Quote {
    private Date date;
    private BigDecimal bid;
    private BigDecimal ask;
    private BigDecimal average;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public BigDecimal getAverage() {
        return average;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quote quote = (Quote) o;

        if (ask != null ? !ask.equals(quote.ask) : quote.ask != null) return false;
        if (average != null ? !average.equals(quote.average) : quote.average != null) return false;
        if (bid != null ? !bid.equals(quote.bid) : quote.bid != null) return false;
        if (date != null ? !date.equals(quote.date) : quote.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (bid != null ? bid.hashCode() : 0);
        result = 31 * result + (ask != null ? ask.hashCode() : 0);
        result = 31 * result + (average != null ? average.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "date=" + date +
                ", bid=" + bid +
                ", ask=" + ask +
                ", average=" + average +
                '}';
    }
}
