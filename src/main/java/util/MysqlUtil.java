package util;

import java.sql.*;

/**
 * Created by zhong on 2018-7-10.
 */
public class MysqlUtil {
    Connection conn;

    String connectionStr = "jdbc:mysql://47.100.23.230:3306/qqrobot_db?serverTimezone=UTC&charset=utf8"; // 配置连接字符串

    String username = "root"; // 数据库用户名

    String password = "Liuyirui2000"; // 数据库密码

    private void open()
    {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            if(conn!=null&&!conn.isClosed()) return;
            conn = DriverManager.getConnection(connectionStr,username,password);
        }catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet ExecuteSQL(String sql,boolean HaveReturn)
    {
        open();

        try {
            Statement statement = conn.createStatement();
            if (HaveReturn)
                return statement.executeQuery(sql);
            else statement.execute(sql);
            //close();
            return null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //close();
        return null;
    }

    public String parseString(String str){
        str = str.replace("'","&apos;");
        str = str.replace("\"","&quot;");
        str = str.replace("<","&lt;");
        str = str.replace(">","&gt;");

        return str;
    }



}
