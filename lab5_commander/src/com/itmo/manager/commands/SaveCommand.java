package com.itmo.manager.commands;

import com.itmo.manager.Receiver;

public class SaveCommand implements Command {
    private Receiver receiver;

    public SaveCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.save();
    }
}
