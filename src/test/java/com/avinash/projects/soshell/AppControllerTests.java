package com.avinash.projects.soshell;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AppControllerTests {
    @Mock
    AppShell shell;

    @InjectMocks
    AppController controller;

    @Test
    void testExecuteCommand() {
        Command command = new Command();
        command.setCommand("user login xyz xyz");
        Mockito.when(shell.processString(command.getCommand())).thenReturn("login successful");
        String response = controller.executeCommand(command);
        Assertions.assertEquals("login successful", response);
    }
}
