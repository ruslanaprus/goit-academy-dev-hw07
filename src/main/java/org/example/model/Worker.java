package org.example.model;

import java.time.LocalDate;

enum Level{
    TRAINEE,
    JUNIOR,
    MIDDLE,
    SENIOR
}

public class Worker {
    private String name;
    private LocalDate dateOfBirth;
    private String email;
    private String Level;
    private int salary;
}
