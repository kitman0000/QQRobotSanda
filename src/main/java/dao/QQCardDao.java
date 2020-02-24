package dao;

import util.MysqlUtil;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhong on 2020-2-18.
 */
public class QQCardDao {
    MysqlUtil mysqlUtil = new MysqlUtil();

    public List<String> getAllQQ() {
        ResultSet resultSet = mysqlUtil.ExecuteSQL("SELECT qq FROM tb_stu_member", true);

        List<String> stringList = new LinkedList<>();

        try {
            while (resultSet.next()) {
                stringList.add(resultSet.getString(1));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return stringList;
    }

    public void updateQQCard(String qqid,String card){
        mysqlUtil.ExecuteSQL("UPDATE tb_stu_member SET card = '" + card+ "' WHERE qq = '" + qqid + "'",false);
    }
}
