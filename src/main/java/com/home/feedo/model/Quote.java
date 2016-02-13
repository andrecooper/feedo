package com.home.feedo.model;

import java.util.Date;

/**
 * Created by andrew on 09.02.16.
 */

public class Quote {
    private Date date;
    private Double bid;
    private Double ask;
    private Double avarage;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public Double getAvarage() {
        return avarage;
    }

    public void setAvarage(Double avarage) {
        this.avarage = avarage;
    }

    @Override
    public String toString() {
        return "MarketQuote{" +
                "date=" + date +
                ", bid=" + bid +
                ", ask=" + ask +
                ", avarage=" + avarage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quote that = (Quote) o;

        if (ask != null ? !ask.equals(that.ask) : that.ask != null) return false;
        if (avarage != null ? !avarage.equals(that.avarage) : that.avarage != null) return false;
        if (bid != null ? !bid.equals(that.bid) : that.bid != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (bid != null ? bid.hashCode() : 0);
        result = 31 * result + (ask != null ? ask.hashCode() : 0);
        result = 31 * result + (avarage != null ? avarage.hashCode() : 0);
        return result;
    }


}
