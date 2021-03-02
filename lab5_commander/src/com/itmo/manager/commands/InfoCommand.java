package com.itmo.manager.commands;

import com.itmo.manager.Receiver;

public class InfoCommand implements Command{
    private Receiver receiver;

    public InfoCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.info();
    }
}
