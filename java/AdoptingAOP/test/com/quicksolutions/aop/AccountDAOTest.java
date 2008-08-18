package com.quicksolutions.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.testng.annotations.*;
import org.testng.Assert;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: toddkaufman
 * Date: Jun 9, 2008
 * Time: 4:35:07 PM
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@TransactionConfiguration(transactionManager="txMgr")
public class AccountDAOTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private AccountDAO targetDAO;

    public AccountDAOTest() {
        super();
    }

    @BeforeClass
    public void createTable() {
        simpleJdbcTemplate.update("CREATE TABLE account(id INTEGER IDENTITY, number BIGINT NOT NULL, holder VARCHAR NOT NULL, balance DECIMAL NOT NULL, UNIQUE(number))");
    }

    @Test(groups={"integration"})
    public void testSave() {
        int beforeCount = countRowsInTable("account");
        Account act = buildAccount();
        targetDAO.save(act);
        int afterCount = countRowsInTable("account");
        Assert.assertEquals(beforeCount, afterCount-1);
    }

    @Test(groups={"integration"})
    public void testDelete() {
        Account act = buildAccount();
        targetDAO.save(act);
        int beforeCount = countRowsInTable("account");
        targetDAO.delete(act);
        int afterCount = countRowsInTable("account");
        Assert.assertEquals(beforeCount, afterCount+1);
    }

    @Test(groups={"integration"})
    public void testFindByPK() {
        Account act = buildAccount();
        targetDAO.save(act);
        Account foundAct = targetDAO.findByPK(act.getId());
        assertEqualContents(act, foundAct);
    }

    @Test(groups={"integration"})
    public void testFindByAccountHolder() {
        Account act = buildAccount();
        targetDAO.save(act);
        List<Account> foundActs = targetDAO.findByAccountHolder(act.getAccountHolder());
        Assert.assertEquals(foundActs.size(), 1);
        assertEqualContents(act, foundActs.get(0));
    }

    @AfterClass
    public void dropTable() {
        simpleJdbcTemplate.update("DROP TABLE account IF EXISTS");
    }

    private Account buildAccount() {
        Account act = new Account();
        act.setAccountHolder("The Todd");
        act.setAccountNum(1234123412341234L);
        act.setBalance(new Double("1000000"));

        Map namedParams = new HashMap();
        namedParams.put("id", act.getId());
        namedParams.put("bal", act.getBalance());
        namedParams.put("hold", act.getAccountHolder());
        namedParams.put("num", act.getAccountNum());

        simpleJdbcTemplate.update("insert into account(number, holder, balance) values (:num, :hold, :bal)", namedParams);
        int key = simpleJdbcTemplate.queryForInt("select id from account where number = :num", namedParams);
        act.setId(key);

        return act;
    }

    private void assertEqualContents(Account act1, Account act2) {
        Assert.assertEquals(act1.getId(), act2.getId());
        Assert.assertEquals(act1.getAccountNum(), act2.getAccountNum());
        Assert.assertEquals(act1.getAccountHolder(), act2.getAccountHolder());
        Assert.assertEquals(act1.getBalance(), act2.getBalance());
    }

}
