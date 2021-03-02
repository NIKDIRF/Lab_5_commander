package com.itmo.manager;

import com.itmo.manager.streams.FlexibleReader;
import com.itmo.manager.streams.Saver;
import com.itmo.studyStream.StudyStream;
import com.itmo.studyStream.studyGroup.EnumComparator;
import com.itmo.studyStream.studyGroup.FormOfEducation;
import com.itmo.studyStream.studyGroup.StudyGroup;

import java.io.*;
import java.util.*;

public class Receiver {
    private InputStream inputStream;
    private boolean scriptState;
    private boolean success;
    private File source;

    public Receiver(boolean scriptState, File source) {
        this.scriptState = scriptState;
        this.source = source;
    }

    /**
     * Добавляет в коллекцию считанный элемент.
     */
    public void add() {
        try {
            System.out.println("!!!!");
            if (!scriptState) {
                ClientManager.refresh();
            }
            StudyGroup studyGroup = ClientManager.readElement();
            System.out.println("элемент считан");
            ClientManager.addToStudyStream(studyGroup);
            this.success = true;
            if (!scriptState) {
                ClientManager.refresh();
            }
        } catch (Exception ex) {
            System.out.println("Вы допустили ошибку при вводе параметров группы. Попробуйте снова, предварительно введя команду add ещё раз.");
            this.success = false;
        }
    }

    /**
     * Удаляет все элементы коллекции.
     */
    public void clear() {
        ClientManager.clearStudyStream();
        StudyStream.IdControl.clearIdControl();
        this.success = true;
    }

    /**
     * Удаляет элемент коллекции согласно считанному id.
     */
    public void removeById() {
        try {
            Integer id = ClientManager.readInteger();
            try {
                if (!StudyStream.IdControl.checkIdControl(id)) throw new NumberFormatException();
                Iterator<StudyGroup> i = ClientManager.getCollection().iterator();
                StudyGroup studyGroup;
                while (i.hasNext()) {
                    studyGroup = i.next();
                    if (id == studyGroup.getId()) {
                        StudyStream.IdControl.removeFromIdControl(studyGroup.getId());
                        System.out.println(studyGroup.getId());
                        ClientManager.removeFromStudyStream(studyGroup);
                        this.success = true;
                        if (!scriptState) {
                            ClientManager.refresh();
                        }
                        break;
                    }
                }
            } catch (NumberFormatException ex) {
                System.out.println("Группы с введённым Id не существует. Попробуйте снова, предварительно введя команду remove_by_id ещё раз.");
                this.success = false;
            }
        } catch (NumberFormatException ex) {
            System.out.println("Ошибка в формате ввода Id. Попробуйте снова, предварительно введя команду remove_by_id ещё раз.");
            this.success = false;
        }
    }

    /**
     * Завершает работу программы.
     */
    public void exit() {
        System.out.println("Программа завершена.");
        System.exit(1);
    }

    /**
     * Сохраняет коллекцию в json файл.
     */
    public void save() {
        Saver saver = new Saver(source);
        saver.saveToFile(ClientManager.getCollection());
    }

    /**
     * Выводит на экран список доступных пользователю команд c их описанием.
     */
    public void help() {
        System.out.println("help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "print_ascending : вывести элементы коллекции в порядке возрастания\n" +
                "print_unique_expelled_students : вывести уникальные значения поля expelledStudents всех элементов в коллекции\n" +
                "print_field_descending_form_of_education : вывести значения поля formOfEducation всех элементов в порядке убывания");
    }

    /**
     * Выводит на экран информацию о коллекции.
     */
    public void info() {
        ClientManager.collectionInfo();
        if (!scriptState) {
            ClientManager.refresh();
        }
    }

    /**
     * Обновляет значение элемента коллекции по считанному id.
     */
    public void update() {
        removeById();
        if (!scriptState) {
            ClientManager.refresh();
        }
        if (success) {
            add();
        }
    }

    /**
     * Добавляет в коллекцию считанный элемент, если считанный элемент больше максимального элемента коллекции.
     */
    public void addIfMax() {
        try {
            System.out.println("!!!!");
            if (!scriptState) {
                ClientManager.refresh();
            }
            StudyGroup studyGroup = ClientManager.readElement();
            if (ClientManager.getSortedCollection().last().compareTo(studyGroup) == -1) {
                ClientManager.addToStudyStream(studyGroup);
            }
            System.out.println("!" + ClientManager.getCollection().size());
            this.success = true;
            if (!scriptState) {
                ClientManager.refresh();
            }
        } catch (Exception ex) {
            System.out.println("Вы допустили ошибку при вводе параметров группы. Попробуйте снова, предварительно введя команду add ещё раз.");
            this.success = false;
        }
    }

    /**
     * Удаляет все элементы коллекции большие считанного элемента.
     */
    public void removeGreater() {
        try {
            if (!scriptState) {
                ClientManager.refresh();
            }
            StudyGroup studyGroup = ClientManager.readElement();
            Collection<StudyGroup> studyGroups = ClientManager.getSortedCollection().tailSet(studyGroup);
            Iterator<StudyGroup> i = studyGroups.iterator();
            StudyGroup group;
            while (i.hasNext()) {
                group = i.next();
                StudyStream.IdControl.removeFromIdControl(group.getId());
                ClientManager.removeFromStudyStream(group);
            }
            this.success = true;
            if (!scriptState) {
                ClientManager.refresh();
            }
        } catch (Exception ex) {
            System.out.println("Вы допустили ошибку при вводе параметров группы. Попробуйте снова, предварительно введя команду add ещё раз.");
            this.success = false;
        }
    }

    /**
     * Удаляет все элементы коллекции меньшие считанного элемента.
     */
    public void removeLower() {
        try {
            if (!scriptState) {
                ClientManager.refresh();
            }
            StudyGroup studyGroup = ClientManager.readElement();
            Collection<StudyGroup> studyGroups = ClientManager.getSortedCollection().headSet(studyGroup);
            Iterator<StudyGroup> i = studyGroups.iterator();
            StudyGroup group;
            while (i.hasNext()) {
                group = i.next();
                StudyStream.IdControl.removeFromIdControl(group.getId());
                System.out.println(group.getStudentsCount());
                ClientManager.removeFromStudyStream(group);
            }
            this.success = true;
            if (!scriptState) {
                ClientManager.refresh();
            }
        } catch (Exception ex) {
            System.out.println("Вы допустили ошибку при вводе параметров группы. Попробуйте снова, предварительно введя команду add ещё раз.");
            this.success = false;
        }
    }

    /**
     * Выводит уникальные значения поля expelledStudents всех элементов в коллекции.
     */
    public void printUniqueExpelledStudents() {
        Collection<Long> unique = ClientManager.getUnique();
        Iterator<Long> i = unique.iterator();
        while (i.hasNext()) {
            System.out.print(i.next() + " ");
        }
        System.out.println();
    }

    /**
     * Выводит значения поля formOfEducation всех элементов в порядке убывания.
     */
    public void printFieldDescendingFormOfEducation() {
        List<FormOfEducation> formsOfEducations = new ArrayList<>();
        Collection<StudyGroup> studyGroups = ClientManager.getCollection();
        Iterator<StudyGroup> i = studyGroups.iterator();
        while (i.hasNext()) {
            formsOfEducations.add(i.next().getFormOfEducation());
        }
        Comparator enumComparator = new EnumComparator();
        Collections.sort(formsOfEducations, enumComparator);
        for(FormOfEducation form : formsOfEducations) {
            System.out.print(form + " ");
        }
        System.out.println();
    }

    /**
     * Выводит в стандартный поток вывода все элементы коллекции в строковом представлении.
     */
    public void show() {
        Collection<StudyGroup> studyGroups = ClientManager.getCollection();
        Iterator<StudyGroup> i = studyGroups.iterator();
        StudyGroup studyGroup;
        while (i.hasNext()) {
            studyGroup = i.next();
            System.out.print("StudyGroup:" +
                                "\nid: " + studyGroup.getId() +
                                "\n    name: " + studyGroup.getName() +
                                "\n    coordinates: " +
                                "\n        x: " + studyGroup.getCoordinates().getX() +
                                "\n        y: " + studyGroup.getCoordinates().getY() +
                                "\n    creationDate: " + studyGroup.getCreationDate() +
                                "\n    studentsCount: " + studyGroup.getStudentsCount() +
                                "\n    expelledStudents: " + studyGroup.getExpelledStudents() +
                                "\n    transferredStudents: " + studyGroup.getTransferredStudents() +
                                "\n    formOfEducation: ");
            if (studyGroup.getFormOfEducation() != null) {
                System.out.print(studyGroup.getFormOfEducation());
            } else {
                System.out.print("null");
            }
            if (studyGroup.getGroupAdmin() == null) {
                System.out.print("\n    groupAdmin: null");
            } else {
                System.out.print("\n    groupAdmin: " +
                                 "\n        name: " + studyGroup.getGroupAdmin().getName());
                if (studyGroup.getGroupAdmin().getBirthday() == null) {
                    System.out.print("\n        birthday: null");
                } else {
                    System.out.print("\n        birthday: " + studyGroup.getGroupAdmin().getBirthday());
                }
                if (studyGroup.getGroupAdmin().getHeight() == null) {
                    System.out.print("\n        height: null");
                } else {
                    System.out.print("\n        height: " + studyGroup.getGroupAdmin().getHeight());
                }
                if (studyGroup.getGroupAdmin().getNationality() == null) {
                    System.out.print("\n        nationality: null");
                } else {
                    System.out.print("\n        nationality: " + studyGroup.getGroupAdmin().getNationality());
                }
                if (studyGroup.getGroupAdmin().getLocation() == null) {
                    System.out.print("\n        location: null");
                } else {
                    System.out.print("\n        location: " +
                            "\n            x: " + studyGroup.getGroupAdmin().getLocation().getX() +
                            "\n            y: " + studyGroup.getGroupAdmin().getLocation().getY() +
                            "\n            z: " + studyGroup.getGroupAdmin().getLocation().getZ() +
                            "\n            name: " + studyGroup.getGroupAdmin().getLocation().getName() +
                            "\n");
                }
            }
        }
    }

    /**
     * Выводит элементы коллекции в порядке возрастания.
     */
    public void printAscending() {
        Collection<StudyGroup> studyGroups = ClientManager.getSortedCollection();
        Iterator<StudyGroup> i = studyGroups.iterator();
        StudyGroup studyGroup;
        while (i.hasNext()) {
            studyGroup = i.next();
            System.out.print("StudyGroup:" +
                    "\nid: " + studyGroup.getId() +
                    "\n    name: " + studyGroup.getName() +
                    "\n    coordinates: " +
                    "\n        x: " + studyGroup.getCoordinates().getX() +
                    "\n        y: " + studyGroup.getCoordinates().getY() +
                    "\n    creationDate: " + studyGroup.getCreationDate() +
                    "\n    studentsCount: " + studyGroup.getStudentsCount() +
                    "\n    expelledStudents: " + studyGroup.getExpelledStudents() +
                    "\n    transferredStudents: " + studyGroup.getTransferredStudents() +
                    "\n    formOfEducation: ");
            if (studyGroup.getFormOfEducation() != null) {
                System.out.print(studyGroup.getFormOfEducation());
            } else {
                System.out.print("null");
            }
            if (studyGroup.getGroupAdmin() == null) {
                System.out.print("\n    groupAdmin: null");
            } else {
                System.out.print("\n    groupAdmin: " +
                        "\n        name: " + studyGroup.getGroupAdmin().getName());
                if (studyGroup.getGroupAdmin().getBirthday() == null) {
                    System.out.print("\n        birthday: null");
                } else {
                    System.out.print("\n        birthday: " + studyGroup.getGroupAdmin().getBirthday());
                }
                if (studyGroup.getGroupAdmin().getHeight() == null) {
                    System.out.print("\n        height: null");
                } else {
                    System.out.print("\n        height: " + studyGroup.getGroupAdmin().getHeight());
                }
                if (studyGroup.getGroupAdmin().getNationality() == null) {
                    System.out.print("\n        nationality: null");
                } else {
                    System.out.print("\n        nationality: " + studyGroup.getGroupAdmin().getNationality());
                }
                if (studyGroup.getGroupAdmin().getLocation() == null) {
                    System.out.print("\n        location: null");
                } else {
                    System.out.print("\n        location: " +
                            "\n            x: " + studyGroup.getGroupAdmin().getLocation().getX() +
                            "\n            y: " + studyGroup.getGroupAdmin().getLocation().getY() +
                            "\n            z: " + studyGroup.getGroupAdmin().getLocation().getZ() +
                            "\n            name: " + studyGroup.getGroupAdmin().getLocation().getName() +
                            "\n");
                }
            }
        }
    }
}
