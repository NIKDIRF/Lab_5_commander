package com.itmo.manager.commands;

import com.itmo.manager.Receiver;

public class ExitCommand implements Command{
    private Receiver receiver;

    public ExitCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.exit();
    }
}
