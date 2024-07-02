package dev.roadfind.direction.service

import spock.lang.Specification

class Base62ServiceTest extends Specification {

    private Base62Service base62Service

    def setup() {
        base62Service = new Base62Service();
    }

    def "base62 의 인코딩/디코딩이 잘 동작하는 지 확인"() {
        given:
        long num = 5

        when:
        String encoded = base62Service.encodeDirectionId(num)

        long decoded = base62Service.decodeDirectionId(encoded)
        then:
        num == decoded
    }
}
