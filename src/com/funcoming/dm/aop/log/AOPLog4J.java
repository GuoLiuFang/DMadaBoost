package com.funcoming.dm.aop.log;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import java.io.Serializable;

/**
 * Created by LiuFangGuo on 4/5/16.
 * <p>
 * Before  在方法调用前
 * After    在方法调用后,无论成功与否
 * After-returning  在方法调用后,有且仅有,执行成功时
 * After-throwing   在方法抛出异常后调用
 * Around
 */
public class AOPLog4J implements Serializable {
    private Logger logger;

    public AOPLog4J() {
        this.logger = Logger.getLogger(AOPLog4J.class);
    }

    public void commonBeforeMethod(JoinPoint joinPoint) {
        logger.debug(joinPoint.toLongString());
        Object[] args = joinPoint.getArgs();
        for (Object object :
                args) {
            logger.debug(joinPoint.toString() + "函数的input是" + object.toString());
        }
    }

    public void commonAfterReturning(JoinPoint joinPoint, Object returnValue) {
        logger.debug(joinPoint.toLongString() + "函数的output是" + returnValue);
    }

    public void commondAround(ProceedingJoinPoint proceedingJoinPoint) {
        logger.debug(proceedingJoinPoint.toLongString());
        long startTime = System.currentTimeMillis();
        try {
            proceedingJoinPoint.proceed();//切入点的方法继续执行
            long finishTime = System.currentTimeMillis();
            logger.debug(proceedingJoinPoint.toString() + "函数的执行Time是" + (finishTime - startTime) + "毫秒");
        } catch (Throwable throwable) {
            Object[] args = proceedingJoinPoint.getArgs();
            for (Object object :
                    args) {
                logger.debug(proceedingJoinPoint.toString() + "函数的input是" + object.toString());
            }
            logger.error("函数执行发生错误或者异常" + throwable);
        }
    }

}
