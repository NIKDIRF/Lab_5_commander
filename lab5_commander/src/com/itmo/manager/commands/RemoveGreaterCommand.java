package com.itmo.manager.commands;

import com.itmo.manager.Receiver;

public class RemoveGreaterCommand implements Command{
    private Receiver receiver;

    public RemoveGreaterCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.removeGreater();
    }

    @Override
    public void execute(String argument) {

    }
}
