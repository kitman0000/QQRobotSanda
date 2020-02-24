package service;

import config.KQClient;
import config.RewriteKQWebClient;
import dao.CheckInDao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zhong on 2020-2-18.
 */
public class CheckInService {
    public void checkIn(RewriteKQWebClient client, String msg, String qq){
        msg = msg.replace(":","：");
        msg = msg.replace(",","，");

        String[] msgContent = msg.split("，",3);

        String stuID = msgContent[0].split("：")[0];
        String checkInDate = msgContent[0].split("：")[1];
        String city = msgContent[1].replace("在","");
        String status = msgContent[2];

        CheckInDao checkInDao = new CheckInDao();
        boolean insertResult = checkInDao.insertCheckIn(stuID,checkInDate,city,status,qq);

        if(insertResult)
            client.sendPrivateMSG(qq,"【提示】打卡成功");
        else
            client.sendPrivateMSG(qq,"【提示】打开失败，请勿重复打卡");
    }

    public void checkProgress(RewriteKQWebClient client,String qq){
        if(!qq.equals("2272068516")&&!qq.equals("184677182")){
            return;
        }
        CheckInDao checkInDao = new CheckInDao();
        ResultSet resultSet = checkInDao.checkProgress();

        int number = 0;
        String unCheckQQ = "";
        try {
            while(resultSet.next()){
                number++;
                unCheckQQ += resultSet.getString("card") +":" + resultSet.getString("qq") + "\r\n";
            }

            if(number != 0)
                client.sendPrivateMSG(qq,"目前剩余" + number + "位同学没有打卡！\r\n" + unCheckQQ);
            else
                client.sendPrivateMSG(qq,"所有同学都已经打卡!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void noticeCheck(RewriteKQWebClient client,String qq){
        if(!qq.equals("2272068516")&&!qq.equals("184677182")){
            return;
        }

        CheckInDao checkInDao = new CheckInDao();
        ResultSet resultSet = checkInDao.checkProgress();

        try {
            while(resultSet.next()){
                String unCheckQQ = resultSet.getString("qq");
                client.sendPrivateMSG(unCheckQQ,"【提示】今日尚未打卡哦，请前往打卡！(如果已经打卡，请尝试重新打卡并注意格式)");
            }

            client.sendPrivateMSG(qq,"已经向未打卡同学发送通知!");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
