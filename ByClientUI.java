package net.by0119;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class ByClientUI implements VMeetingConfig{
    public static final ImageIcon bgimg = new ImageIcon("img/bg_dialog.jpg");
    private ByUser localUser;
    private ByUser targetUser = new ByUser();

    public ByClientUI(ByUser localUser) {
        this.localUser = localUser;
    }

    public JFrame initUI(String title, Socket socket,ByClientReceiveMsg byClientReceiveMsg){
        JFrame jFrame = new JFrame(title);
        jFrame.setLocation(400,100);
        jFrame.setSize(S_WIDTH,S_HEIGHT);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);

        JLabel bgLabel = new JLabel(bgimg);
        bgLabel.setSize(S_WIDTH,S_HEIGHT);
        bgLabel.setLocation(0,0);


        JPanel leftBar = new JPanel();
        leftBar.setBackground(Color.lightGray);
        Dimension dim = new Dimension(LeftBar_WEIGHT,LeftBar_HEIGHT);
        leftBar.setPreferredSize(dim);

        String information = "Client Information \n\nIP:"+socket.getInetAddress().getHostAddress()+"\nport:"+String.valueOf(socket.getLocalPort())+"\n";
        String index = "name:"+this.localUser.getName()+"\n"+"ID:"+this.localUser.getID();

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

        friendListScroll.setViewportView(friendList);



        JTextArea targetID = new JTextArea(10,14);
        targetID.setSize(120,60);
        targetID.setEditable(true);
        targetID.setBackground(Color.white);
        targetID.setLineWrap(true);


        JButton LogoutBtn = new JButton("log out");
        LogoutBtn.setBackground(Color.white);
        LogoutBtn.setSize(btnWidth,btnHeight);



        leftBar.add(info);
        leftBar.add(targetID);
        leftBar.add(LogoutBtn);

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

        JTextPane showDia = new JTextPane();
        showDia.setEditable(false);
//        showDia.setLineWrap(true);
        showDia.setBackground(Color.WHITE);

//        JTextArea showDia = new JTextArea("dialog",20,28);
//        showDia.setEditable(false);
//        showDia.setLineWrap(true);
//        showDia.setBackground(Color.WHITE);

        showDia.setSize(380,400);

//        showDia.setBounds(150,150,30,30);
//        showDia.setLocation(0,150);
        dialogAbove.setViewportView(showDia);
        dialog.add(dialogAbove,BorderLayout.NORTH);
        byClientReceiveMsg.setDialogTrack(showDia);

        JPanel toolbar = new JPanel();
        toolbar.setBackground(Color.lightGray);
        toolbar.setSize(380,18);

        JButton ConfirmSendBtn = new JButton("发送");
        ConfirmSendBtn.setBackground(Color.white);
        ConfirmSendBtn.setSize(btnWidth,btnHeight);

        JButton PaintBtn = new JButton("涂鸦");
        PaintBtn.setBackground(Color.white);
        PaintBtn.setSize(btnWidth,btnHeight);

        JButton PhotoBtn = new JButton("图片");
        PhotoBtn.setBackground(Color.white);
        PhotoBtn.setSize(btnWidth,btnHeight);

        JButton VideoCallBtn = new JButton("视频通话");
        VideoCallBtn.setBackground(Color.white);
        VideoCallBtn.setSize(btnWidth,btnHeight);

        JButton FileBtn = new JButton("文件");
        FileBtn.setBackground(Color.white);
        FileBtn.setSize(btnWidth,btnHeight);

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


        toolbar.add(ConfirmSendBtn);
        toolbar.add(PaintBtn);
        toolbar.add(PhotoBtn);
        toolbar.add(VideoCallBtn);
        toolbar.add(FileBtn);
        toolbar.add(CleanInputBtn);
        toolbar.add(CleanHistoryBtn);

        dialog.add(dialogBottom,BorderLayout.SOUTH);
        dialog.add(toolbar);

//        dialog.add(dialogAbove);
//        dialog.add(dialogBottom);

        jFrame.add(bgLabel);
        jFrame.add(leftBar,BorderLayout.WEST);
        jFrame.add(dialog,BorderLayout.EAST);
//        jFrame.add(bgLabel);

        jFrame.setVisible(true);
        ByClientListener byClientListener = new ByClientListener(jFrame,socket,inputArea,showDia,dialogTitle,this.localUser,this.targetUser);
        byClientListener.setTargetID(targetID);
        byClientListener.setByClient(byClientReceiveMsg.getByClient());
        byClientListener.setByClientUI(this);
        byClientReceiveMsg.setByClientUI(this);
        byClientListener.setByClientReceiveMsg(byClientReceiveMsg);

        dialog.addMouseListener(byClientListener);
        friendList.addListSelectionListener(byClientListener);

        LogoutBtn.addActionListener(byClientListener);
        PaintBtn.addActionListener(byClientListener);
        PhotoBtn.addActionListener(byClientListener);
        VideoCallBtn.addActionListener(byClientListener);
        FileBtn.addActionListener(byClientListener);
        ConfirmSendBtn.addActionListener(byClientListener);
        CleanInputBtn.addActionListener(byClientListener);
        CleanHistoryBtn.addActionListener(byClientListener);

        jFrame.addMouseListener(byClientListener);

        return jFrame;
    }

    public JFrame videoUI(Socket socket, ByClientReceiveMsg byClientReceiveMsg) {
        JFrame jFrame = new JFrame("Video Call");
        jFrame.setSize(400,600);
        jFrame.setLayout(null);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);

        JButton beginBtn = new JButton("拨打");
        beginBtn.setLocation(70,500);
        beginBtn.setSize(100,40);

        JButton hangOutBtn = new JButton("结束视频");
        hangOutBtn.setLocation(230,500);
        hangOutBtn.setSize(100,40);


        jFrame.add(beginBtn);
        jFrame.add(hangOutBtn);

        jFrame.setVisible(true);
        ByClientVideoCallListener byClientVideoCallListener = new ByClientVideoCallListener();
        byClientVideoCallListener.setSocket(socket);
        byClientVideoCallListener.setLocalUser(localUser);
        byClientVideoCallListener.setTargetUser(targetUser);
        byClientVideoCallListener.setPen(jFrame.getGraphics());

        beginBtn.addActionListener(byClientVideoCallListener);
        hangOutBtn.addActionListener(byClientVideoCallListener);

        return jFrame;
    }

    public JFrame getVideoUI(Socket socket, ByClientReceiveMsg byClientReceiveMsg) {
        JFrame jFrame = new JFrame("Video Call");
        jFrame.setSize(400,600);
        jFrame.setLayout(null);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);

        JButton beginBtn = new JButton("接听");
        beginBtn.setLocation(70,500);
        beginBtn.setSize(100,40);

        JButton hangOutBtn = new JButton("结束视频");
        hangOutBtn.setLocation(230,500);
        hangOutBtn.setSize(100,40);


        jFrame.add(beginBtn);
        jFrame.add(hangOutBtn);

        jFrame.setVisible(true);
        ByClientVideoCallListener byClientVideoCallListener = new ByClientVideoCallListener();
        byClientVideoCallListener.setSocket(socket);
        byClientVideoCallListener.setLocalUser(localUser);
        byClientVideoCallListener.setTargetUser(targetUser);
        byClientVideoCallListener.setPen(jFrame.getGraphics());

        beginBtn.addActionListener(byClientVideoCallListener);
        hangOutBtn.addActionListener(byClientVideoCallListener);

        return jFrame;
    }
}
