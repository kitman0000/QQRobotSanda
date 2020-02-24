package dao;

import com.mysql.cj.protocol.Resultset;
import util.DateUtil;
import util.MysqlUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zhong on 2020-2-18.
 */
public class CheckInDao {
    MysqlUtil mysqlUtil = new MysqlUtil();

    public boolean insertCheckIn(String stuID,String checkInDate,String city,String status,String sender){

        String dateStr = DateUtil.getDateString();

        ResultSet resultset = mysqlUtil.ExecuteSQL("SELECT count(id) FROM tb_check_in WHERE sender = '"+ sender + "' AND sysDate = '"+ dateStr  +"'",true);
        try {
            resultset.next();
            int hasInsert = resultset.getInt(1);

            if(hasInsert != 0) return false;
        } catch  (SQLException e) {
            e.printStackTrace();
        }

        mysqlUtil.ExecuteSQL("INSERT INTO tb_check_in (stuID,checkInDate,city,status,sender,sysDate) " +
                "VALUES ('" + stuID + "','"+checkInDate + "','"+city + "','"+status+"','"+sender+"','"+dateStr+"')",false);
        return true;
    }

    public ResultSet checkProgress(){

        String dateStr = DateUtil.getDateString();
        return mysqlUtil.ExecuteSQL("select * from tb_stu_member where qq not in (select sender from tb_check_in where sysDate ='"+dateStr+"')",true);
    }
}
