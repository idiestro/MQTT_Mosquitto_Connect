package MQTT;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Arrays;

public class MqttConnection {

    private MqttClient mqttClient;
    private MqttConnectOptions mqttConnectOptions;
    private MqttMessage mqttMessage;
    private MemoryPersistence persistence = new MemoryPersistence();

    private String clientId, topic;

    /*
    Init connection and setup mqtt
     */
    public void connectionSetUp(String host, String port, String client) {
        clientId = client;
        System.out.println("Connecting to broker...");
        String finalEndpoint = "tcp://" + host + ":" + port;
        try {
            mqttClient = new MqttClient(finalEndpoint, clientId, persistence);
            mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(true);
        } catch (MqttException e) {
            exceptionPrinter(e);
            throw new RuntimeException(e);
        }
    }

    /*
    Connect to broker
     */
    public void connect() {
        try {
            mqttClient.connect(mqttConnectOptions);
        } catch (MqttException e) {
            System.out.println("Connection failed!");
            exceptionPrinter(e);
            throw new RuntimeException(e);
        }
        System.out.println("Connection successfully!");
    }

    /*
    Stablish connection to receive message from broker
     */
    public void callBack() {
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                System.out.println("Conexión perdida con el broker MQTT");
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                System.out.println("Message received: " + mqttMessage + "\n" + "On topic: " + topic);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    /*
    Publish message into broker
     */
    public void sendMessage(String message, String topic, int QoSLevel) {
        try {
            mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(QoSLevel);
            mqttClient.publish(topic, mqttMessage);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Message: " + message + " publised, to topic: " + topic);
    }

    /*
    Subscribe to topic
     */
    public void subscribeTopic(String topicName, int qosLevel) {
        topic = topicName;
        try {
            mqttClient.subscribe(topicName, qosLevel);
            System.out.println("Subscribed to topic: " + topic);
        } catch (MqttException e) {
            System.out.println("Failed subscribing to topic: " + topic);
            exceptionPrinter(e);
            throw new RuntimeException(e);
        }
    }

    /*
    Print MQTT exceptions messages
     */
    private void exceptionPrinter(MqttException e) {
        System.out.println("Reason:" + e.getReasonCode());
        System.out.println("Message:" + e.getMessage());
        System.out.println("Localization:" + e.getLocalizedMessage());
        System.out.println("Cause:" + e.getCause());
        System.out.println("Exception:" + e);
    }
}
