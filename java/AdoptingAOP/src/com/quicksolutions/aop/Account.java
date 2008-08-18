package com.quicksolutions.aop;

/**
 * Created by IntelliJ IDEA.
 * User: toddkaufman
 * Date: Jun 9, 2008
 * Time: 1:44:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class Account {

    private Integer id;

    private Long accountNum;

    private Double balance;

    private String accountHolder;

    public Account() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Long accountNum) {
        this.accountNum = accountNum;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }
}
