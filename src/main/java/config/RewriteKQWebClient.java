package config;

import cc.plural.jsonij.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mumu.webclient.KQWebClient;

import java.net.URI;

public class RewriteKQWebClient extends KQWebClient {
    private MyQQAdapter qqAdapter;

    public void setQqAdapter(MyQQAdapter qqAdapter) {
        this.qqAdapter = qqAdapter;
    }

    public RewriteKQWebClient(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void sendPrivateMSG(String qq, String msg) {
        try {
            Thread.sleep(10L);
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }
        JSONObject sendMsg = new JSONObject();

        sendMsg.put("act",106);
        sendMsg.put("QQID",qq);
        sendMsg.put("msg",msg);
        super.send(sendMsg.toJSONString());
    }

    @Override
    public void sendGroupMSG(String qq, String groupid, String msg, Boolean isAT) {
        try {
            Thread.sleep(10L);
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }
        JSONObject sendMsg = new JSONObject();
        sendMsg.put("act",101);
        sendMsg.put("groupid",groupid);
        sendMsg.put("msg",msg);
        super.send(sendMsg.toJSONString());
    }

    public void getGroupMemberInfo(String groupid,String qqid,String nocache){
        try {
            Thread.sleep(10L);
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }
        JSONObject sendMsg = new JSONObject();
        sendMsg.put("act",25303);
        sendMsg.put("groupid",groupid);
        sendMsg.put("QQID",qqid);
        sendMsg.put("nocache",nocache);
        super.send(sendMsg.toJSONString());
    }

    public void muteMember(String groupid,String qqid){
        try {
            Thread.sleep(10L);
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }
        JSONObject sendMsg = new JSONObject();
        sendMsg.put("act",121);
        sendMsg.put("groupid",groupid);
        sendMsg.put("QQID",qqid);
        sendMsg.put("duration",60*24*30*29*2);
        super.send(sendMsg.toJSONString());
    }

    public void changeMemberCard(String groupid,String qqid,String newcard){
        try {
            Thread.sleep(10L);
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }
        JSONObject sendMsg = new JSONObject();
        sendMsg.put("act",126);
        sendMsg.put("groupid",groupid);
        sendMsg.put("QQID",qqid);
        sendMsg.put("newcard",newcard);
        super.send(sendMsg.toJSONString());
    }

    public void changeMemberTitle(String groupid,String qqid,String newTitle){
        try {
            Thread.sleep(10L);
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }
        JSONObject sendMsg = new JSONObject();
        sendMsg.put("act",128);
        sendMsg.put("groupid",groupid);
        sendMsg.put("QQID",qqid);
        sendMsg.put("newspecialtitle",newTitle);
        super.send(sendMsg.toJSONString());
    }

    @Override
    public void onMessage(String msg) {
        super.onMessage(msg);
        try {
            JSON json = JSON.parse(msg);
            String act = json.get("act").getString();
            if(act.equals("0")){
                qqAdapter.checkCardThreadNotify(json.get("QQID").toString(),json.get("nick").toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
