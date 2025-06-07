package com.andres.agify_api.controller;

import com.andres.agify_api.model.AgifyResponse;
import com.andres.agify_api.service.AgifyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AgifyController {

    private final AgifyService service;

    public AgifyController(AgifyService service) {
        this.service = service;
    }

    @GetMapping("/api/age")
    public Mono<AgifyResponse> predictAge(@RequestParam String name) {
        return service.getPredictedAge(name);
    }
}