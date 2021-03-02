package com.itmo.manager.commands;

import com.itmo.manager.Receiver;

public class PrintAscendingCommand implements Command{
    private Receiver receiver;

    public PrintAscendingCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.printAscending();
    }
}
