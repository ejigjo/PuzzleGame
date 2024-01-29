package com.work1.ui;

import cn.hutool.core.io.FileUtil;

import domain.User;
import util.CodeUtil;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class LoginJFrame extends JFrame implements MouseListener {

    ArrayList<User> allUsers = new ArrayList<>();


    JButton login = new JButton();
    JButton register = new JButton();

    JTextField username = new JTextField();
    //JTextField password = new JTextField();
    JPasswordField password = new JPasswordField();
    JTextField code = new JTextField();

    //正確的驗證碼
    JLabel rightCode = new JLabel();

    public LoginJFrame() {
        //獲取使用者帳號密碼
        getUserInfo();
        //初始化介面
        initJFrame();
        //在這個介面中加入內容
        initView();
        //讓當前介面顯示出來
        this.setVisible(true);
    }

    private void getUserInfo() {
        //導入用戶帳號密碼放入集合當中
        List<String> userInfoList = FileUtil.readUtf8Lines("C:\\Users\\Jason Kuo\\IdeaProjects\\puzzlegame\\userinfo.txt");
        //遍歷集合當中每一筆使用者數據
        for (String u : userInfoList) {
            String userName = u.split("&")[0].split("=")[1];
            String passWord = u.split("&")[1].split("=")[1];
            User user = new User(userName, passWord);
            allUsers.add(user);
        }
        System.out.println(allUsers);
    }


    //點擊
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == login) {
            System.out.println("點擊了登入按鈕");
            //取得兩個文字輸入框中的內容
            String usernameInput = username.getText();
            String passwordInput = password.getText();
            //取得使用者輸入的驗證碼
            String codeInput = code.getText();

            //建立一個User對象
            User userInfo = new User(usernameInput, passwordInput);
            System.out.println("使用者輸入的使用者名稱為" + usernameInput);
            System.out.println("使用者輸入的密碼為" + passwordInput);

            if (codeInput.length() == 0) {
                showJDialog("驗證碼不能為空");
            } else if (usernameInput.length() == 0 || passwordInput.length() == 0) {
                //校驗使用者名稱和密碼是否為空
                System.out.println("使用者名稱或密碼為空");
                //呼叫showJDialog方法並展示彈框
                showJDialog("使用者名稱或密碼為空");
            } else if (!codeInput.equalsIgnoreCase(rightCode.getText())) {
                showJDialog("驗證碼輸入錯誤");
            } else if (contains(userInfo)) {
                System.out.println("使用者名稱和密碼正確可以開始玩遊戲了");
                //關閉目前登入介面
                this.setVisible(false);
                //開啟遊戲的主介面
                //需要把目前登入的使用者名稱傳遞給遊戲介面
                new GameJFrame();
            } else {
                System.out.println("使用者名稱或密碼錯誤");
                showJDialog("使用者名稱或密碼錯誤");
            }
        } else if (e.getSource() == register) {
            System.out.println("點擊了註冊按鈕");
            //關閉目前的登入介面
            this.setVisible(false);
            //開啟註冊介面
            new RegisterJFrame(allUsers);
        } else if (e.getSource() == rightCode) {
            System.out.println("更換驗證碼");
            //取得一個新的驗證碼
            String code = CodeUtil.getCode();
            rightCode.setText(code);
        }
    }

    //展示彈框
    public void showJDialog(String content) {
        //建立一個彈框對象
        JDialog jDialog = new JDialog();
        //給彈框設定大小
        jDialog.setSize(200, 150);
        //讓彈框置頂
        jDialog.setAlwaysOnTop(true);
        //讓彈框居中
        jDialog.setLocationRelativeTo(null);
        //彈框不關閉永遠無法操作下面的介面
        jDialog.setModal(true);

        //建立Jlabel物件管理文字並加入到彈框當中
        JLabel warning = new JLabel(content);
        warning.setBounds(0, 0, 200, 150);
        jDialog.getContentPane().add(warning);

        //讓彈框展示出來
        jDialog.setVisible(true);
    }

    //按下不鬆
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == login) {
            login.setIcon(new ImageIcon("..\\puzzlegame\\image\\login\\登入按下.png"));
        } else if (e.getSource() == register) {
            register.setIcon(new ImageIcon("..\\puzzlegame\\image\\login\\註冊按下.png"));
        }
    }

    //放開按鈕
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == login) {
            login.setIcon(new ImageIcon("..\\puzzlegame\\image\\login\\登入按鈕.png"));
        } else if (e.getSource() == register) {
            register.setIcon(new ImageIcon("..\\puzzlegame\\image\\login\\註冊按鈕.png"));
        }
    }

    //滑鼠劃入
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    //滑鼠劃出
    @Override
    public void mouseExited(MouseEvent e) {

    }

    //判斷使用者在集合中是否存在
    public boolean contains(User userInput) {
        for (int i = 0; i < allUsers.size(); i++) {
            User rightUser = allUsers.get(i);
            if (userInput.getUsername().equals(rightUser.getUsername()) && userInput.getPassword().equals(rightUser.getPassword())) {
                //有相同的代表存在，回傳true，後面的不需要再比了
                return true;
            }
        }
        //循環結束之後還沒找到就表示不存在
        return false;
    }

    //在這個介面中加入內容
    public void initView() {
        //1. 新增使用者名稱文字
        JLabel usernameText = new JLabel(new ImageIcon("..\\puzzlegame\\image\\login\\使用者名稱.png"));
        usernameText.setBounds(95, 140, 90, 17);
        this.getContentPane().add(usernameText);

        //2.新增使用者名稱輸入框
        username.setBounds(195, 134, 200, 30);
        this.getContentPane().add(username);

        //3.新增密碼文字
        JLabel passwordText = new JLabel(new ImageIcon("..\\puzzlegame\\image\\login\\密碼.png"));
        passwordText.setBounds(100, 200, 80, 16);
        this.getContentPane().add(passwordText);

        //4.密碼輸入框
        password.setBounds(195, 195, 200, 30);
        this.getContentPane().add(password);

        //驗證碼提示
        JLabel codeText = new JLabel(new ImageIcon("..\\puzzlegame\\image\\login\\驗證碼.png"));
        codeText.setBounds(120, 256, 70, 30);
        this.getContentPane().add(codeText);

        //驗證碼的輸入框
        code.setBounds(195, 256, 100, 30);
        code.addMouseListener(this);
        this.getContentPane().add(code);


        String codeStr = CodeUtil.getCode();
        //設定內容
        rightCode.setText(codeStr);
        //綁定滑鼠事件
        rightCode.addMouseListener(this);
        //位置和寬高
        rightCode.setBounds(300, 256, 50, 30);
        //加入到介面
        this.getContentPane().add(rightCode);

        //5.新增登入按鈕
        login.setBounds(123, 310, 128, 47);
        login.setIcon(new ImageIcon("..\\puzzlegame\\image\\login\\登入按鈕.png"));
        //去除按鈕的邊框
        login.setBorderPainted(false);
        //去除按鈕的背景
        login.setContentAreaFilled(false);
        //給登入按鈕綁定滑鼠事件
        login.addMouseListener(this);
        this.getContentPane().add(login);

        //6.新增註冊按鈕
        register.setBounds(256, 310, 128, 47);
        register.setIcon(new ImageIcon("..\\puzzlegame\\image\\login\\註冊按鈕.png"));
        //去除按鈕的邊框
        register.setBorderPainted(false);
        //去除按鈕的背景
        register.setContentAreaFilled(false);
        //給註冊按鈕綁定滑鼠事件
        register.addMouseListener(this);
        this.getContentPane().add(register);
        //7.新增背景圖片
        JLabel background = new JLabel(new ImageIcon("..\\puzzlegame\\image\\login\\background.png"));
        background.setBounds(0, 0, 470, 390);
        this.getContentPane().add(background);

    }

    //初始化介面
    public void initJFrame() {
        this.setSize(488, 430);//設定寬高
        this.setTitle("Puzzle Game v1.0 LOGIN");//設定標題
        this.setDefaultCloseOperation(3);//設定關閉模式
        this.setLocationRelativeTo(null);//居中
        this.setAlwaysOnTop(true);//置頂
        this.setLayout(null);//取消內部預設佈局
    }
}