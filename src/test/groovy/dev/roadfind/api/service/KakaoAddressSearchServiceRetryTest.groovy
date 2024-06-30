package dev.roadfind.api.service

import com.fasterxml.jackson.databind.ObjectMapper
import dev.roadfind.AbstractIntegrationContainerBaseTest
import dev.roadfind.api.dto.DocumentDto
import dev.roadfind.api.dto.KakaoApiResponseDto
import dev.roadfind.api.dto.MetaDto
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

class KakaoAddressSearchServiceRetryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    //mockito 의 MockBean
    @SpringBean
    private KakaoUriBuilderService kakaoUriBuilderService = Mock()

    private MockWebServer mockWebServer

    private ObjectMapper objectMapper = new ObjectMapper()

    private String inputAddress = "서울 성북구 종암로 10길"

    def setup() {
        mockWebServer = new MockWebServer()
        mockWebServer.start()
        println mockWebServer.port
        println mockWebServer.url("/")
    }

    def cleanup() {
        mockWebServer.shutdown()
    }

    def "requestAddressSearch retry success"() {
        given:
        def metaDto = new MetaDto(1)
        def documentDto = DocumentDto.builder()
                .addressName(inputAddress)
                .build()
        def expectedResponse = new KakaoApiResponseDto(metaDto, Arrays.asList(documentDto))
        def uri = mockWebServer.url("/").uri()

        when:
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(objectMapper.writeValueAsString(expectedResponse))
        )
        def kakaoApiResult =kakaoAddressSearchService.requestAddressSearch(inputAddress)

        then:
        2 * kakaoUriBuilderService.buildUriByAddressSearch(inputAddress) >> uri
        kakaoApiResult.documentDtoList().size() == 1
        kakaoApiResult.metaDto().totalCount() == 1
        kakaoApiResult.documentDtoList().get(0).addressName() == inputAddress
    }

    def "requestAddressSearch retry failed"() {
        given:
        def uri = mockWebServer.url("/").uri()

        when:
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))

        def kakaoApiResult = kakaoAddressSearchService.requestAddressSearch(inputAddress)
        then:
        2 * kakaoUriBuilderService.buildUriByAddressSearch(inputAddress) >> uri
        kakaoApiResult == null
    }
}
