package util;

import org.springframework.stereotype.Component;
import com.google.common.util.concurrent.RateLimiter;

@Component
public class TranslationRateLimiter {
    private final RateLimiter rateLimiter = RateLimiter.create(10.0); // 초당 10건 허용

    public void acquire() {
        rateLimiter.acquire(); // 호출 전 블로킹 대기
    }
}
