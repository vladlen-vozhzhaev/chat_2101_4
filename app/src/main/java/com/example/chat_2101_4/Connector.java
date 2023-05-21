package com.example.chat_2101_4;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connector {
    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;
    private static JSONObject jsonObject = new JSONObject();
    private static String response;
    public static void connect(){
        try {
            socket = new Socket("192.168.1.9", 9178);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean auth(String login, String pass){
        try {
            jsonObject.put("action", "login"); // -> Действие которое должен выполнить сервер (авторизация)
            jsonObject.put("login", login);
            jsonObject.put("pass", pass);
            out.writeUTF(jsonObject.toString()); // Отправляем данные на сервер
            response = in.readUTF();
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return response.equals("success");
    }
    public static void sendMessage(String message){
        try {
            out.writeUTF(jsonObject.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
