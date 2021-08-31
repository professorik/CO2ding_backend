package com.ducklings;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class Controller {

    public Controller() {}

    @GetMapping()
    public String getBranches() { return "Hello, World!"; }
}
