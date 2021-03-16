package com.itmo.manager.commands;

import com.itmo.manager.Receiver;

public class PrintFieldDescendingFormOfEducationCommand implements Command{
    private Receiver receiver;

    public PrintFieldDescendingFormOfEducationCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.printFieldDescendingFormOfEducation();
    }

    @Override
    public void execute(String argument) {

    }
}
