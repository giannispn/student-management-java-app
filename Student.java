import java.io.Serializable;

public class Student implements Serializable {
    private String firstName;
    private String lastName;
    private int age;
    private double grade;

    public Student(String firstName, String lastName, int age, double grade){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.grade = grade;
    }

    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }

    public int getAge(){
        return age;
    }

    public double getGrade(){
        return grade;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setGrade(double grade){
        this.grade = grade;
    }

    @Override
    public String toString(){
        return firstName + " " + lastName + " | " + age + " ετών | Βαθμός: " + grade;
    }


}