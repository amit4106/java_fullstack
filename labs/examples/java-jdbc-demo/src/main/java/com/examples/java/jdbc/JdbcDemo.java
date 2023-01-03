package com.examples.java.jdbc;

import java.sql.*;

public class JdbcDemo {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String LOCAL_DB_URL = "jdbc:mysql://localhost:3306/mysql_training";
    static final String RDS_DB_URL = "jdbc:mysql://rds-mysql.ct3izbioghub.ap-south-1.rds.amazonaws.com:3306/mysql_training";
    // Database credentials
    static final String USER = "training";
    static final String PASS = "training";
    public static void main(String[] args) throws SQLException {
        System.out.println("JDBC Demo!!!");

        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // STEP 1: Loading Driver [optional]
//        try {
//            Class.forName(JDBC_DRIVER);
//        } catch (ClassNotFoundException e) {
//            System.out.println("Error loading Driver - " + e.getMessage());
//            System.exit(1);
//        }
//        System.out.println("Driver loaded successfully");

        // STEP 2: Create Connection
        try {
            conn = DriverManager.getConnection(RDS_DB_URL, USER, PASS);
            // Enabling Transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Error while creating connection - " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Connection created successfully - " + conn);

        // STEP 3: Create (Prepared)Statement
        try {
            stmt = conn.createStatement();
            System.out.println("Statement created successfully - " + stmt);

            // STEP 4: Execute Query
//            String insertQuery = "INSERT INTO EMPLOYEE (NAME, AGE, GENDER, SALARY, DESIGNATION, DEPARTMENT, COUNTRY_ID) VALUES('Ankit',25,'M',50000,'Developer','IT',2)";
//            int insertCount = stmt.executeUpdate(insertQuery);
//            System.out.println("No of recored inserted - " + insertCount);
//            conn.commit();

            String insertQuery = "INSERT INTO EMPLOYEE (NAME, AGE, GENDER, SALARY, DESIGNATION, DEPARTMENT, COUNTRY_ID) VALUES(?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1,"Ankur");
            pstmt.setInt(2, 30);
            pstmt.setString(3,"M");
            pstmt.setDouble(4,50000.0);
            pstmt.setString(5,"Lead");
            pstmt.setString(6,"IT");
            pstmt.setInt(7, 2);
            int insertCount = pstmt.executeUpdate();
            System.out.println("No of recored inserted - " + insertCount);

            String updateQuery = "UPDATE EMPLOYEE SET designation='Manager' WHERE name='Ankur'";
            int updateCount = stmt.executeUpdate(updateQuery);
            System.out.println("No of recored updated - " + updateCount);

            // commiting the changes
            conn.commit();

            // STEP 5: Get Resultset [for SELECT queries]
            String selectQuery = "SELECT * FROM EMPLOYEE";
            rs = stmt.executeQuery(selectQuery);

            // STEP 6: Browse through the records [for SELECT queries]
            System.out.println("ID \t NAME \t AGE \t SALARY \t DESIGNATION \t DEPARTMENT");
            while(rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                int age = rs.getInt("AGE");
                double salary = rs.getDouble("SALARY");
                String designation = rs.getString("DESIGNATION");
                String department = rs.getString("DEPARTMENT");

                System.out.println(id + "\t" + name + "\t" + age + "\t" + salary + "\t" + designation + "\t" + department );
            }

        } catch (SQLException e) {

            try {
                conn.rollback();
            }catch(SQLException e1) {
                System.out.println("Error while rolling back - " + e1.getMessage());
            }

            System.out.println("Error while creating statement or executing query - " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } finally {

            // STEP 7: Close connection
            try {
                rs.close();
                System.out.println("Resultset Closed");
            } catch (SQLException e) {
                System.out.println("Error closing Result Set - " + e.getMessage());
            }

            try {
                stmt.close();
                System.out.println("Statement Closed");

            } catch (SQLException e) {
                System.out.println("Error closing Statement - " + e.getMessage());
            }

            try {
                conn.close();
                System.out.println("Connection Closed");

            } catch (SQLException e) {
                System.out.println("Error closing Connection - " + e.getMessage());

            }
        }



    }
}
