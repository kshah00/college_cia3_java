package app.dao;

import app.model.Student;
import java.util.ArrayList;

public interface StudentRepository {
    boolean save(Student student);

    Student findById(int id);

    Student findByRoll(String rollNumber);

    ArrayList<Student> findAll();

    boolean update(Student student);

    boolean delete(int id);

    ArrayList<Student> search(String keyword);
}
