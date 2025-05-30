# 계좌 관리 API (Account API)

## 프로젝트 개요
이 프로젝트는 Kotlin, Spring Boot, Spring MVC ,Redis 중복 방지 를 학습 하기 위해서 간단하게 구현 해본 API입니다.

---

### 1. 계좌 생성
- 사용자 ID와 초기 잔액으로 새 계좌를 생성합니다.
- 요청 본문: 사용자 ID, 초기 잔액
```
- 엔드포인트: `POST /account/register`
{
  "userId": 12345,
  "iniBalance": 10000
} 
```



### 2. 계좌 삭제
- 사용자 ID와 계좌 번호로 계좌를 삭제합니다.
- 요청 본문: 사용자 ID, 계좌 번호
```
- 엔드포인트: `DELETE /account/register`
{
  "userId": 12345,
  "iniBalance": 10000
} 
```

### 3. 사용자 계좌 목록 조회
- 사용자 ID로 해당 사용자의 모든 계좌 정보를 조회합니다.
- 엔드포인트: `GET /account/list?user_id={userId}`
- 응답: 계좌 정보 목록

---

## Redis 분산 락 기능

```aiignore

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AccountLock {
    long tryLockTime() default 5000L;
}

```

```aiignore
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

```

```aiignore
@Service
class LockService(
    private val redissonClient: RedissonClient,
) {

    fun getLock(accountNumber : String ){
        val lock: RLock = redissonClient.getLock("${getLockKey()}:"+accountNumber);

        logger.debug { "Lock acquired : {$accountNumber}"  };

        try{
            val isLock : Boolean = lock.tryLock(1, 10, java.util.concurrent.TimeUnit.SECONDS)

            logger.info { "Lock failed : {$isLock}"  };

            if(!isLock){
                throw  AccountException(ErrorCode.LOCK_FAILED);
            }

        }catch (e: AccountException){
            throw e;
        }
        catch (e: Exception){
            logger.error { "Lock failed : {$e}"  };
        }

    }

    fun unLock(accountNumber : String ){
        logger.debug { "Lock released : {$accountNumber}"  };
        redissonClient.getLock("${getLockKey()}:"+accountNumber).unlock();
    }

    private fun getLockKey(): String = "ACLK"
}
```


### 주요 구성 요소
1. **임베디드 Redis 서버**: 개발 및 테스트 환경에서 별도의 Redis 설치 없이 애플리케이션 내에 임베디드 Redis 서버를 실행.
2. **Redisson 클라이언트**:Redis 서버를 구현하지 않고  Redis에 기반한 분산 락을 구현하기 위해  Redisson 라이브러리를 사용.
3. **AOP 기반 락 처리**: AOP를 활용하여 특정 메소드 실행 전후에 락을 획득하고 해제하는 기능을 구현.

### 락 메커니즘
- `@AccountLock` 어노테이션이 적용된 메소드에 대해 자동으로 락 처리.
- 계좌 번호를 기반으로 한 키를 사용하여 락을 획득.
- 락 획득 시도는 최대 1초, 유지 시간은 10초로 설정되어 있습니다. (TEST 를 위함)
- 락 획득에 실패할 경우  `LOCK_FAILED`


