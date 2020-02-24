package config;

import org.apache.log4j.Logger;

import java.net.URI;

public class KQClient {

    private static Logger logger = Logger.getLogger(KQClient.class);

    private static RewriteKQWebClient kqWebClient;

    public static void runClient(String host){
        try {
            if(kqWebClient == null){
                //连接coolq服务器
                kqWebClient = new RewriteKQWebClient(new URI(host));
            }
            //消息监听适配器
            MyQQAdapter myQQAdapter = new MyQQAdapter(kqWebClient);
            //监听消息
            kqWebClient.addQQMSGListenner(myQQAdapter);
        }catch (Exception e){
            System.err.println("init KQ client fail e:"+e.getMessage());
            e.printStackTrace();
        }
    }


}
