package com.itmo.manager.commands;

import com.itmo.manager.Receiver;

public class ShowCommand implements Command{
    private Receiver receiver;

    public ShowCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.show();
    }

    @Override
    public void execute(String argument) {

    }

}
