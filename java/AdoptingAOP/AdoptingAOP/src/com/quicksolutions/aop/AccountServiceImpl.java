package com.quicksolutions.aop;

/**
 * Created by IntelliJ IDEA.
 * User: toddkaufman
 * Date: Jun 9, 2008
 * Time: 1:56:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccountServiceImpl implements AccountService {

    private AccountDAO dao;

    public AccountDAO getDao() {
        return dao;
    }

    public void setDao(AccountDAO dao) {
        this.dao = dao;
    }

    public void transferFunds(Account from, Account to, Double amount) {
        withdraw(from, amount);
        deposit(to, amount);
    }

    public void deposit(Account act, Double amount) {
        act.setBalance(act.getBalance() + amount);
        dao.save(act);
    }

    public void withdraw(Account act, Double amount) {
        act.setBalance(act.getBalance() - amount);
        dao.save(act);
    }

    public Account findAccount(Integer id) {
       return dao.findByPK(id);
    }
}
