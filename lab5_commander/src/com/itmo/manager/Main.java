package com.itmo.manager;

import com.itmo.studyStream.StudyStream;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws IOException {
        ClientManager clientManager = new ClientManager(new StudyStream(args[0]));
        clientManager.run();
    }
}
