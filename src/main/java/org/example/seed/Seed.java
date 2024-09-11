package org.example.seed;

import org.example.model.Client;
import org.example.model.Level;
import org.example.model.Project;
import org.example.model.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Seed {

    private static final Logger logger = LoggerFactory.getLogger(Seed.class);

    public static final List<Worker> workers = new ArrayList<>();
    public static final List<Client> clients = new ArrayList<>();
    public static final List<Project> projects = new ArrayList<>();

    static {
        workers.add(new Worker("Alice", LocalDate.of(2001, 8, 20), "alice@example.com", Level.SENIOR, 100000));
        workers.add(new Worker("Bob", LocalDate.of(1995, 10, 11), "bob@example.com", Level.MIDDLE, 20000));
        workers.add(new Worker("Eve", LocalDate.of(2000, 1, 1), "eve@example.com", Level.SENIOR, 32000));
        workers.add(new Worker("Whiskers", LocalDate.of(2015, 6, 1), "whiskers@example.com", Level.TRAINEE, 10000));
        workers.add(new Worker("Purrito", LocalDate.of(2020, 3, 21), "purrito@example.com", Level.JUNIOR, 15000));
        workers.add(new Worker("Pawsters", LocalDate.of(2021, 1, 30), "pawsters@example.com", Level.TRAINEE, 950));
        workers.add(new Worker("Meowiarty", LocalDate.of(2021, 1, 30), "meowiarty@example.com", Level.SENIOR, 29000));
        workers.add(new Worker("Purrlock", LocalDate.of(2011, 7, 12), "purrlock@example.com", Level.MIDDLE, 22000));
        workers.add(new Worker("Clawster", LocalDate.of(2019, 4, 1), "clawster@example.com", Level.MIDDLE, 22000));
        workers.add(new Worker("Buttercup", LocalDate.of(2020, 9, 27), "buttercup@example.com", Level.MIDDLE, 32000));
        workers.add(new Worker("ET", LocalDate.of(1901, 1, 1), "et@example.com", Level.SENIOR, 100000));

        clients.add(new Client("Whiskers and Paw Co."));
        clients.add(new Client("Purrfect Solutions"));
        clients.add(new Client("Meowster Inc."));
        clients.add(new Client("Clawtastic Creations"));
        clients.add(new Client("Snack Caterprises"));

        projects.add(new Project("Purrfectly Crafted", 1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 10, 31)));
        projects.add(new Project("Whisker Wonderland", 2, LocalDate.of(2022, 5, 15), LocalDate.of(2024, 5, 15)));
        projects.add(new Project("Meowgical Moments", 3, LocalDate.of(2023, 2, 1), LocalDate.of(2023, 7, 31)));
        projects.add(new Project("Pawsitively Adorable Designs", 4, LocalDate.of(2015, 6, 1), LocalDate.of(2023, 10, 1)));
        projects.add(new Project("Cattitude Chronicles", 5, LocalDate.of(2021, 8, 1), LocalDate.of(2023, 2, 1)));
        projects.add(new Project("Feline Fine Art", 3, LocalDate.of(2023, 6, 1), LocalDate.of(2023, 8, 31)));
        projects.add(new Project("The Whisker Whisperer", 2, LocalDate.of(2022, 7, 1), LocalDate.of(2023, 6, 30)));
        projects.add(new Project("Paw Prints & Paintbrushes", 3, LocalDate.of(2018, 1, 1), LocalDate.of(2022, 2, 28)));
        projects.add(new Project("Meowsterpiece Gallery", 4, LocalDate.of(2023, 7, 1), LocalDate.of(2023, 8, 31)));
        projects.add(new Project("Fur-tastic Finds", 4, LocalDate.of(2023, 3, 1), LocalDate.of(2023, 9, 30)));
        projects.add(new Project("Cat-astrophic Cuteness", 1, LocalDate.of(2015, 7, 1), LocalDate.of(2023, 10, 31)));

        logger.info("Number of workers: {}", workers.size());
        logger.info("Number of clients: {}", clients.size());
        logger.info("Number of projects: {}", projects.size());
    }

//    public static List<Worker> getWorkers() {
//        List<Worker> workers = new ArrayList<>();
//        workers.add(new Worker("Alice", LocalDate.of(2001, 8, 20), "alice@example.com", Level.SENIOR, 100000));
//        workers.add(new Worker("Bob", LocalDate.of(1995, 10, 11), "bob@example.com", Level.MIDDLE, 20000));
//        workers.add(new Worker("Eve", LocalDate.of(2000, 1, 1), "eve@example.com", Level.SENIOR, 32000));
//        workers.add(new Worker("Whiskers", LocalDate.of(2015, 6, 1), "whiskers@example.com", Level.TRAINEE, 10000));
//        workers.add(new Worker("Purrito", LocalDate.of(2020, 3, 21), "purrito@example.com", Level.JUNIOR, 15000));
//        workers.add(new Worker("Pawsters", LocalDate.of(2021, 1, 30), "pawsters@example.com", Level.TRAINEE, 950));
//        workers.add(new Worker("Meowiarty", LocalDate.of(2021, 1, 30), "meowiarty@example.com", Level.SENIOR, 29000));
//        workers.add(new Worker("Purrlock", LocalDate.of(2011, 7, 12), "purrlock@example.com", Level.MIDDLE, 22000));
//        workers.add(new Worker("Clawster", LocalDate.of(2019, 4, 1), "clawster@example.com", Level.MIDDLE, 22000));
//        workers.add(new Worker("Buttercup", LocalDate.of(2020, 9, 27), "buttercup@example.com", Level.MIDDLE, 32000));
//        workers.add(new Worker("ET", LocalDate.of(1901, 1, 1), "et@example.com", Level.SENIOR, 100000));
//        return workers;
//    }
//
//    public static List<Client> getClients() {
//        List<Client> clients = new ArrayList<>();
//        clients.add(new Client("Whiskers and Paw Co."));
//        clients.add(new Client("Purrfect Solutions"));
//        clients.add(new Client("Meowster Inc."));
//        clients.add(new Client("Clawtastic Creations"));
//        clients.add(new Client("Snack Caterprises"));
//        return new ArrayList<>();
//    }
//
//    public static List<Project> getProjects() {
//        List<Project> projects = new ArrayList<>();
//        projects.add(new Project("Purrfectly Crafted", 1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 10, 31)));
//        projects.add(new Project("Whisker Wonderland", 2, LocalDate.of(2022, 5, 15), LocalDate.of(2024, 5, 15)));
//        projects.add(new Project("Meowgical Moments", 3, LocalDate.of(2023, 2, 1), LocalDate.of(2023, 7, 31)));
//        projects.add(new Project("Pawsitively Adorable Designs", 4, LocalDate.of(2015, 6, 1), LocalDate.of(2023, 10, 1)));
//        projects.add(new Project("Cattitude Chronicles", 5, LocalDate.of(2021, 8, 1), LocalDate.of(2023, 2, 1)));
//        projects.add(new Project("Feline Fine Art", 3, LocalDate.of(2023, 6, 1), LocalDate.of(2023, 8, 31)));
//        projects.add(new Project("The Whisker Whisperer", 2, LocalDate.of(2022, 7, 1), LocalDate.of(2023, 6, 30)));
//        projects.add(new Project("Paw Prints & Paintbrushes", 3, LocalDate.of(2018, 1, 1), LocalDate.of(2022, 2, 28)));
//        projects.add(new Project("Meowsterpiece Gallery", 4, LocalDate.of(2023, 7, 1), LocalDate.of(2023, 8, 31)));
//        projects.add(new Project("Fur-tastic Finds", 4, LocalDate.of(2023, 3, 1), LocalDate.of(2023, 9, 30)));
//        projects.add(new Project("Cat-astrophic Cuteness", 1, LocalDate.of(2015, 7, 1), LocalDate.of(2023, 10, 31)));
//        return new ArrayList<>();
//    }
//
//    static {
//        List<Project> projects = new ArrayList<>();
//        projects.add(new Project("Purrfectly Crafted", 1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 10, 31)));
//        projects.add(new Project("Whisker Wonderland", 2, LocalDate.of(2022, 5, 15), LocalDate.of(2024, 5, 15)));
//        projects.add(new Project("Meowgical Moments", 3, LocalDate.of(2023, 2, 1), LocalDate.of(2023, 7, 31)));
//        projects.add(new Project("Pawsitively Adorable Designs", 4, LocalDate.of(2015, 6, 1), LocalDate.of(2023, 10, 1)));
//        projects.add(new Project("Cattitude Chronicles", 5, LocalDate.of(2021, 8, 1), LocalDate.of(2023, 2, 1)));
//        projects.add(new Project("Feline Fine Art", 3, LocalDate.of(2023, 6, 1), LocalDate.of(2023, 8, 31)));
//        projects.add(new Project("The Whisker Whisperer", 2, LocalDate.of(2022, 7, 1), LocalDate.of(2023, 6, 30)));
//        projects.add(new Project("Paw Prints & Paintbrushes", 3, LocalDate.of(2018, 1, 1), LocalDate.of(2022, 2, 28)));
//        projects.add(new Project("Meowsterpiece Gallery", 4, LocalDate.of(2023, 7, 1), LocalDate.of(2023, 8, 31)));
//        projects.add(new Project("Fur-tastic Finds", 4, LocalDate.of(2023, 3, 1), LocalDate.of(2023, 9, 30)));
//        projects.add(new Project("Cat-astrophic Cuteness", 1, LocalDate.of(2015, 7, 1), LocalDate.of(2023, 10, 31)));
//
//    }
}

