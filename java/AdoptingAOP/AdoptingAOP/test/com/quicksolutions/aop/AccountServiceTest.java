package com.quicksolutions.aop;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: toddkaufman
 * Date: Jun 9, 2008
 * Time: 4:35:34 PM
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:com/quicksolutions/aop/logging/loggingContext.xml", "classpath:com/quicksolutions/aop/transactions/trxContext.xml"})
@TransactionConfiguration(transactionManager = "txMgr")
public class AccountServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    private Account act1;
    private Account act2;
    private AccountService actSvc;

    @BeforeClass
    public void createTable() {
        simpleJdbcTemplate.update("CREATE TABLE account(id INTEGER IDENTITY, number BIGINT NOT NULL, holder VARCHAR NOT NULL, balance DECIMAL NOT NULL, UNIQUE(number))");
    }

    @BeforeMethod
    public void populateObjects() {
        buildAccounts();
        actSvc = (AccountService) applicationContext.getBean("accountService");
    }

    @Test(groups = {"functional"})
    public void testTransferFunds() {
        double amount = 10000;
        double act1BeforeBal = act1.getBalance();
        double act2BeforeBal = act2.getBalance();

        actSvc.transferFunds(act1, act2, amount);

        Assert.assertEquals(act1BeforeBal - amount, act1.getBalance());
        Assert.assertEquals(act2BeforeBal + amount, act2.getBalance());
    }

    @Test(groups = {"functional"})
    public void testDeposit() {
        double amount = 10000;
        double act1BeforeBal = act1.getBalance();

        actSvc.deposit(act1, amount);

        Assert.assertEquals(act1BeforeBal + amount, act1.getBalance());
    }

    @Test(groups = {"functional"})
    public void testWithdraw() {
        double amount = 10000;
        double act1BeforeBal = act1.getBalance();

        actSvc.withdraw(act1, amount);

        Assert.assertEquals(act1BeforeBal - amount, act1.getBalance());
    }

    @Test(groups = {"functional"})
    public void testFindAccount() {

        Account foundAct = actSvc.findAccount(act1.getId());

        Assert.assertEquals(act1.getId(), foundAct.getId());
        Assert.assertEquals(act1.getAccountNum(), foundAct.getAccountNum());
        Assert.assertEquals(act1.getAccountHolder(), foundAct.getAccountHolder());
        Assert.assertEquals(act1.getBalance(), foundAct.getBalance());
    }


    @AfterClass
    public void dropTable() {
        simpleJdbcTemplate.update("DROP TABLE account IF EXISTS");
    }

    private void buildAccounts() {
        act1 = new Account();
        act1.setAccountHolder("The Todd");
        act1.setAccountNum(1234123412341234L);
        act1.setBalance(new Double("1000000"));

        Map namedParams = getNamedParams(act1);
        saveAccount(act1, namedParams);

        act2 = new Account();
        act2.setAccountHolder("Tom Weinstein");
        act2.setAccountNum(4321432143214321L);
        act2.setBalance(new Double("10"));

        namedParams = getNamedParams(act2);
        saveAccount(act2, namedParams);

    }

    private void saveAccount(Account act, Map params) {
        simpleJdbcTemplate.update("insert into account(number, holder, balance) values (:num, :hold, :bal)", params);
        int key = simpleJdbcTemplate.queryForInt("select id from account where number = :num", params);
        act.setId(key);
    }

    private Map getNamedParams(Account act) {
        Map namedParams = new HashMap();
        namedParams.put("id", act.getId());
        namedParams.put("bal", act.getBalance());
        namedParams.put("hold", act.getAccountHolder());
        namedParams.put("num", act.getAccountNum());
        return namedParams;
    }

}
