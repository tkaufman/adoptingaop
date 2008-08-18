package com.quicksolutions.aop;

/**
 * Created by IntelliJ IDEA.
 * User: toddkaufman
 * Date: Jun 9, 2008
 * Time: 1:31:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AccountService {

    public void transferFunds(Account from, Account to, Double amount);

    public void deposit(Account act, Double amount);

    public void withdraw(Account act, Double amount);

    public Account findAccount(Integer id);
}
