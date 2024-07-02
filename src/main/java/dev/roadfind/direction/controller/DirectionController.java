package dev.roadfind.direction.controller;


import dev.roadfind.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/dir")
@RequiredArgsConstructor
public class DirectionController {

    private final DirectionService directionService;


    @GetMapping("/{encodedId}")
    public String searchDirection(@PathVariable(value = "encodedId") String encodedId) {
        String returnUrl = directionService.searchDirectionUrlById(encodedId);

        return "redirect:"+returnUrl;
    }
}
