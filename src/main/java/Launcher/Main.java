package Launcher;

import java.util.Properties;
import MQTT.MqttConnection;
import Utils.Utils;

public class Main {

    private static String host;
    private static String port;
    private static MqttConnection mqttConnection;
    private static void setUp(){
        Properties prop = null;
        try {
            prop = Utils.getConfigProperties();
            host = prop.getProperty("HOST");
            port = prop.getProperty("PORT");
            mqttConnection = new MqttConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        setUp();
        mqttConnection.connect(host,port, "Cliente 1");
    }
}