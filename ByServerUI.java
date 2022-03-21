package net.by0119;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ByServerUI implements VMeetingConfig{
    private Socket socket;
//    private ByServerReceiveMsg byServerReceiveMsg;
    private JTextArea showDia;
    public static final ImageIcon bgimg = new ImageIcon("img/bg_dialog.jpg");

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

//    public void setByServerReceiveMsg(ByServerReceiveMsg byServerReceiveMsg) {
//        this.byServerReceiveMsg = byServerReceiveMsg;
//    }

    public JTextArea getShowDia() {
        return showDia;
    }

    public JFrame initUI(String title, ServerSocket serverSocket, String name, int ID) throws UnknownHostException {
        JFrame jFrame = new JFrame(title);
        jFrame.setLocation(400,100);
        jFrame.setSize(S_WIDTH,S_HEIGHT);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);

        JPanel leftBar = new JPanel();
        leftBar.setBackground(Color.lightGray);
        Dimension dim = new Dimension(LeftBar_WEIGHT,LeftBar_HEIGHT);
        leftBar.setPreferredSize(dim);

        String information = "Server Information \n\nIP:"+serverSocket.getInetAddress().getHostAddress()+"\nport:"+String.valueOf(serverSocket.getLocalPort())+"\n";
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        String index = "name:"+name+"\n"+"ID:"+ID;
        JTextArea info = new JTextArea(information+index,5,15);
        info.setEditable(false);
        info.setBackground(Color.lightGray);
        info.setLineWrap(true);
        //IP.setSize(200,300);

        JScrollPane friendListScroll = new JScrollPane();
        friendListScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        friendListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        String[] friendName = {"皮皮井","小河鱼","李诗情","张成","王兴德"};
        JList friendList = new JList(friendName);
        System.out.println(friendName);
        friendList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JLabel bgLabel = new JLabel(bgimg);
        bgLabel.setSize(500,625);
        bgLabel.setLocation(0,0);


        friendListScroll.setViewportView(friendList);
        leftBar.add(info);
        leftBar.add(bgLabel);
//        leftBar.add(friendListScroll);

        JPanel dialog = new JPanel();
        dialog.setSize(C_Dia_WEIGHT,C_Dia_HEIGHT);
        dialog.setLayout(new BorderLayout());

        JScrollPane dialogAbove = new JScrollPane();
        dialogAbove.setBackground(Color.gray);
        Dimension dimAbove = new Dimension(C_Dia_WEIGHT,C_Dia_HEIGHT_Above);
        dialogAbove.setPreferredSize(dimAbove);
        dialogAbove.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        dialogAbove.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JLabel dialogTitle = new JLabel();
        dialogTitle.setBackground(Color.orange);
        dialogTitle.setSize(20,5);
        dialogAbove.add(dialogTitle);

        JTextArea showDia = new JTextArea("dialog",20,28);
        showDia.setEditable(false);
        showDia.setLineWrap(true);
        showDia.setBackground(Color.WHITE);

        showDia.setSize(380,400);
//        showDia.setBounds(150,150,30,30);
//        showDia.setLocation(0,150);
        dialogAbove.setViewportView(showDia);
        dialog.add(dialogAbove,BorderLayout.NORTH);
        this.showDia = showDia;
//        this.byServerReceiveMsg.setDialogTrack(showDia);

        JPanel toolbar = new JPanel();
        toolbar.setBackground(Color.lightGray);
        toolbar.setSize(380,18);

        JButton PaintBtn = new JButton("涂鸦");
        PaintBtn.setBackground(Color.white);
        PaintBtn.setSize(btnWidth,btnHeight);

        JButton ConfirmSendBtn = new JButton("发送");
        ConfirmSendBtn.setBackground(Color.white);
        ConfirmSendBtn.setSize(btnWidth,btnHeight);

        JButton CleanInputBtn = new JButton("清空");
        CleanInputBtn.setBackground(Color.white);
        CleanInputBtn.setSize(btnWidth,btnHeight);

        JButton CleanHistoryBtn = new JButton("清空聊天记录");
        CleanHistoryBtn.setBackground(Color.white);
        CleanHistoryBtn.setSize(btnWidth,btnHeight);

        JScrollPane dialogBottom = new JScrollPane();
        dialogBottom.setBackground(Color.WHITE);
        Dimension dimBottom = new Dimension(C_Dia_WEIGHT,C_Dia_HEIGHT_Bottom);
        dialogBottom.setPreferredSize(dimBottom);
        dialogBottom.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        dialogBottom.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JTextArea inputArea = new JTextArea("input",10,28);
        inputArea.setEditable(true);
        inputArea.setLineWrap(true);
        inputArea.setSize(380,195);
        inputArea.setBackground(Color.white);

        dialogBottom.setViewportView(inputArea);

        toolbar.add(PaintBtn);
        toolbar.add(ConfirmSendBtn);
        toolbar.add(CleanInputBtn);
        toolbar.add(CleanHistoryBtn);

        dialog.add(dialogBottom,BorderLayout.SOUTH);
        dialog.add(toolbar);

//        dialog.add(dialogAbove);
//        dialog.add(dialogBottom);


        jFrame.setVisible(true);

        ByServerListener byServerListener = new ByServerListener(jFrame,this.socket,inputArea,showDia,dialogTitle,name,ID);
        dialog.addMouseListener(byServerListener);

        friendList.addListSelectionListener(byServerListener);

        PaintBtn.addActionListener(byServerListener);
        ConfirmSendBtn.addActionListener(byServerListener);
        CleanInputBtn.addActionListener(byServerListener);
        CleanHistoryBtn.addActionListener(byServerListener);

        jFrame.add(leftBar,BorderLayout.WEST);
        jFrame.add(dialog,BorderLayout.EAST);
        jFrame.addMouseListener(byServerListener);

        return jFrame;
    }
}
