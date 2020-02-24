package service;

import config.RewriteKQWebClient;
import dao.QQCardDao;

import java.util.List;

/**
 * Created by zhong on 2020-2-18.
 */
public class QQCardService implements  Runnable{

    private String card;

    private RewriteKQWebClient client;

    public void setCard(String card) {
        this.card = card;
    }

    public void setClient(RewriteKQWebClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        syncQQCard("2272068516");
    }

    public synchronized void syncQQCard(String qqid){
        if(!qqid.equals( "2272068516")){
            return;
        }

        QQCardDao qqCardDao = new QQCardDao();
        List<String>qqList = qqCardDao.getAllQQ();

        for(String qq : qqList){
            client.getGroupMemberInfo("787953276",qq,"1");
            try {
                this.wait();

                qqCardDao.updateQQCard(qq,card);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
