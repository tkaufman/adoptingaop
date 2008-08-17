package com.quicksolutions.aop;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: toddkaufman
 * Date: Jun 9, 2008
 * Time: 2:04:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AccountDAO {

    public void save(Account act);

    public void delete(Account act);

    public Account findByPK(Integer id);

    public List<Account> findByAccountHolder(String holder);
}
