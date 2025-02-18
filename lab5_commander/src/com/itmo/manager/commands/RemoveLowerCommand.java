package com.itmo.manager.commands;

import com.itmo.manager.Receiver;

public class RemoveLowerCommand implements Command{
    private Receiver receiver;

    public RemoveLowerCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.removeLower();
    }

    @Override
    public void execute(String argument) {

    }
}
