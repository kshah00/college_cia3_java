package app;

import app.dao.DatabaseConnection;
import app.model.Student;
import app.service.StudentService;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.initSchema();
        StudentService service = new StudentService();
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("1 Add student");
            System.out.println("2 View all");
            System.out.println("3 Update student");
            System.out.println("4 Delete student");
            System.out.println("5 Search");
            System.out.println("6 Exit");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> addFlow(sc, service);
                    case "2" -> viewAll(service);
                    case "3" -> updateFlow(sc, service);
                    case "4" -> deleteFlow(sc, service);
                    case "5" -> searchFlow(sc, service);
                    case "6" -> running = false;
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception ex) {
                System.out.println("Something went wrong. Try again.");
            }
        }
        sc.close();
        System.out.println("Bye.");
    }

    private static void addFlow(Scanner sc, StudentService service) {
        System.out.print("Roll number: ");
        String roll = sc.nextLine();
        System.out.print("Full name: ");
        String name = sc.nextLine();
        System.out.print("Course: ");
        String course = sc.nextLine();
        System.out.print("Email (optional): ");
        String email = sc.nextLine();
        if (service.addStudent(roll, name, course, email)) {
            System.out.println("Added.");
        } else {
            System.out.println("Not added.");
        }
    }

    private static void viewAll(StudentService service) {
        ArrayList<Student> all = service.listAll();
        if (all.isEmpty()) {
            System.out.println("No records.");
            return;
        }
        for (Student s : all) {
            System.out.println(s.getId() + " | " + s.summaryLine() + " | " + nullSafe(s.getEmail()));
        }
    }

    private static void updateFlow(Scanner sc, StudentService service) {
        System.out.print("Student id: ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid id.");
            return;
        }
        Student cur = service.getStudent(id);
        if (cur == null) {
            System.out.println("Not found.");
            return;
        }
        System.out.println("Current: " + cur.summaryLine());
        System.out.print("New roll number: ");
        cur.setRollNumber(sc.nextLine());
        System.out.print("New full name: ");
        cur.setFullName(sc.nextLine());
        System.out.print("New course: ");
        cur.setCourse(sc.nextLine());
        System.out.print("New email: ");
        cur.setEmail(sc.nextLine());
        if (service.editStudent(cur)) {
            System.out.println("Updated.");
        } else {
            System.out.println("Update failed.");
        }
    }

    private static void deleteFlow(Scanner sc, StudentService service) {
        System.out.print("Student id to delete: ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid id.");
            return;
        }
        if (service.removeStudent(id)) {
            System.out.println("Deleted.");
        } else {
            System.out.println("Delete failed.");
        }
    }

    private static void searchFlow(Scanner sc, StudentService service) {
        System.out.print("Keyword: ");
        String kw = sc.nextLine();
        ArrayList<Student> hits = service.searchStudents(kw);
        if (hits.isEmpty()) {
            System.out.println("No matches.");
            return;
        }
        for (Student s : hits) {
            System.out.println(s.getId() + " | " + s.summaryLine());
        }
    }

    private static String nullSafe(String v) {
        return v == null ? "-" : v;
    }
}
