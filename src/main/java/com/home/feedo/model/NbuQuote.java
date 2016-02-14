package com.home.feedo.model;

import java.math.BigDecimal;
import java.util.Date;

public class NbuQuote {

    private Date date;
    private BigDecimal bid;
    private BigDecimal ask;
    private BigDecimal vol;

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

    public BigDecimal getVol() {
        return vol;
    }

    public void setVol(BigDecimal vol) {
        this.vol = vol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NbuQuote nbuQuote = (NbuQuote) o;

        if (ask != null ? !ask.equals(nbuQuote.ask) : nbuQuote.ask != null) return false;
        if (bid != null ? !bid.equals(nbuQuote.bid) : nbuQuote.bid != null) return false;
        if (date != null ? !date.equals(nbuQuote.date) : nbuQuote.date != null) return false;
        if (vol != null ? !vol.equals(nbuQuote.vol) : nbuQuote.vol != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (bid != null ? bid.hashCode() : 0);
        result = 31 * result + (ask != null ? ask.hashCode() : 0);
        result = 31 * result + (vol != null ? vol.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NbuQuote{" +
                "date=" + date +
                ", bid=" + bid +
                ", ask=" + ask +
                ", vol=" + vol +
                '}';
    }
}
