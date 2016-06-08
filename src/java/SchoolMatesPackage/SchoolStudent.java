/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SchoolMatesPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author vtv13qau
 */
public class SchoolStudent {
    private int schoolID;
    private int studentID;
    private int startYear;
    private int endYear;

    public SchoolStudent(int schoolID, int studentID, int startYear, int endYear) {
        this.schoolID = schoolID;
        this.studentID = studentID;
        this.startYear = startYear;
        this.endYear = endYear;
    }
    
    public void persist() throws SQLException{
        DBAccessor db = new DBAccessor();
        Connection c;
        try {
            c = db.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Connection failed..."), e);
        }//Connection established

        PreparedStatement q = c.prepareStatement("INSERT INTO school_student "
                + "(school_id, student_id, start_year, end_year)"
                + " VALUES(?,?,?,?)");
        q.setInt(1, schoolID);
        q.setInt(2, studentID);
        q.setInt(3, startYear);
        q.setInt(4, endYear);



        q.executeUpdate();
        c.close();
    }
    
    public static ArrayList <SchoolStudent> getAllAttendance(int studentID) throws SQLException{
        DBAccessor db = new DBAccessor();
        Connection c;
        try {
            c = db.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Connection failed..."), e);
        }//Connection established
        
        PreparedStatement q = c.prepareStatement("SELECT  DISTINCT school_id, start_year, end_year  FROM school_student WHERE student_id =?");
        q.setInt(1, studentID);
        
        ArrayList <SchoolStudent> list  = new ArrayList<SchoolStudent>();
        ResultSet result = q.executeQuery();
        c.close();
        
        while (result.next()){
            int schoolID = result.getInt("school_id");
            int startYear = result.getInt("start_year");
            int endYear = result.getInt("end_year");
            SchoolStudent s = new SchoolStudent(schoolID, studentID, startYear, endYear);
            list.add(s);
            
        }
        return list;
    }
    public static SchoolStudent getAttendance(int studentID, int schoolID) throws SQLException{
        DBAccessor db = new DBAccessor();
        Connection c;
        try {
            c = db.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Connection failed..."), e);
        }//Connection established
        
        PreparedStatement q = c.prepareStatement("SELECT  DISTINCT start_year, end_year  FROM school_student WHERE student_id =? AND school_id =?");
        q.setInt(1, studentID);
        q.setInt(2, schoolID);
        
        
        ResultSet result = q.executeQuery();
        c.close();
        
        SchoolStudent s = null;
        while (result.next()){
            int startYear = result.getInt("start_year");
            int endYear = result.getInt("end_year");
            
            s = new SchoolStudent(schoolID, studentID, startYear, endYear);
            break;
        }
        return s;
    }
    
    public void delete() throws SQLException{
        DBAccessor db = new DBAccessor();
        Connection c;
        try {
            c = db.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Connection failed..."), e);
        }//Connection established
        
        PreparedStatement q = c.prepareStatement("DELETE FROM school_student WHERE school_id=? AND student_id=? AND start_year=? AND end_year=?");
        q.setInt(1, schoolID);
        q.setInt(2, studentID);
        q.setInt(3, startYear);
        q.setInt(4, endYear);
                
        q.executeUpdate();
        c.close();
                
    }
    
    public int getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(int schoolID) {
        this.schoolID = schoolID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }
        
}

