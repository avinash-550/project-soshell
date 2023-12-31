package com.avinash.projects.soshell;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    private final AppShell shell;

    public AppController(AppShell shell) {
        this.shell = shell;
    }

    @PostMapping("/process")
    public String executeCommand(@RequestBody Command command) {
        String result = shell.processString(command.getCommand());
        return result;
    }
}
