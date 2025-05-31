package com.drsimple.jwtsecurity.electronics;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/electronics")
@RequiredArgsConstructor
public class ElectronicsController {
    private final ElectronicsService electronicsService;

    @PostMapping
    public Electronics create(@RequestBody Electronics electronics) {
        return electronicsService.createElectronics(electronics);
    }
}
