package dev.roadfind.api.service

import dev.roadfind.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService;

    def "address 파라미터 값이 null이면, requestAddressSearch 는 null 리턴"() {
        given:
        def address = null
        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)
        then:
        result == null
    }

    def "주소 값이 valid 하다면, requestAddressSearch 는 정상적으로 documents를 반환한다."() {
        given:
        def address = "서울 성북구 종암로 10길"
        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result.documentDtoList().size() > 0
        result.documentDtoList().get(0).addressName() != null
        result.metaDto().totalCount() > 0

    }
}
