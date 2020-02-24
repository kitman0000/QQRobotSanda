package config;

import com.mumu.listenner.KQMSGAdapter;
import com.mumu.msg.AddAdmin;
import com.mumu.msg.RE_MSG_Forum;
import com.mumu.msg.RE_MSG_Group;
import com.mumu.msg.RE_MSG_Private;
import dao.CheckInDao;
import service.CheckInService;
import service.QQCardService;

public class MyQQAdapter extends KQMSGAdapter {

    private RewriteKQWebClient kqWebClient;

    private QQCardService qqCardService;

    public MyQQAdapter(RewriteKQWebClient kqWebClient) {
        kqWebClient.setQqAdapter(this);
        this.kqWebClient = kqWebClient;
    }

    /**
     * 接收私聊消息
     */
    public synchronized void Re_MSG_Private(RE_MSG_Private msg) {
        System.out.println("接收到私聊信息 from:"+msg.getFromqq()+" \t msg:"+msg.getMsg());

        if(msg.getMsg().equals("查看打卡进度")){
            CheckInService checkInService = new CheckInService();
            checkInService.checkProgress(kqWebClient,msg.getFromqq());
        }

        if(msg.getMsg().equals("提醒打卡")){
            CheckInService checkInService = new CheckInService();
            checkInService.noticeCheck(kqWebClient,msg.getFromqq());
        }

        if(msg.getMsg().equals("同步群名片")){
            qqCardService = new QQCardService();
            qqCardService.setClient(kqWebClient);

            Thread asyncThread = new Thread(qqCardService);

            if(msg.getFromqq().equals("2272068516"))
                asyncThread.start();
        }
    }

    public void RE_MSG_FORUM(RE_MSG_Forum msg) {
        System.out.println("接收到消息 groupName:"+msg.getFromQQ() + "qq:"+msg.getFromQQ() + "msg:"+msg.getMsg());
    }

    /**
     * 接收群消息
     */
    public void RE_MSG_Group(RE_MSG_Group msg) {
        if(msg.getMsg().toLowerCase().replace(" ","").startsWith("f")){ // 打卡
            CheckInService checkInService = new CheckInService();
            checkInService.checkIn(kqWebClient,msg.getMsg(),msg.getFromQQ());
        }
    }

    /**
     * 群成员添加
     */
    public void RE_EXAMPLE_AMEMBER(AddAdmin msg) {

    }

    public void checkCardThreadNotify(String qqid,String nickName){
        synchronized (qqCardService) {
            qqCardService.setCard(nickName);
            qqCardService.notify();
        }
    }


}
