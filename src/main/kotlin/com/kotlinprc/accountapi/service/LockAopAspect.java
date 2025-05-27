package com.kotlinprc.accountapi.service;

import com.kotlinprc.accountapi.aop.AccountLockIdInterface;
import com.kotlinprc.accountapi.model.dto.UseBalance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LockAopAspect {

    private final LockService lockService;

    @Around("@annotation(com.kotlinprc.accountapi.aop.AccountLock) && args(request)")
    public Object aroundMethod(
            ProceedingJoinPoint pjp, AccountLockIdInterface request
    ) throws Throwable {
        // lock 획득
        lockService.getLock(request.getAccountNumber());
        try{
            // before
            return pjp.proceed();
        }finally{
            // lock 해제
            lockService.unLock(request.getAccountNumber());
        }
    }
}
