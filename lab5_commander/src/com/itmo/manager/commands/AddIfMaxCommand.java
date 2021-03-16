package com.itmo.manager.commands;

import com.itmo.manager.Receiver;

public class AddIfMaxCommand implements Command{
    private Receiver receiver;

    public AddIfMaxCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.addIfMax();
    }

    @Override
    public void execute(String argument) {

    }
}
