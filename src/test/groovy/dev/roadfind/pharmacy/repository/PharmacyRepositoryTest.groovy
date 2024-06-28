package dev.roadfind.pharmacy.repository

import dev.roadfind.AbstractIntegrationContainerBaseTest
import dev.roadfind.pharmacy.entity.Pharmacy
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    def setup() {
        pharmacyRepository.deleteAll()
    }

    def "PharmacyRepository save"() {
        given:
        String address = "서울특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .longitude(longitude)
                .latitude(latitude)
                .build()

        when:
        def result = pharmacyRepository.save(pharmacy)

        then:
        result.getPharmacyAddress() == address
        result.getPharmacyName() == name
        result.getLongitude() == longitude
        result.getLatitude() == latitude
    }

    def "PharmacyRepository saveAll"() {
        given:
        String address = "서울특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .longitude(longitude)
                .latitude(latitude)
                .build()

        when:
        pharmacyRepository.saveAll(Arrays.asList(pharmacy))
        def result = pharmacyRepository.findAll()

        then:
        result.size() == 1
    }

}
