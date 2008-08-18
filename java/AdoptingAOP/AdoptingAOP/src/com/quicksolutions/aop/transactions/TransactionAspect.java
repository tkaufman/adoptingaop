package com.quicksolutions.aop.transactions;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * User: toddkaufman
 * Date: Jun 15, 2008
 * Time: 2:25:44 PM
 * To change this template use File | Settings | File Templates.
 */
@Aspect
public class TransactionAspect {

    Log log = LogFactory.getLog(TransactionAspect.class);

    @Pointcut("execution(* transferFunds(..))")
    public void methodsNamedTransfer() {
    }

    @Pointcut("target(com.quicksolutions.aop.AccountService)")
    private void accountServiceClass() {
    }

    @Pointcut("methodsNamedTransfer() && accountServiceClass()")
    private void qsiTransfers() {
    }

    @Around("qsiTransfers()")
    public Object transactionWrappingAdvice(ProceedingJoinPoint pjp) throws Throwable {

        // Begin Transaction
        log.debug("Beginning transaction");

        Object retVal = null;
        try {
            retVal = pjp.proceed();
        }
        catch (Exception ex) {
            // Roll back transaction
            log.debug("Rolling back transaction");
            return retVal;
        }

        // Commit transaction
        log.debug("Committing transaction");

        return retVal;
    }

}
