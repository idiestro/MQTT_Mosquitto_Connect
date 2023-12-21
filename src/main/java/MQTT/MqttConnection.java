package MQTT;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttConnection {

    private MqttClient mqttClient;
    private MqttConnectOptions mqttConnectOptions;
    private MqttMessage mqttMessage;
    private MemoryPersistence persistence = new MemoryPersistence();

    public void connect(String host, String port, String clientId){
        System.out.println("Connecting to broker...");
        String finalEndpoint = "tcp://" + host + ":" + port;
        try{
            mqttClient = new MqttClient(finalEndpoint, clientId, persistence);
            mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(true);
            mqttClient.connect(mqttConnectOptions);
            System.out.println("Connection successfully!");

        }catch (MqttException e){
            System.out.println("Connection failed!");
            exceptionPrinter(e);
        }

    }

    public void sendMessage(String message, int QoSLevel){
        mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(QoSLevel);
        System.out.println("Message publised!");
    }

    private void exceptionPrinter(MqttException e){
        System.out.println("Reason:" + e.getReasonCode());
        System.out.println("Message:" + e.getMessage());
        System.out.println("Localization:" + e.getLocalizedMessage());
        System.out.println("Cause:" + e.getCause());
        System.out.println("Exception:" + e);
    }
}
