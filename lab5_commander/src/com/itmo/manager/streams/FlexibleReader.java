package com.itmo.manager.streams;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.itmo.studyStream.StudyStream;
import com.itmo.studyStream.studyGroup.*;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;


/**
 * Реализует считывание и парсинг данных.
 */
public class FlexibleReader {
    private InputStream inputStream;
    private Gson serializer = new Gson();
    private Collection<StudyGroup> studyStream;
    private Scanner scanner;
    private String inputLine;
    private boolean messageToggle;

    public FlexibleReader() {
        this.inputStream = System.in;
        this.scanner = new Scanner(inputStream);
        this.messageToggle = true;
    }

    public FlexibleReader(InputStream inputStream) {
        this.inputStream = inputStream;
        this.scanner = new Scanner(inputStream);
        this.studyStream = new HashSet<>();
        this.messageToggle = false;
    }

    /**
     * Проверяет наличие следующего значения из потока ввода.
     * @return Результат проверки.
     */
    public boolean hasNext() {
        return scanner.hasNext();
    }

    /**
     * Возвращет считываемое число из потока ввода.
     * @return Возвращаемое число.
     */
    public Integer ReadInteger() {
        //System.out.println("Вводим число " + messageToggle);


        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            if (messageToggle)
                System.out.println("Вы должны были ввести число.");
            return null;
        }
    }

    /**
     * Возвращет считываемую строку из потока ввода.
     * @return Возвращаемая строка.
     */
    public String ReadString() {
        if (messageToggle)
            scanner = new Scanner(inputStream);
        //System.out.println("вводим команду из " + inputStream);
        return scanner.nextLine().trim();
    }

    public void saveToFile() {

    }


    /**
     * Десериализует коллекцию из файла json и возвращет её.
     * @return Возвращаемая коллекция.
     */
    public Collection<StudyGroup> getJsonFileToCollection() {
        this.inputLine = scanner.nextLine();
        try {
            Collection<StudyGroup> loadStudyStream = serializer.fromJson(inputLine, new TypeToken<HashSet<StudyGroup>>() {}.getType());
            Iterator<StudyGroup> i = loadStudyStream.iterator();
            StudyGroup checker;
            try {
                while (i.hasNext()) {
                    checker = i.next();

                    if (StudyStream.IdControl.checkIdControl(checker.getId())) {
                        throw new NumberFormatException();
                    } else {
                        try {
                            if (checker.getId() <= 0)
                                throw new NumberFormatException();
                        } catch (NumberFormatException ex) {
                            System.out.println("В исходном файле найден id меньший 1, исправьте и попробуйте снова.");
                        }
                        StudyStream.IdControl.addToIdControl(checker.getId());
                        //System.out.println("(((" + checker.getId());
                    }
                }
                studyStream = loadStudyStream;
            } catch (NumberFormatException ex) {
                System.out.println("В исходном файле найдены повторяющиеся id, исправьте и попробуйте снова.");
                System.exit(1);
            }
            return studyStream;
        } catch (JsonSyntaxException ex) {
            System.out.println("Нарушен синтаксис Json.");
            return studyStream;
        }
    }

    /**
     * Считывает данные студента из потока ввода и возвращает самого студента.
     * @return Возвращаемый студент.
     */
    public StudyGroup readElement() {
        int id = 0; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
        String name; //Поле не может быть null, Строка не может быть пустой
        Coordinates coordinates; //Поле не может быть null
        LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
        long studentsCount; //Значение поля должно быть больше 0
        long expelledStudents; //Значение поля должно быть больше 0
        Integer transferredStudents; //Значение поля должно быть больше 0, Поле не может быть null
        FormOfEducation formOfEducation; //Поле может быть null
        Person groupAdmin; //Поле может быть null

        if (messageToggle) {
            System.out.println("Введите имя группы:\n*Значениие не может быть пустым*");
            scanner = new Scanner(System.in);
        }

        name = scanner.nextLine().trim();
        if (name.isEmpty())
            throw new NumberFormatException();


        double x = 0;
        if (messageToggle)
            System.out.println("Введите координату по X:");
        x = scanner.nextDouble();

        double y = 0;
        if (messageToggle)
            System.out.println("Введите координату по Y:");
        y = scanner.nextDouble();

        coordinates = new Coordinates(x, y);

        creationDate = LocalDateTime.now();

        if (messageToggle)
            System.out.println("Введите колличество студентов:\n*Значение должно быть больше 0*");
        studentsCount = scanner.nextLong();
        if (studentsCount <= 0) throw new NumberFormatException();

        if (messageToggle)
            System.out.println("Введите колличество исключённых студентов:\n*Значение должно быть больше 0*");
        expelledStudents = scanner.nextLong();
        if (expelledStudents <= 0) throw new NumberFormatException();

        if (messageToggle)
            System.out.println("Введите колличество переведённых студентов:\n*Значение должно быть больше 0 и не может быть пустым*");
        transferredStudents = scanner.nextInt();
        if (transferredStudents <= 0) throw new NumberFormatException();

        if (messageToggle)
            System.out.println("Выбор формата обучения:\nНажмите 1 для выбора дистанционного формата...\nНажмите 2 для выбора очного формата...\nНажмите 3 для выбора вечернего формата...");
        int form = scanner.nextInt();
        switch (form) {
            case (1) :
                formOfEducation = FormOfEducation.DISTANCE_EDUCATION;
                break;
            case (2) :
                formOfEducation = FormOfEducation.FULL_TIME_EDUCATION;
                break;
            case (3) :
                formOfEducation = FormOfEducation.EVENING_CLASSES;
                break;
            default :
                throw new IllegalStateException("Unexpected value: " + form);
        }

        if (messageToggle)
            System.out.println("Введите данные старосты группы...\nИмя старосты:\n*Старосте нельзя остаться без имени*");
        String adminName;
        if (messageToggle)
            scanner = new Scanner(System.in);
        adminName = scanner.nextLine();
        if (adminName.isEmpty()) throw new NumberFormatException();

        LocalDateTime adminBirthday;
        if (messageToggle)
            System.out.println("Если вы знаете когда родился староста введите 1, если нет введите 0...");
        int know = scanner.nextInt();
        if (know != 0 && know != 1) {
            throw new NumberFormatException();
        } else {
            if (know == 1) {
                if (messageToggle)
                    System.out.println("Год:");
                int year = scanner.nextInt();
                if (messageToggle)
                    System.out.println("Месяц:");
                int month = scanner.nextInt();
                if (messageToggle)
                    System.out.println("День:");
                int day = scanner.nextInt();
                if (messageToggle)
                    System.out.println("Час:");
                int hour = scanner.nextInt();
                if (messageToggle)
                    System.out.println("Минута:");
                int minute = scanner.nextInt();
                    adminBirthday = LocalDateTime.of(year, month, day, hour, minute);
            } else {
                adminBirthday = null;
            }
        }

        Double height;
        if (messageToggle)
            System.out.println("Если вы знаете рост старосты введите 1, если нет введите 0...");
        know = scanner.nextInt();
        if (know != 0 && know != 1) {
            throw new NumberFormatException();
        } else {
            if (know == 1) {
                if (messageToggle)
                    System.out.println("Введите рост старосты:");
                height = scanner.nextDouble();
            } else {
                height = null;
            }
        }

        Country nationality = null;
        if (messageToggle)
            System.out.println("Если вы знаете родную страну старосты введите 1, если нет введите 0...");
        know = scanner.nextInt();
        if (know != 0 && know != 1) {
            throw new NumberFormatException();
        } else {
            if (know == 1) {
                if (messageToggle) {
                    System.out.println("Выбор родной страны старосты:");
                    System.out.println("Нажмите 1 если староста родом из России...\nНажмите 2 если староста родом из Ватикана...\nНажмите 3 если староста родом из Италии...\nНажмите 4 если староста родом из Японии...");
                }
                form = scanner.nextInt();
                switch (form) {
                    case (1) :
                        nationality = Country.RUSSIA;
                        break;
                    case (2) :
                        nationality = Country.VATICAN;
                        break;
                    case (3) :
                        nationality = Country.ITALY;
                        break;
                    case (4) :
                        nationality = Country.JAPAN;
                        break;
                    default :
                        throw new IllegalStateException("Unexpected value: " + form);
                }
            }
        }

        Location location;
        int locationX = 0;
        long locationY = 0;
        long locationZ = 0;
        String locationName = null;
        if (messageToggle)
            System.out.println("Если вы знаете где сейчас староста введите 1, если нет введите 0...");
        know = scanner.nextInt();
        if (know != 0 && know != 1) {
            throw new NumberFormatException();
        } else {
            if (know == 1) {
                if (messageToggle) {
                    System.out.println("Опишите место дислокации старосты...");
                    System.out.println("Введите координату по X:");
                }
                locationX = scanner.nextInt();
                if (messageToggle)
                    System.out.println("Введите координату по Y:");
                locationY = scanner.nextLong();
                if (messageToggle)
                    System.out.println("Введите координату по Z:");
                locationZ = scanner.nextLong();
                if (messageToggle)
                    System.out.println("Введите название локации где сейчас находится староста:\n*Мы должны знать хотя бы название*");
                if (messageToggle)
                    scanner = new Scanner(System.in);
                locationName = scanner.nextLine().trim();
                if (locationName.isEmpty()) throw new NumberFormatException();

            }
        }

        location = new Location(locationX, locationY, locationZ, locationName);

        groupAdmin = new Person(adminName, adminBirthday, height, nationality, location);

        id = StudyStream.IdControl.createNewId();
        StudyStream.IdControl.addToIdControl(id);

        StudyGroup studyGroup = new StudyGroup(id, name, coordinates, studentsCount,expelledStudents, transferredStudents, formOfEducation, groupAdmin);
        return studyGroup;
    }

}
