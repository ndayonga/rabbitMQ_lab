import java.io.IOException;
import java.io.UnsupportedEncodingException;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.Random;

import com.rabbitmq.client.*;

enum State{IDLE, WAITING, STARTED};

public class Process1 {

    private final static String QUEUE_NAME = "ping_pong_queue";
    private static State state = State.IDLE;
    private static int processId = generateRandomId(5, 10);   // id entre 5 et 10

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            String[] parts = message.split(" ");

            Message msg;

            String messageText = parts[0];
            int id = Integer.parseInt(parts[1]);
            msg = new Message(messageText, id);

            receiveMessage(msg, channel);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        sendMessage(channel, new Message("START", processId));

    }

    private static void sendMessage(Channel channel, Message msg) {
        try {
            String message = msg.getMessage()+" "+ msg.getId();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println("out : " + message);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void receiveMessage(Message msg, Channel channel) {
        String message = msg.getMessage();
        int id = msg.getId();
        System.out.println("in : " + message);

        if (id == processId) return;

        switch(message){
            case "START" : {
                if (state == State.IDLE){
                    state = State.WAITING;
                    sendMessage(channel, new Message("INIT_CONN", processId));
                }
                break;
            }

            case "INIT_CONN" : {
                if (state == State.WAITING){
                    state = State.STARTED;
                    sendMessage(channel, new Message("OK_CONN", processId));
                }

                else if(state == State.IDLE){
                    state = State.WAITING;
                    sendMessage(channel, new Message(message, processId));
                }
                break;
            }

            case "OK_CONN" : {
                state = State.STARTED;
                sendMessage(channel, new Message("PING", processId));
                break;
            }

            case "PING" : {
                sendMessage(channel,  new Message("PONG", processId));
                break;
            }

            case "PONG" : {
                sendMessage(channel, new Message("PING", processId));
                break;
            }
        }
        
    }

    private static int generateRandomId(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}