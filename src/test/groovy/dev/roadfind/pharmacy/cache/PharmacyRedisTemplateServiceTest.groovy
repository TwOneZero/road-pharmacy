package dev.roadfind.pharmacy.cache

import dev.roadfind.AbstractIntegrationContainerBaseTest
import dev.roadfind.pharmacy.dto.PharmacyDto
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRedisTemplateServiceTest extends AbstractIntegrationContainerBaseTest {


    @Autowired
    private PharmacyRedisTemplateService pharmacyRedisTemplateService

    def setup() {
        pharmacyRedisTemplateService.findAll()
        .forEach(dto -> {
            pharmacyRedisTemplateService.delete(dto.id())
        })
    }

    def "save success"() {
        given:
        String pharmacyName = "name"
        String pharmacyAddress = "address"
        PharmacyDto dto = PharmacyDto.builder()
                .id(1L)
                .pharmacyName(pharmacyName)
                .pharmacyAddress(pharmacyAddress)
                .build()

        when:
        pharmacyRedisTemplateService.save(dto)
        List<PharmacyDto> result = pharmacyRedisTemplateService.findAll()

        then:
        result.size() == 1
        result.get(0).id() == 1L
        result.get(0).pharmacyName() == pharmacyName


    }

    def "save fail - dto 가 null 이거나 id 가 없다면 에러 반환"() {
        given:
        PharmacyDto dto = PharmacyDto.builder()
                .build()
        when:
        pharmacyRedisTemplateService.save(dto)
        List<PharmacyDto> result = pharmacyRedisTemplateService.findAll()

        then:
        result.size() == 0
    }


}
