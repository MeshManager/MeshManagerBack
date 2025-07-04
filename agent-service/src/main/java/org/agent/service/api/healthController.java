package org.agent.service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class healthController {
    @GetMapping(value = "/health", produces = "application/json")
    public String getALBHealth() {
        return "Server Status OK";
    }
}