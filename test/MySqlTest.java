package test;

import java.sql.*;

public class MySqlTest {
    public static String url = "jdbc:mysql://127.0.0.1:3306/mysql";
    public static String username = "HiCharm";
    public static String password = "114514";
    public static void main(String[] args) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            System.out.println("连接成功");
            Statement stmt = conn.createStatement();
            String sql = "SELECT VERSION()";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                System.out.println("数据库版本："+rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();    
        }
    }
}
