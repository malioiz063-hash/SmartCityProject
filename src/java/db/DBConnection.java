package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection con;

    public static Connection getConnection() {

        try {

            if (con == null || con.isClosed()) {

                long startTime = System.currentTimeMillis();

                Class.forName("com.mysql.jdbc.Driver");

                con = DriverManager.getConnection(
                        "jdbc:mysql://hayabusa.proxy.rlwy.net:30697/railway?useSSL=false&serverTimezone=UTC",
                        "root",
                        "ZvKbnFnWCHURACtqnAesgWoQXPAVrDwE"
                );

                long endTime = System.currentTimeMillis();

                System.out.println("DATABASE CONNECTED");
                System.out.println(
                        "DB Connection Time = "
                        + (endTime - startTime)
                        + " ms");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return con;
    }
}











//package db;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//public class DBConnection {
//
//    private static Connection con;
//
//    public static Connection getConnection() {
//
//        try {
//
//            if (con == null || con.isClosed()) {
//
//                Class.forName("com.mysql.jdbc.Driver");
//
//                con = DriverManager.getConnection(
//                        "jdbc:mysql://hayabusa.proxy.rlwy.net:30697/railway?useSSL=false&serverTimezone=UTC",
//                        "root",
//                        "ZvKbnFnWCHURACtqnAesgWoQXPAVrDwE"
//                );
//
//                System.out.println("DATABASE CONNECTED");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return con;
//    }
//}









//package db;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//public class DBConnection {
//
//    public static Connection getConnection() {
//
//        try {
//
//            Class.forName("com.mysql.jdbc.Driver");
//
//            Connection con = DriverManager.getConnection(
//                    "jdbc:mysql://127.0.0.1:3306/smart_city",
//                    "root",
//                    "");
//
//            System.out.println("DATABASE CONNECTED");
//
//            return con;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            throw new RuntimeException(e);
//        }
//    }
//}