package com.itmo.manager.commands;

import com.itmo.manager.Receiver;

public class PrintUniqueExpelledStudentsCommand implements Command{
    private Receiver receiver;

    public PrintUniqueExpelledStudentsCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.printUniqueExpelledStudents();
    }

    @Override
    public void execute(String argument) {

    }
}
