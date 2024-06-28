package dev.roadfind.pharmacy.service

import dev.roadfind.AbstractIntegrationContainerBaseTest
import dev.roadfind.pharmacy.entity.Pharmacy
import dev.roadfind.pharmacy.repository.PharmacyRepository
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRepositoryServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private PharmacyRepositoryService pharmacyRepositoryService

    def setup() {
        pharmacyRepository.deleteAll()
    }

    def "PharmacyRepository update - success"() {
        given:
        String address = "서울특별시 성북구 종암동"
        String updatingAddress = "서울 광진구 구의동"
        String name = "은혜 약국"

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .build()

        when:
        def entity = pharmacyRepository.save(pharmacy)

        pharmacyRepositoryService.updateAddress(entity.getId(), updatingAddress)

        def updatedEntity = pharmacyRepository.findAll()
        then:
        updatedEntity.get(0).getPharmacyAddress() == updatingAddress

    }

}
