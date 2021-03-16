package com.itmo.studyStream;

import com.itmo.manager.streams.FlexibleReader;
import com.itmo.studyStream.studyGroup.StudyGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

public class StudyStream {
    private static Collection<StudyGroup> studyGroups = new HashSet<StudyGroup>();
    private static LocalDateTime initializationDate;
    private Collection<Long> unique = new HashSet<Long>();
    private Collection<Long> duplicate = new HashSet<Long>();
    private File source;

    public static class IdControl {
        private static HashSet<Integer> idControl = new HashSet<Integer>();

        public static Integer createNewId() {
            Integer id = 1;
            while (checkIdControl(id)) {
                id++;
            }
            return id;
        }


        public static void addToIdControl(Integer id) {
            idControl.add(id);
        }

        public static boolean checkIdControl(Integer id) {
            return idControl.contains(id);
        }

        public static void removeFromIdControl(Integer id) {
            idControl.remove(id);
        }

        public static void clearIdControl() {
            idControl.clear();
        }

        public static void show() {
            Iterator<Integer> i = idControl.iterator();
            while (i.hasNext()) {
                System.out.println("    " + i.next());
            }
        }

    }

    public StudyStream() {

    }

    public StudyStream(String jsonData) {
        this.initializationDate = LocalDateTime.now();
        try {
            if (jsonData == null) throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("Путь до json файла нужно подать в аргументе командной строки.");
            System.exit(1);
        }
        File file = new File(jsonData);
        try {
            if (!file.canRead() || !file.canWrite()) throw new SecurityException();
        } catch (SecurityException ex) {
            System.out.println("Файл защищён от чтения или записи. Для работы программы нужны оба разрешения.");
            System.exit(1);
        }
        this.source = file;
        try {
            FlexibleReader reader = new FlexibleReader(new FileInputStream(file));
            studyGroups = reader.getJsonFileToCollection();

        } catch (FileNotFoundException ex) {
            System.out.println("Файл по указанному пути отсутствует.");
            System.exit(1);
        }

    }

    public void add(StudyGroup studyGroup) {
        studyGroups.add(studyGroup);
    }

    public void remove(StudyGroup studyGroup) {
        studyGroups.remove(studyGroup);
    }

    public void clear() {
        studyGroups.clear();
        IdControl.clearIdControl();
    }

    public boolean contains(StudyGroup studyGroup) {
        return studyGroups.contains(studyGroup);
    }

    public Integer size() {
        return studyGroups.size();
    }

    public Collection<StudyGroup> getCollection() {
        return studyGroups;
    }

    public void collectionInfo() {
        System.out.println("Тип коллекции: HashSet\nДата инициализации: " + initializationDate + "\nКоличество элементов: " + size() + "\nЗарезервированные Id: ");
        IdControl.show();
    }

    public TreeSet<StudyGroup> sortedCollection() {
        TreeSet<StudyGroup> sortedCollection = new TreeSet();
        sortedCollection.addAll(studyGroups);
        return sortedCollection;
    }

    public Collection<Long> getUnique() {
        Iterator<StudyGroup> i = studyGroups.iterator();
        StudyGroup studyGroup;
        long expelledStudents;
        while (i.hasNext()) {
            studyGroup = i.next();
            expelledStudents = studyGroup.getExpelledStudents();
            if (unique.contains(expelledStudents)) {
                unique.remove(expelledStudents);
                duplicate.add(expelledStudents);
            } else {
                unique.add(expelledStudents);
            }
        }
        return unique;
    }

    public File getSource() {
        return source;
    }
}
