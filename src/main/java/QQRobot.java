import config.KQClient;

public class QQRobot {
    public static void main(String[] args) {
        System.out.println("正在连接127.0.0.1:25301");
        KQClient.runClient("ws://127.0.0.1:25301");
    }
}
