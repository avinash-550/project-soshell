package com.avinash.projects.soshell;

import org.jline.terminal.Terminal;
import org.springframework.shell.ResultHandlerService;
import org.springframework.shell.Shell;
import org.springframework.shell.command.CommandCatalog;
import org.springframework.shell.context.ShellContext;
import org.springframework.shell.exit.ExitCodeMappings;
import org.springframework.stereotype.Component;

@Component
public class AppShell extends Shell {

    public AppShell(ResultHandlerService resultHandlerService, CommandCatalog commandRegistry, Terminal terminal,
            ShellContext shellContext, ExitCodeMappings exitCodeMappings) {
        super(resultHandlerService, commandRegistry, terminal, shellContext, exitCodeMappings);
    }

    public String processString(String input) {
        Object result = this.evaluate(() -> input);
        String resultMessage;
        if (result != null) {
            resultMessage = result.toString();
        } else {
            resultMessage = "No result returned from command.";
        }
        return resultMessage;
    }

}
