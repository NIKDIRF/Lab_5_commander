package com.itmo.manager.commands;

import com.itmo.manager.Receiver;

public class RemoveByIdCommand implements Command{
    private Receiver receiver;

    public RemoveByIdCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {

    }

    @Override
    public void execute(String argument) {
        this.receiver.removeById(argument);
    }
}
