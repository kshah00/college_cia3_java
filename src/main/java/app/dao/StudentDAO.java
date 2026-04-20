package app.dao;

import app.model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAO implements StudentRepository {

    @Override
    public boolean save(Student student) {
        String sql = "INSERT INTO students (roll_number, full_name, course, email) VALUES (?,?,?,?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, student.getRollNumber());
            ps.setString(2, student.getFullName());
            ps.setString(3, student.getCourse());
            ps.setString(4, student.getEmail());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Save failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Student findById(int id) {
        String sql = "SELECT id, roll_number, full_name, course, email FROM students WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Find by id failed: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Student findByRoll(String rollNumber) {
        String sql = "SELECT id, roll_number, full_name, course, email FROM students WHERE roll_number=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, rollNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Find by roll failed: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Student> findAll() {
        ArrayList<Student> list = new ArrayList<>();
        String sql = "SELECT id, roll_number, full_name, course, email FROM students ORDER BY id";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("List failed: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean update(Student student) {
        String sql = "UPDATE students SET roll_number=?, full_name=?, course=?, email=? WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, student.getRollNumber());
            ps.setString(2, student.getFullName());
            ps.setString(3, student.getCourse());
            ps.setString(4, student.getEmail());
            ps.setInt(5, student.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Delete failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<Student> search(String keyword) {
        ArrayList<Student> list = new ArrayList<>();
        String like = "%" + keyword + "%";
        String sql = "SELECT id, roll_number, full_name, course, email FROM students "
                + "WHERE roll_number LIKE ? OR full_name LIKE ? OR course LIKE ? OR IFNULL(email,'') LIKE ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setString(4, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Search failed: " + e.getMessage());
        }
        return list;
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("id"),
                rs.getString("roll_number"),
                rs.getString("full_name"),
                rs.getString("course"),
                rs.getString("email")
        );
    }
}
