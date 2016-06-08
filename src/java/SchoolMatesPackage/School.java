package SchoolMatesPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class School {

    private int id;
    private String type;
    private String name;
    private String street;
    private String city;
    private String postCode;
    private String website;
    
    /**
     * Default constructor
     */
    public School() {}

    /**
     * Value based constructor
     * @param school_type
     * @param name
     * @param street
     * @param city
     * @param postCode
     * @param website 
     */
    public School(String type, String name, String street, String city, String postCode, String website) {
        this.type = type;
        this.name = name;
        this.street = street;
        this.city = city;
        this.postCode = postCode;
        this.website = website;
        
        this.id = 0; //initialising to zero to show it is a new school.
                    //The proper ID will be assigned when the object is persisted into the database
    }

    /**
     * Persist method that inserts into the database or updates it. 
     * If the id=0(which means an object was just created)
     * insert new school into the database
     * else (means we have the id and the school is already in the database)
     * update the school's records in the database
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

            PreparedStatement q = c.prepareStatement("INSERT INTO school "
                    + "(school_type, name, street, city, post_code, website)"
                    + " VALUES(?,?,?,?,?,?)");
            q.setString(1, type);
            q.setString(2, name);
            q.setString(3, street);
            q.setString(4, city);
            q.setString(5, postCode);
            q.setString(6, website);


            q.executeUpdate();
        } else { //update

            PreparedStatement q = c.prepareStatement("UPDATE school"
                    + " SET school_type =?, name =?, street =?, city =?, post_code =?,website =?"
                    + " WHERE id =?;");
            q.setString(1, type);
            q.setString(2, name);
            q.setString(3, street);
            q.setString(4, city);
            q.setString(5, postCode);
            q.setString(6, website);
            q.setInt(8, id);
            
            q.executeUpdate();

        }

        c.close();
    }
    
    public static School findByID(int id) throws SQLException {
        DBAccessor db = new DBAccessor();
        Connection c = db.getConnection();

        PreparedStatement q = c.prepareStatement("SELECT * FROM school WHERE id =?");
        q.setInt(1, id);
        ResultSet results = q.executeQuery();

        db.close();
        try {
            if (results.next()) {
                School s = new School();
                s.id = id;
                s.type = results.getString("school_type");
                s.name = results.getString("name");
                
                s.street = results.getString("street");
                s.city = results.getString("city");
                s.postCode = results.getString("post_code");
                s.website = results.getString("website");
                return s;
            }
        } catch (Exception e) {
            throw new SQLException("Problem obtaining school's data");
        }
        
        return null;
    }
    
    public static ArrayList<School> listAll() throws SQLException{
        DBAccessor db = new DBAccessor();
        Connection c = db.getConnection();

        PreparedStatement q = c.prepareStatement("SELECT * FROM school");
        ResultSet results = q.executeQuery();

        
        try {
            ArrayList<School> list = new ArrayList<School>();
            while (results.next()) {
                School s = new School();
                s.id = results.getInt("id");
                s.type = results.getString("school_type");
                s.name = results.getString("name");                
                s.street = results.getString("street");
                s.city = results.getString("city");
                s.postCode = results.getString("post_code");
                s.website = results.getString("website");
                list.add(s);
                
                
            }
            c.close();
            return list;
        } catch (Exception e) {
            throw new SQLException("Problem obtaining list of all schools");
        }
        
        
    }
    
    public void setID(int id) {
        this.id = id;
    }

    
    public int getID() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String schoolType) {
        this.type = schoolType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

   
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    
    
}
