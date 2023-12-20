package MQTT;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttConnection {

    MqttClient mqttClient;
    MqttConnectOptions mqttConnectOptions;
    MqttMessage mqttMessage;

    public void connect(){
        System.out.println("Connecting to broker...");
        try{
            mqttClient = new MqttClient(host, clientId, persistence);
            mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(true);
            System.out.println("Connection successfully!");

        }catch (MqttException e){
            System.out.println("Connection failed!");
            exceptionPrinted(e);
        }

    }

    public void sendMessage(String message, int QoSLevel){
        mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(QoSLevel);
        System.out.println("Message publised!");
    }

    private void exceptionPrinted(MqttException e){
        System.out.println("Reason:" + e.getReasonCode());
        System.out.println("Message:" + e.getMessage());
        System.out.println("Localization:" + e.getLocalizedMessage());
        System.out.println("Cause:" + e.getCause());
        System.out.println("Exception:" + e);
    }
}
