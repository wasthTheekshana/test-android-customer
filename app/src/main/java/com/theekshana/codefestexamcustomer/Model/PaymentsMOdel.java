package com.theekshana.codefestexamcustomer.Model;

public class PaymentsMOdel {

    String title;
    String price;
    String userName;
    String cardName;
    String cdNumber;
    String cdcvv;

    public PaymentsMOdel(String title, String price, String userName, String cardName, String cdNumber, String cdcvv) {
        this.title = title;
        this.price = price;
        this.userName = userName;
        this.cardName = cardName;
        this.cdNumber = cdNumber;
        this.cdcvv = cdcvv;
    }

    public PaymentsMOdel() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCdNumber() {
        return cdNumber;
    }

    public void setCdNumber(String cdNumber) {
        this.cdNumber = cdNumber;
    }

    public String getCdcvv() {
        return cdcvv;
    }

    public void setCdcvv(String cdcvv) {
        this.cdcvv = cdcvv;
    }
}
