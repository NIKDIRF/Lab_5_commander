package com.itmo.manager.commands;

import com.itmo.manager.Receiver;

public class ClearCommand implements Command{
    private Receiver receiver;

    public ClearCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.clear();
    }
}
