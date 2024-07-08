package dev.roadfind.pharmacy.cache

import dev.roadfind.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate

class RedisTemplateTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private RedisTemplate redisTemplate

    def "RedisTemplate String Operation"() {
        given:
        def valueOperation = redisTemplate.opsForValue()
        def key = "stringKey"
        def value = "hello"

        when:
        valueOperation.set(key, value)

        then:
        valueOperation.get(key) == value
    }

    def "RedisTemplate Set Operation"() {
        given:
        def setOperation = redisTemplate.opsForSet()
        def key = "setKey"

        when:
        setOperation.add(key, "h", "e", "l", "l", "o")

        then:
//        중복제거
        setOperation.size(key) == 4
    }

    def "RedisTemplate Hash Operation"() {
        given:
        def hashOperation = redisTemplate.opsForHash()
        def key = "hashKey"

        when:
        hashOperation.put(key, "subkey", "value")
        then:
        hashOperation.get(key, "subkey") == "value"

        def entries = hashOperation.entries(key)
        entries.keySet().contains("subkey")
        entries.values().contains("value")

        (int) hashOperation.size(key) == entries.size()
    }
}