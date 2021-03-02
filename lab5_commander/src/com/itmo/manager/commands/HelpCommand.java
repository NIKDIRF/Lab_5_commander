package com.itmo.manager.commands;

import com.itmo.manager.Receiver;

public class HelpCommand implements Command{
    private Receiver receiver;

    public HelpCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.help();
    }
}
