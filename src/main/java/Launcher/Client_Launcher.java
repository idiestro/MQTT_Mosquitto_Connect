package Launcher;

import java.util.HashMap;
import java.util.Properties;

import MQTT.MqttConnection;
import Utils.*;

public class Client_Launcher extends Thread {

    private static String host;
    private static String port;
    private static String clientId;
    private static String topicSubscribe;
    private static String topicPublish;
    private static int qosLevel;
    private static MqttConnection mqttConnection;
    private static randomMeasures measures;

    private static void setUp() {
        try {
            Properties prop = Utils.getConfigProperties();
            host = prop.getProperty("HOST");
            port = prop.getProperty("PORT");
            clientId = prop.getProperty("CLIENTID");
            topicSubscribe = prop.getProperty("TOPIC_SUBSCRIBE");
            topicPublish = prop.getProperty("TOPIC_PUBLISH");
            qosLevel = Integer.valueOf(prop.getProperty("QOS_LEVEL"));

            mqttConnection = new MqttConnection();
            measures = new randomMeasures();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        HashMap<String, String> data = new HashMap<>();
        String payload;
        try {
            //Setup connection
            mqttConnection.connectionSetUp(host, port, clientId);
            //Set callback
            mqttConnection.callBack();
            //Connect to broker
            mqttConnection.connect();
            //Subscribe to topic
            mqttConnection.subscribeTopic(topicSubscribe, qosLevel);
            while (true) {
                //Create data payload
                data.put("temperature", String.valueOf(measures.getTemperature()));
                data.put("brightness", String.valueOf(measures.getBrightness()));
                //convert data into Json
                payload = Utils.createJsonPayload(data);
                //Send message to broker
                mqttConnection.sendMessage(payload, topicPublish, qosLevel);
                //Wait X ms
                Thread.sleep(3000);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        setUp();
        Client_Launcher main = new Client_Launcher();
        main.run();
    }
}