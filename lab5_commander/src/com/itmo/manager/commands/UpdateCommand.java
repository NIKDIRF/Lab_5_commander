package com.itmo.manager.commands;

import com.itmo.manager.Receiver;

public class UpdateCommand implements Command{
    private Receiver receiver;

    public UpdateCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.update();
    }
}
