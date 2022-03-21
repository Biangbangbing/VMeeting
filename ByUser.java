package net.by0119;

import java.net.Socket;

public class ByUser {
    String name;
    int ID;
    String password;
    int state;

    Socket socket = null;
    String ip;
    int port;

    public Socket getSocket() {
        return socket;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ByUser() {
    }

    public ByUser(int ID, String password) {
        this.ID = ID;
        this.password = password;
    }

    public ByUser(String name, int ID, String password) {
        this.name = name;
        this.ID = ID;
        this.password = password;
    }

    public ByUser(String name, int ID) {
        this.name = name;
        this.ID = ID;
        this.password = "0000";
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
