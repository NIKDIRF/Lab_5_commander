package com.itmo.manager;

import com.itmo.manager.commands.Command;

import javax.swing.*;
import java.io.IOException;

public class Invoker {
    private Command exitCommand;
    private Command addCommand;
    private Command removeByIdCommand;
    private Command clearCommand;
    private Command saveCommand;
    private Command helpCommand;
    private Command infoCommand;
    private Command updateCommand;
    private Command addIfMaxCommand;
    private Command removeGreaterCommand;
    private Command removeLowerCommand;
    private Command showCommand;
    private Command printAscendingCommand;
    private Command printUniqueExpelledStudentsCommand;
    private Command printFieldDescendingFormOfEducationCommand;

    public Invoker(Command exitCommand, Command addCommand, Command removeByIdCommandCommand, Command clearCommand,
                   Command saveCommand, Command helpCommand, Command infoCommand, Command updateCommand, Command addIfMaxCommand,
                   Command removeGreaterCommand, Command removeLowerCommand,Command showCommand, Command printAscendingCommand,
                   Command printUniqueExpelledStudentsCommand, Command printFieldDescendingFormOfEducationCommand) {
        this.exitCommand = exitCommand;
        this.addCommand = addCommand;
        this.removeByIdCommand = removeByIdCommandCommand;
        this.clearCommand = clearCommand;
        this.saveCommand = saveCommand;
        this.helpCommand = helpCommand;
        this.infoCommand = infoCommand;
        this.updateCommand = updateCommand;
        this.addIfMaxCommand = addIfMaxCommand;
        this.removeGreaterCommand = removeGreaterCommand;
        this.removeLowerCommand = removeLowerCommand;
        this.showCommand = showCommand;
        this.printAscendingCommand = printAscendingCommand;
        this.printUniqueExpelledStudentsCommand = printUniqueExpelledStudentsCommand;
        this.printFieldDescendingFormOfEducationCommand = printFieldDescendingFormOfEducationCommand;
    }

    public void exit() {
        this.exitCommand.execute();
    }

    public void add() {
        this.addCommand.execute();
    }

    public void removeById(String argument) {
        this.removeByIdCommand.execute(argument);
    }

    public void clear() {
        this.clearCommand.execute();
    }

    public void save() {
        this.saveCommand.execute();
    }

    public void help() {
        this.helpCommand.execute();
    }

    public void info() {
        this.infoCommand.execute();
    }

    public void update(String argument) {
        this.updateCommand.execute(argument);
    }

    public void addIfMax() {
        this.addIfMaxCommand.execute();
    }

    public void removeGreater() {
        this.removeGreaterCommand.execute();
    }

    public void removeLower() {
        this.removeLowerCommand.execute();
    }

    public void show() {
        this.showCommand.execute();
    }

    public void printAscending() {
        this.printAscendingCommand.execute();
    }

    public void printUniqueExpelledStudents() {
        this.printUniqueExpelledStudentsCommand.execute();;
    }

    public void printFieldDescendingFormOfEducation() {
        this.printFieldDescendingFormOfEducationCommand.execute();
    }
}
