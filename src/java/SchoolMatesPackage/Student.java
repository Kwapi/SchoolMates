package SchoolMatesPackage;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

public class Student {

    private int id;
    private String email;
    private String name;
    private String surname;
    private String schoolSurname;
    private String aboutMe;
    private String password;

    /**
     * Default constructor
     */
    public Student() {
    }

    public Student(String email, String password, String name, String surname, String schoolSurname) {
        this.id = 0; //initialising to zero to show it is a new student.
        //The proper ID will be assigned when the object is persisted into the database
        this.email = email;
        this.name = name;
        this.schoolSurname = schoolSurname;
        this.surname = surname;
        this.password = password;
        this.aboutMe = ""; //user can edit about_me later
        
        

    }

    /**
     * A method that returns true if the student with given parameters is found.
     * Else, false.
     *
     * @param email
     * @param password
     * @return
     */
    public static boolean exists(String email, String password) throws SQLException {
        DBAccessor db = new DBAccessor();
        Connection c = db.getConnection();

        PreparedStatement q = c.prepareStatement("SELECT * FROM student WHERE email =? AND password=?");
        q.setString(1, email);
        q.setString(2, password);
        ResultSet results = q.executeQuery();

        db.close();

        if (results.next()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the student exists based on the email
     *
     * @param email
     * @return
     * @throws SQLException
     */
    public static boolean exists(String email) throws SQLException {
        DBAccessor db = new DBAccessor();
        Connection c = db.getConnection();

        PreparedStatement q = c.prepareStatement("SELECT * FROM student WHERE email =?");
        q.setString(1, email);
        ResultSet results = q.executeQuery();

        db.close();

        if (results.next()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Searches for a Student whose email matches the one provided.
     *
     * @param email
     * @return Student if found, else null
     * @throws SQLException
     */
    public static Student findByEmail(String email) throws SQLException {
        DBAccessor db = new DBAccessor();
        Connection c = db.getConnection();

        PreparedStatement q = c.prepareStatement("SELECT * FROM student WHERE email =?");
        q.setString(1, email);
        ResultSet results = q.executeQuery();

        db.close();
        try {
            if (results.next()) {
                Student s = new Student();
                s.id = Integer.parseInt(results.getString("id"));
                s.email = email;
                s.password = results.getString("password");
                s.name = results.getString("name");
                s.surname = results.getString("surname");
                s.schoolSurname = results.getString("school_surname");
                s.aboutMe = results.getString("about_me");
                return s;
            }
        } catch (Exception e) {
            throw new SQLException("Problem obtaining student's data");
        }

        return null;
    }

    /**
     * Creates a Student object from their id
     */
    public static Student findByID(int id) throws SQLException {
        try {
            DBAccessor db = new DBAccessor();
            Connection c = db.getConnection();

            PreparedStatement q = c.prepareStatement("SELECT * FROM student WHERE id =?");
            q.setInt(1, id);
            ResultSet results = q.executeQuery();

            db.close();

            if (results.next()) {
                Student s = new Student();
                s.id = id;
                s.email = results.getString("email");
                s.password = results.getString("password");
                s.name = results.getString("name");
                s.surname = results.getString("surname");
                s.schoolSurname = results.getString("school_surname");
                s.aboutMe = results.getString("about_me");
                return s;
            }
        } catch (Exception e) {
            throw new SQLException("Problem obtaining student's data");
        }

        return null;
    }

    public static ArrayList<Student> listAll() throws SQLException {
         try {
            DBAccessor db = new DBAccessor();
            Connection c = db.getConnection();

            PreparedStatement q = c.prepareStatement("SELECT * FROM student");
            ResultSet results = q.executeQuery();
            
            ArrayList<Student> list = new ArrayList<Student>();
            

            db.close();
            
            while (results.next()) {
                Student s = new Student();
                s.id = results.getInt("id");
                s.name = results.getString("name");
                s.surname = results.getString("surname");
                s.schoolSurname = results.getString("school_surname");
                s.aboutMe = results.getString("about_me");
                
                list.add(s);
                
                return list;
            }
        } catch (Exception e) {
            throw new SQLException("Problem obtaining list of all students ");
        }

        return null;
    }
    
    public static ArrayList<Student> listAllFromSchool(int schoolID) throws SQLException {
         try {
            DBAccessor db = new DBAccessor();
            Connection c = db.getConnection();

            PreparedStatement q = c.prepareStatement("SELECT student.id, student.name, student.surname, student.school_surname FROM student, school_student WHERE student.id = school_student.student_id AND school_student.school_id =?");
            
            q.setInt(1, schoolID);
            ResultSet results = q.executeQuery();
            ArrayList<Student> list = new ArrayList<Student>();
            

            db.close();
            
            while (results.next()) {
                Student s = new Student();
                s.id = results.getInt("id");
                s.name = results.getString("name");
                s.surname = results.getString("surname");
                s.schoolSurname = results.getString("school_surname");
                               
                list.add(s);
                
                
            }
            if(list.isEmpty()){
                return null;
            }else{
             return list;   
            }
            
        } catch (Exception e) {
            throw new SQLException("Problem obtaining list of all students ");
        }

        
    }

    /**
     * Creates a Student object if the student id is stored in session
     *
     * @param session
     * @return
     * @throws SQLException
     */
    public static Student getFromSession(HttpSession session) throws SQLException {
        Integer id = (Integer) session.getAttribute("student_id");

        if (id != null) {
            return findByID(id);
        } else {
            return null;
        }
    }

    public void storeInSession(HttpSession session) {
        session.setAttribute("student_id", (Integer) id);
    }

    /**
     * Finds all schoolmates of the student with the given studentID
     *
     * @param schoolID
     * @param startYear
     * @param endYear
     * @param studentID
     * @return
     * @throws SQLException
     */
    public static ArrayList<Student> findSchoolMates(int schoolID, int startYear, int endYear, int studentID) throws SQLException {
        DBAccessor db = new DBAccessor();
        Connection c = db.getConnection();
        try {
            PreparedStatement q = c.prepareStatement("SELECT DISTINCT student_id FROM school_student WHERE (school_ID =? AND start_year <=? AND  end_year >=? AND student_id !=?)");
            q.setInt(1, schoolID);
            q.setInt(2, endYear);
            q.setInt(3, startYear);
            q.setInt(4, studentID);

            ResultSet results = q.executeQuery();
            ArrayList<Student> list = new ArrayList<Student>();
            Student s;
            while (results.next()) {
                s = Student.findByID(results.getInt("student_id"));
                list.add(s);

            }
            db.close();
            return list;



        } catch (Exception e) {
            throw new SQLException("Problem finding schoolmates");
        }
    }

    /**
     * Persist method that inserts into the database or updates it. If the
     * id=0(which means an object was just created) insert new student into the
     * database else (means we have the id and the student is already in the
     * database) update the student's records in the database
     *
     * @throws SQLException
     */
    public void persist() throws SQLException {
        DBAccessor db = new DBAccessor();
        Connection c;
        try {
            c = db.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Connection failed..."), e);
        }//Connection established

        if (id == 0) { //insert into database

            PreparedStatement q = c.prepareStatement("INSERT INTO student "
                    + "(email, password, name, surname, school_surname, about_me)"
                    + " VALUES(?,?,?,?,?,?)");
            q.setString(1, email);
            q.setString(2, password);
            q.setString(3, name);
            q.setString(4, surname);
            q.setString(5, schoolSurname);
            q.setString(6, aboutMe);

            q.executeUpdate();

        } else { //update

            PreparedStatement q = c.prepareStatement("UPDATE student"
                    + " SET email =?, password =?, name =?, surname =?, school_surname =?, about_me =?"
                    + " WHERE id =?;");
            q.setString(1, email);
            q.setString(2, password);
            q.setString(3, name);
            q.setString(4, surname);
            q.setString(5, schoolSurname);
            q.setString(6, aboutMe);
            q.setInt(7, id);

            q.executeUpdate();

        }

        if (id == 0) {
            //get the newly created ID and store it in this bean
            PreparedStatement q = c.prepareStatement("SELECT * FROM student ORDER BY id DESC Limit 1");
            ResultSet result = q.executeQuery();
            if (result.next()) {
                id = result.getInt(1);
            }

        }

        c.close();
    }
    
    
    
    public String getFullName(){
        StringBuilder str = new StringBuilder();
        
        str.append(name).append(" ").append(surname);
        
        if (!schoolSurname.isEmpty()){
            str.append("(").append(schoolSurname).append(")");
        }
        
        return str.toString();
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSchoolSurname() {
        return schoolSurname;
    }

    public void setSchoolSurname(String schoolSurname) {
        this.schoolSurname = schoolSurname;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
