package app.model;

public class Student extends Person {
    private int id;
    private String rollNumber;
    private String course;

    public Student(String rollNumber, String fullName, String course, String email) {
        super(fullName, email);
        this.rollNumber = rollNumber;
        this.course = course;
        this.id = 0;
    }

    public Student(int id, String rollNumber, String fullName, String course, String email) {
        super(fullName, email);
        this.id = id;
        this.rollNumber = rollNumber;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String summaryLine() {
        return rollNumber + " | " + getFullName() + " | " + course;
    }
}
