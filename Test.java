package com;

/**
 * ClassName:Test1
 * Package:com
 * Description:
 *
 * @Date:2020/1/3 13:55
 * @Author:DangWei
 */
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Test1 {
    private static final String URL="jdbc:mysql://localhost/ebook";
    private static final String USER="root";
    private static final String PASSWORD="";
    public static DataSource getDataSource(){
        MysqlDataSource ds=new MysqlDataSource();
        ds.setURL(URL);
        ds.setUser(USER);
        ds.setPassword(PASSWORD);
        return ds;
    }
    private static class Borrow{
        private int id;
        private int book_id;
        private int student_id;
        private String start_time;
        private String end_time;
        public Borrow(int id,int book_id,int student_id,String start_time,String end_time){
            this.id=id;
            this.book_id=book_id;
            this.student_id=student_id;
            this.start_time=start_time;
            this.end_time=end_time;
        }
    }

    public static void insertTable(Borrow borrow){
        DataSource ds=getDataSource();
        Connection connection=null;
        PreparedStatement stmt=null;
        try {
            connection=ds.getConnection();
            String str="insert into borrow_info values(?,?,?,?,?)";
            stmt=connection.prepareStatement(str);
            stmt.setInt(1,borrow.id);
            stmt.setInt(2,borrow.book_id);
            stmt.setInt(3,borrow.student_id);
            stmt.setString(4,borrow.start_time);
            stmt.setString(5,borrow.end_time);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(stmt!=null){
                    stmt.close();
                }
                if(connection!=null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static void selectTable(){
        DataSource ds=getDataSource();
        Connection connection=null;
        PreparedStatement stmt=null;
        ResultSet res=null;
        try {
            connection=ds.getConnection();
            String str="select borrow_info.id,borrow_info.student_id,borrow_info.start_time," +
                    "borrow_info.end_time,book.name,book.author" +
                    " from borrow_info" +
                    "  join book on borrow_info.book_id=book.id" +
                    "  join category on category.id=3 ";
            stmt=connection.prepareStatement(str);
            res=stmt.executeQuery();
            while(res.next()){
                Integer b_id=res.getInt("borrow_info.id");
                Integer s_id=res.getInt("borrow_info.student_id");
                String s_time=res.getString("borrow_info.start_time");
                String e_time=res.getString("borrow_info.end_time");
                String name=res.getString("book.name");
                String author=res.getString("book.author");
                System.out.println("borrow.id:"+b_id+"  borrow.student_id:"+
                        s_id+"  borrow.start_time:"+s_time+"  borrow.end_time:"+e_time+
                "  book.name:"+name+"  book.author:"+author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(res!=null){
                    res.close();
                }
                if(stmt!=null){
                    stmt.close();
                }
                if(connection!=null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateTable(){
        DataSource ds=getDataSource();
        Connection connection=null;
        PreparedStatement stmt=null;
        try {
            connection=ds.getConnection();
            String str="update book set price=61.20 where name=" +
                    "'深入理解Java虚拟机'";
            stmt=connection.prepareStatement(str);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                if(stmt!=null){
                    stmt.close();
                }
                if(connection!=null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteTable(){
        DataSource ds=getDataSource();
        Connection connection=null;
        PreparedStatement stmt=null;
        try {
            connection=ds.getConnection();
            String str="delete from borrow_info where id=19";
            stmt=connection.prepareStatement(str);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(stmt!=null){
                    stmt.close();
                }
                if(connection!=null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Borrow borrow=new Borrow(19,10,3,
                "2019-09-25 17:50:00","2019-10-25 17:50:00");
        insertTable(borrow);
        selectTable();
        updateTable();
        deleteTable();
    }
}
