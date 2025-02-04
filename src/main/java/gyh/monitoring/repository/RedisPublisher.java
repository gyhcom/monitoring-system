package gyh.monitoring.repository;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisPublisher {
    private final StringRedisTemplate redisTemplate;

    public RedisPublisher(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publishHealthStatus(String server, boolean isUp) {
        redisTemplate.convertAndSend("server-health", server + " is " + (isUp ? "UP" : "DOWN"));
    }

    public void saveHealthStatus(String key, String status) {
        redisTemplate.opsForValue().set(key,status);
        System.out.println("Redis 저장 " + key + " -> "+ status );
    }
}
