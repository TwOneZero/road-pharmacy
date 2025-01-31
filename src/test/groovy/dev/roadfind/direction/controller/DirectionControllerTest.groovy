package dev.roadfind.direction.controller

import dev.roadfind.direction.service.DirectionService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class DirectionControllerTest extends Specification {

    private MockMvc mockMvc
    private DirectionService directionService = Mock()

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DirectionController(directionService)).build()
    }

    def "GET [/dir/{encodedId}]"() {
        given:
        String encodedId = "e"
        String redirectUrl = "https://map.kakao.com/link/map/pharmacy,38.11,128.11"

        when:
        directionService.searchDirectionUrlById(encodedId) >> redirectUrl
        def result = mockMvc.perform(get("/dir/{encodedId}",encodedId))

        then:
        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(redirectUrl))
                .andDo(print())
    }
}
