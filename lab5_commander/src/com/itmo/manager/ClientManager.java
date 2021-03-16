package com.itmo.manager;

import com.itmo.manager.commands.*;
import com.itmo.manager.streams.FlexibleReader;
import com.itmo.studyStream.StudyStream;
import com.itmo.studyStream.studyGroup.StudyGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;

public class ClientManager {
    private static StudyStream studyStream;
    private static HashSet<File> infinityControl;
    private Receiver receiver;
    private ClientManager branch;
    private boolean scriptState;
    private File script;
    private static FlexibleReader reader = new FlexibleReader();
    private FlexibleReader recReader;
    private boolean whileParam;

    //      инициализация экземпляров команд
    private Command exit;
    private Command add;
    private Command removeById;
    private Command clear;
    private Command save;
    private Command help;
    private Command info;
    private Command update;
    private Command addIfMax;
    private Command removeGreater;
    private Command removeLower;
    private Command show;
    private Command printAscending;
    private Command printUniqueExpelledStudents;
    private Command printFieldDescendingFormOfEducation;

    private Invoker root;
    //  заполнение коллекции значениями из файла

    public ClientManager(StudyStream studyStream) {
        ClientManager.studyStream = studyStream;
        infinityControl = new HashSet<File>();
        this.receiver = new Receiver(false, getSource());
        this.scriptState = false;
        this.exit = new ExitCommand(receiver);
        this.add = new AddCommand(receiver);
        this.removeById = new RemoveByIdCommand(receiver);
        this.clear = new ClearCommand(receiver);
        this.save = new SaveCommand(receiver);
        this.help = new HelpCommand(receiver);
        this.info = new InfoCommand(receiver);
        this.update = new UpdateCommand(receiver);
        this.addIfMax = new AddIfMaxCommand(receiver);
        this.removeGreater = new RemoveGreaterCommand(receiver);
        this.removeLower = new RemoveLowerCommand(receiver);
        this.show = new ShowCommand(receiver);
        this.printAscending = new PrintAscendingCommand(receiver);
        this.printUniqueExpelledStudents = new PrintUniqueExpelledStudentsCommand(receiver);
        this.printFieldDescendingFormOfEducation = new PrintFieldDescendingFormOfEducationCommand(receiver);

        this.root = new Invoker(exit, add, removeById, clear, save, help, info, update, addIfMax, removeGreater,
                removeLower, show, printAscending, printUniqueExpelledStudents, printFieldDescendingFormOfEducation);
    }

    public ClientManager(File script, FlexibleReader recReader) throws FileNotFoundException {
        infinityControl.add(script);
        this.scriptState = true;
        this.receiver = new Receiver(true, getSource());
        this.script = script;

        this.recReader = recReader;
        this.reader = new FlexibleReader(new FileInputStream(script));

        this.exit = new ExitCommand(receiver);
        this.add = new AddCommand(receiver);
        this.removeById = new RemoveByIdCommand(receiver);
        this.clear = new ClearCommand(receiver);
        this.save = new SaveCommand(receiver);
        this.help = new HelpCommand(receiver);
        this.info = new InfoCommand(receiver);
        this.update = new UpdateCommand(receiver);
        this.addIfMax = new AddIfMaxCommand(receiver);
        this.removeGreater = new RemoveGreaterCommand(receiver);
        this.removeLower = new RemoveLowerCommand(receiver);
        this.show = new ShowCommand(receiver);
        this.printAscending = new PrintAscendingCommand(receiver);
        this.printUniqueExpelledStudents = new PrintUniqueExpelledStudentsCommand(receiver);
        this.printFieldDescendingFormOfEducation = new PrintFieldDescendingFormOfEducationCommand(receiver);

        this.root = new Invoker(exit, add, removeById, clear, save, help, info, update, addIfMax, removeGreater,
                removeLower, show, printAscending, printUniqueExpelledStudents, printFieldDescendingFormOfEducation);
    }

    private String nextCommand;

    /**
     * Запуск считывания комманд и их вызов.
     */
    public void run() {
        whileParam = true;


        if (!scriptState) {
            reader = new FlexibleReader();
        }

        while (whileParam) {

            if (scriptState) {
                if (reader.hasNext()) {
                    nextCommand = reader.ReadString();
                } else {
                    whileParam = false;
                    break;
                }
            } else {
                reader = new FlexibleReader();
                nextCommand = reader.ReadString();
            }

            String arr[] = nextCommand.split(" ", 2);

            try {
                switch (arr[0].toLowerCase()) {
                    case ("exit") :
                        root.exit();
                        break;
                    case ("add") :
                        root.add();
                        break;
                    case ("remove_by_id") :
                        root.removeById(arr[1]);
                        break;
                    case ("clear") :
                        root.clear();
                        break;
                    case ("save") :
                        root.save();
                        break;
                    case ("help") :
                        root.help();
                        break;
                    case ("info") :
                        root.info();
                        break;
                    case ("update") :
                        root.update(arr[1]);
                        break;
                    case ("add_if_max") :
                        root.addIfMax();
                        break;
                    case ("remove_greater") :
                        root.removeGreater();
                        break;
                    case ("remove_lower") :
                        root.removeLower();
                        break;
                    case ("show") :
                        root.show();
                        break;
                    case ("print_ascending") :
                        root.printAscending();
                        break;
                    case ("print_unique_expelled_students") :
                        root.printUniqueExpelledStudents();
                        break;
                    case ("print_field_descending_form_of_education") :
                        root.printFieldDescendingFormOfEducation();
                        break;
                    case ("execute_script") :
                        try {

                            try {
                                script = new File(arr[1]);
                            } catch (ArrayIndexOutOfBoundsException ex) {
                                throw new FileNotFoundException();
                            }

                            if (infinityControl.contains(script)) {
                                System.out.println("Исполнение скрипт файла " + script.toString() + "вызовет бесконечный цикл. Скрипта не будет.");
                            } else {
                                recReader = reader;
                                this.branch = new ClientManager(script, reader);
                                infinityControl.add(script);
                                branch.run();
                                reader = recReader;
                                infinityControl.remove(script);
                            }

                        } catch (FileNotFoundException ex) {
                            System.out.println("Неверный путь до скрипта.");
                        }
                        break;
                    default :
                        throw new IllegalStateException("Unexpected value: " + nextCommand);
                }
            } catch (IllegalStateException ex) {
                if (scriptState) {
                    System.out.println("Ваш скрипт потерпел фиаско, рекомендую попрактиковаться в интерактивном режими. Скрипт по пути " + script +  " остановлен.");
                    whileParam = false;
                } else {
                    System.out.println("Введена неизвестная команда. Попробуйте снова. Узнать больше можно с помощью команд help и info.");
                }
            }
        }

    }

    /**
     * Обновляет источник ввода.
     */
    public static void refresh() {
        ClientManager.reader = new FlexibleReader();
    }

    /**
     * Возвращает считанную студенческую гурппу.
     * @return Возвращаемая студеньческая группа.
     */
    public static StudyGroup readElement() {
        return ClientManager.reader.readElement();
    }

    /**
     * Возвращем исходный json файл.
     * @return Возвращаемый файл.
     */
    public static File getSource() {
        return ClientManager.studyStream.getSource();
    }


}
