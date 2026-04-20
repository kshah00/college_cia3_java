package app.service;

import app.dao.StudentDAO;
import app.dao.StudentRepository;
import app.model.Student;
import java.util.ArrayList;

public class StudentService {
    private final StudentRepository repo;

    public StudentService() {
        this.repo = new StudentDAO();
    }

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public boolean addStudent(Student s) {
        if (!isValid(s)) {
            return false;
        }
        if (repo.findByRoll(s.getRollNumber()) != null) {
            System.out.println("Roll number already exists.");
            return false;
        }
        return repo.save(s);
    }

    public boolean addStudent(String roll, String name, String course, String email) {
        return addStudent(new Student(roll, name, course, email));
    }

    public Student getStudent(int id) {
        if (id <= 0) {
            return null;
        }
        return repo.findById(id);
    }

    public Student getStudent(String rollNumber) {
        if (rollNumber == null || rollNumber.trim().isEmpty()) {
            return null;
        }
        return repo.findByRoll(rollNumber.trim());
    }

    public ArrayList<Student> listAll() {
        return repo.findAll();
    }

    public boolean editStudent(Student s) {
        if (s.getId() <= 0 || !isValid(s)) {
            return false;
        }
        Student existing = repo.findById(s.getId());
        if (existing == null) {
            System.out.println("Student not found.");
            return false;
        }
        Student clash = repo.findByRoll(s.getRollNumber());
        if (clash != null && clash.getId() != s.getId()) {
            System.out.println("Another student uses this roll number.");
            return false;
        }
        return repo.update(s);
    }

    public boolean removeStudent(int id) {
        if (id <= 0) {
            return false;
        }
        return repo.delete(id);
    }

    public ArrayList<Student> searchStudents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return repo.search(keyword.trim());
    }

    private boolean isValid(Student s) {
        if (s.getRollNumber() == null || s.getRollNumber().trim().isEmpty()) {
            System.out.println("Roll number is required.");
            return false;
        }
        if (s.getFullName() == null || s.getFullName().trim().isEmpty()) {
            System.out.println("Name is required.");
            return false;
        }
        if (s.getCourse() == null || s.getCourse().trim().isEmpty()) {
            System.out.println("Course is required.");
            return false;
        }
        return true;
    }
}
