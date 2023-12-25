package Launcher;

import Utils.Utils;


import MQTT.*;

import java.util.HashMap;
import java.util.Properties;

public class Control_Launcher extends Thread{

    private static HashMap<String, String> dataset;
    private static Properties prop;
    private static MqttConnection mqttConnection;
    private static String host,port,clientId,topicSubscribe;
    private static int qosLevel;


    private static void setUp(){
        try {
            prop = Utils.getConfigProperties();
            host = prop.getProperty("HOST");
            port = prop.getProperty("PORT");
            clientId = prop.getProperty("CONTROL_CLIENTID");
            topicSubscribe = prop.getProperty("CONTROL_TOPIC_SUBSCRIBE");
            qosLevel = Integer.valueOf(prop.getProperty("CONTROL_QOS_LEVEL"));

            mqttConnection = new MqttConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static void mqttConnect(){
        //Setup connection
        mqttConnection.connectionSetUp(host, port, clientId);
        //Set callback
        mqttConnection.callBack();
        //Connect to broker
        mqttConnection.connect();
        //Subscribe to topic
        mqttConnection.subscribeTopic(topicSubscribe, qosLevel);
    }

    public void run(){
        mqttConnect();
    }
    public static void main(String[] args) {
        setUp();
        Control_Launcher control = new Control_Launcher();
        control.run();
    }
}

