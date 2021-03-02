package com.itmo.manager.commands;

import com.itmo.manager.Receiver;

public class AddCommand implements Command{
    private Receiver receiver;

    public AddCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.add();
    }
}
