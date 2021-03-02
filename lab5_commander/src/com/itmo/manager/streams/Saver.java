package com.itmo.manager.streams;

import com.google.gson.Gson;
import com.itmo.studyStream.studyGroup.StudyGroup;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

public class Saver {
    private Gson serializer = new Gson();
    private File source;

    public Saver(File source) {
        this.source = source;
    }

    /**
     * Сохраняет коллекцию в исходный json файл.
     * @param database Коллекция студеньчиских групп.
     */
    public void saveToFile(Collection<StudyGroup> database) {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(source))) {
            byte[] buffer = serializer.toJson(database).getBytes();
            out.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            System.out.println("Вы указали нерверный путь до файла сохранения. Попробуйте снова, предварительно введя команду save ещё раз.");
        }
    }

}
