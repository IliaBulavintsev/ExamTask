package com.example.ilia.examtask.controller;


public class Calculator {

    private String amount;
    private double fromNominal;
    private double fromValue;
    private double toNominal;
    private double toValue;

    public Calculator(String amount, double fromNominal, double fromValue, double toNominal, double toValue) {
        this.amount = amount;
        this.fromNominal = fromNominal;
        this.fromValue = fromValue;
        this.toNominal = toNominal;
        this.toValue = toValue;
    }

    public double performCalculation() {

        return Double.parseDouble(this.amount) * (this.fromNominal * this.fromValue)/
                (this.toNominal * this.toValue);
    }
}
