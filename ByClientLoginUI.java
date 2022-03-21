package net.by0119;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class ByClientLoginUI {
    public static final ImageIcon bgimg = new ImageIcon("img/bg_login3.jpg");
    public static final ImageIcon bg_register_img = new ImageIcon("img/bg_register1.jpg");
    public static final ImageIcon logoimg = new ImageIcon("img/logo.png");
//    private ByClientReceiveMsg byClientReceiveMsg;

    public void login(ByClient byClient) throws FileNotFoundException {
        JFrame loginUI = new JFrame("客户端登录");
        loginUI.setSize(500,625);
        loginUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginUI.setLayout(null);

//        JTextField nameInput = new JTextField(12);
//        nameInput.setSize(160,40);
//        nameInput.setLocation(200,130);

        JTextField idInput = new JTextField(12);
        idInput.setSize(160,40);
        idInput.setLocation(200,180);

        JPasswordField passwordInput = new JPasswordField(12);
        passwordInput.setSize(160,40);
        passwordInput.setLocation(200,230);

//        JLabel nameLabel = new JLabel("Name:");
//        nameLabel.setSize(80,10);
//        nameLabel.setLocation(120,145);
//        nameLabel.setForeground(Color.lightGray);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setSize(80,10);
        idLabel.setLocation(120,195);
        idLabel.setForeground(Color.lightGray);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setSize(80,10);
        passwordLabel.setLocation(120,245);
        passwordLabel.setForeground(Color.lightGray);

        JLabel bgLabel = new JLabel(bgimg);
        bgLabel.setSize(500,625);
        bgLabel.setLocation(0,0);

        JLabel logoLabel = new JLabel("BY·Chatting");
        logoLabel.setSize(100,66);
        logoLabel.setLocation(10,535);
        logoLabel.setForeground(Color.lightGray);

        JButton loginButton = new JButton("登录");
        loginButton.setSize(80,30);
        loginButton.setLocation(155,310);

        JButton registerButton = new JButton("注册");
        registerButton.setSize(80,30);
        registerButton.setLocation(265,310);

        //loginUI.add(nameInput);
        loginUI.add(idInput);
        loginUI.add(passwordInput);
        //loginUI.add(nameLabel);
        loginUI.add(idLabel);
        loginUI.add(passwordLabel);
        loginUI.add(loginButton);
        loginUI.add(registerButton);

        loginUI.add(logoLabel);
        loginUI.add(bgLabel);
        loginUI.setVisible(true);
        byClient.getByClientReceiveMsg().setClientLoginUI(loginUI);

        ByClientLoginListener byClientLoginListener = new ByClientLoginListener(this,loginUI,loginUI.getGraphics(),byClient,byClient.getByClientReceiveMsg(),idInput,passwordInput);
        loginButton.addActionListener(byClientLoginListener);
        registerButton.addActionListener(byClientLoginListener);


        loginUI.addMouseListener(byClientLoginListener);

    }
//    public static void main(String[] args) throws FileNotFoundException {
//        new ByClientLoginUI().login();
//    }

    public void register(ByClient byClient) {
        System.out.println("注册ing");
        JFrame registerUI = new JFrame("用户注册");

        System.out.println("注册ing2");
        registerUI.setSize(500,625);
        registerUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerUI.setLayout(null);

        JTextField nameInput = new JTextField(12);
        nameInput.setSize(160,40);
        nameInput.setLocation(200,80);

        JTextField idInput = new JTextField(12);
        idInput.setSize(160,40);
        idInput.setLocation(200,130);

        JPasswordField passwordInput = new JPasswordField(12);
        passwordInput.setSize(160,40);
        passwordInput.setLocation(200,180);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setSize(80,10);
        nameLabel.setLocation(120,95);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setSize(80,10);
        idLabel.setLocation(120,145);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setSize(80,10);
        passwordLabel.setLocation(120,195);

        JLabel bgLabel = new JLabel(bg_register_img);
        bgLabel.setSize(500,625);
        bgLabel.setLocation(0,0);

        JLabel logoLabel = new JLabel("BY·Chatting");
        logoLabel.setSize(100,66);
        logoLabel.setLocation(10,535);
        logoLabel.setForeground(Color.lightGray);

        JButton loginButton = new JButton("登录");
        loginButton.setSize(80,30);
        loginButton.setLocation(155,310);

        JButton registerButton = new JButton("注册");
        registerButton.setSize(80,30);
        registerButton.setLocation(210,260);

        registerUI.add(nameInput);
        registerUI.add(idInput);
        registerUI.add(passwordInput);
        registerUI.add(nameLabel);
        registerUI.add(idLabel);
        registerUI.add(passwordLabel);
        //registerUI.add(loginButton);
        registerUI.add(registerButton);
        registerUI.add(logoLabel);
        registerUI.add(bgLabel);
        byClient.getByClientReceiveMsg().setClientRegisterUI(registerUI);

        //loginButton.addActionListener();
        ByClientRegisterListener byClientRegisterListener = new ByClientRegisterListener(this,registerUI,registerUI.getGraphics(),nameInput,idInput,passwordInput,byClient, byClient.getByClientReceiveMsg());

        registerButton.addActionListener(byClientRegisterListener);
        nameInput.addActionListener(byClientRegisterListener);

        registerUI.addMouseListener(byClientRegisterListener);

        registerUI.setVisible(true);
    }
//    public static void main(String[] args){
//        //new ByClientLoginUI().register();
//    }
}
