package com.quicksolutions.aop;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: toddkaufman
 * Date: Jun 9, 2008
 * Time: 2:06:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccountDAOImpl implements AccountDAO {

    private SimpleJdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource ds) {
        this.jdbcTemplate = new SimpleJdbcTemplate(ds);
    }

    public void save(Account act) {
        Map namedParams = getNamedParams(act);

        if (act.getId() != null) {
            jdbcTemplate.update("update account set balance = :bal, holder = :hold where id = :id", namedParams);
        }
        else {
            jdbcTemplate.update("insert (number, holder, balance) into account values (:num, :hold, :bal)", namedParams);
            int key = jdbcTemplate.queryForInt("select id from account where number = :num", namedParams);
            act.setId(key);
        }
    }

    public void delete(Account act) {
        Map namedParams = getNamedParams(act);
        jdbcTemplate.update("delete from account where id = :id", namedParams);
    }

    public Account findByPK(Integer id) {
        Map namedParams = new HashMap();
        namedParams.put("id", id);
        return jdbcTemplate.queryForObject("select id, number, holder, balance from account where id = :id", new AccountMapper(), namedParams);
    }

    public List<Account> findByAccountHolder(String holder) {
        Map namedParams = new HashMap();
        namedParams.put("hold", holder);
        return jdbcTemplate.query("select id, number, holder, balance from account where holder = :hold", new AccountMapper(), namedParams);
    }


    private Map getNamedParams(Account act) {
        Map namedParams = new HashMap();
        namedParams.put("id", act.getId());
        namedParams.put("bal", act.getBalance());
        namedParams.put("hold", act.getAccountHolder());
        namedParams.put("num", act.getAccountNum());
        return namedParams;
    }

    private static final class AccountMapper implements ParameterizedRowMapper<Account> {

        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            Account act = new Account();
            act.setId(rs.getInt("id"));
            act.setAccountNum(rs.getLong("number"));
            act.setAccountHolder(rs.getString("holder"));
            act.setBalance(rs.getDouble("balance"));
            return act;
        }
    }
}
