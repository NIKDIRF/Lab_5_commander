package com.itmo.manager.commands;

import com.itmo.manager.Receiver;

public class UpdateCommand implements Command{
    private Receiver receiver;

    public UpdateCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {

    }

    @Override
    public void execute(String argument) {
        this.receiver.update(argument);
    }
}
