package com.work1.ui;

import cn.hutool.core.io.FileUtil;
import com.work1.ui.LoginJFrame;
import domain.User;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class RegisterJFrame extends JFrame implements MouseListener {

    ArrayList<User> allUsers;

    //提升三個輸入框的變數的作用範圍，讓這三個變數可以在本類別中所有方法裡面可以使用。
    JTextField username = new JTextField();
    JTextField password = new JTextField();
    JTextField rePassword = new JTextField();

    //提升兩個按鈕變數的作用範圍，讓這兩個變數可以在本類別中所有方法裡面可以使用。
    JButton submit = new JButton();
    JButton reset = new JButton();

    public RegisterJFrame(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
        initFrame();
        initView();
        setVisible(true);
    }

    //註冊介面點擊功能
    @Override
    public void mouseClicked(MouseEvent e) {
        //點擊註冊邏輯
        if (e.getSource() == submit) {
            //判斷用戶輸入是否為空白
            if (username.getText().length() == 0 || password.getText().length() == 0 || rePassword.getText().length() == 0) {
                showDialog("用戶名和密碼不能為空白");
                //判斷兩次密碼輸入是否一樣
            } else if (!password.getText().equals(rePassword.getText())) {
                showDialog("兩次密碼輸入不相同,請重新輸入");
                //判斷用戶和密碼輸入格式是否符合規範
            } else if (!username.getText().matches("[a-zA-Z0-9]{4,16}")) {
                showDialog("輸入用戶不符合規則,請重新輸入");
            } else if (!password.getText().matches("\\S*(?=\\S{6,})(?=\\S*\\d)(?=\\S*[a-z])\\S*")) {
                showDialog("輸入密碼不符合規則,至少包含1個小寫字母,1個數字,長度至少6位,請重新輸入");
                //判斷新輸入的用戶是否已經存在
            } else if (containsUsername(username.getText())) {
                showDialog("該用戶已經存在");
            }else {
                //判斷都符合,將新用戶資料加入集合當中
                allUsers.add(new User(username.getText(), password.getText()));
                //將用戶資料寫入記事本
                FileUtil.writeLines(allUsers, "C:\\Users\\Jason Kuo\\IdeaProjects\\puzzlegame\\userinfo.txt", "UTF-8");
                //提示註冊成功
                showDialog("註冊成功");
                //關閉註冊頁面
                this.setVisible(false);
                //開啟登錄頁面
                new LoginJFrame();
            }
            //點擊重置邏輯
        } else if (e.getSource() == reset) {
            username.setText("");
            password.setText("");
            rePassword.setText("");
        }

    }


    /*
     * 作用：
     * 判斷username在集合中是否存在
     * 參數：
     *       使用者名稱
     * 傳回值：
     * true：存在
     * false：不存在
     *
     * */
    public boolean containsUsername(String username) {
        for (User u : allUsers) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == submit) {
            submit.setIcon(new ImageIcon("..//puzzlegame\\image\\register\\註冊按下.png"));
        } else if (e.getSource() == reset) {
            reset.setIcon(new ImageIcon("..//puzzlegame\\image\\register\\重設按下.png"));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == submit) {
            submit.setIcon(new ImageIcon("..//puzzlegame\\image\\register\\註冊按鈕.png"));
        } else if (e.getSource() == reset) {
            reset.setIcon(new ImageIcon("..//puzzlegame\\image\\register\\重設按鈕.png"));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void initView() {
        //新增註冊用戶名的文本
        JLabel usernameText = new JLabel(new ImageIcon("..//puzzlegame\\image\\register\\註冊使用者名稱.png"));
        usernameText.setBounds(85, 135, 90, 20);

        //新增註冊使用者名稱的輸入框
        username.setBounds(195, 134, 200, 30);

        //新增註冊密碼的文本
        JLabel passwordText = new JLabel(new ImageIcon("..//puzzlegame\\image\\register\\註冊密碼.png"));
        passwordText.setBounds(90, 195, 90, 30);

        //新增密碼輸入框
        password.setBounds(195, 195, 200, 30);

        //新增再次輸入密碼的文字
        JLabel rePasswordText = new JLabel(new ImageIcon("..//puzzlegame\\image\\register\\再次輸入密碼.png"));
        rePasswordText.setBounds(25, 255, 170, 35);

        //新增再次輸入密碼的輸入框
        rePassword.setBounds(195, 255, 200, 30);

        //註冊的按鈕
        submit.setIcon(new ImageIcon("..//puzzlegame\\image\\register\\註冊按鈕.png"));
        submit.setBounds(123, 310, 128, 47);
        submit.setBorderPainted(false);
        submit.setContentAreaFilled(false);
        submit.addMouseListener(this);

        //重置的按鈕
        reset.setIcon(new ImageIcon("..//puzzlegame\\image\\register\\重設按鈕.png"));
        reset.setBounds(256, 310, 128, 47);
        reset.setBorderPainted(false);
        reset.setContentAreaFilled(false);
        reset.addMouseListener(this);
        //背景圖片
        JLabel background = new JLabel(new ImageIcon("..//puzzlegame\\image\\register\\background.png"));
        background.setBounds(0, 0, 470, 390);

        this.getContentPane().add(usernameText);
        this.getContentPane().add(passwordText);
        this.getContentPane().add(rePasswordText);
        this.getContentPane().add(username);
        this.getContentPane().add(password);
        this.getContentPane().add(rePassword);
        this.getContentPane().add(submit);
        this.getContentPane().add(reset);
        this.getContentPane().add(background);
    }

    private void initFrame() {
        //對自己的介面做一些設定。
        //設定寬高
        setSize(488, 430);
        //設定標題
        setTitle("Puzzle Game v1.0 Register");
        //取消內部預設佈局
        setLayout(null);
        //設定關閉模式
        setDefaultCloseOperation(3);
        //設定居中
        setLocationRelativeTo(null);
        //設定置頂
        setAlwaysOnTop(true);
    }

    //只建立一個彈框對象
    JDialog jDialog = new JDialog();

    //因為展示彈框的程式碼，會運行多次
    //所以，我們把展示彈框的程式碼，抽取到一個方法中。 以後用到的時候，就不需要寫了
    //直接呼叫就可以了。
    public void showDialog(String content) {
        if (!jDialog.isVisible()) {
            //把彈框中原來的文字給清空掉。
            jDialog.getContentPane().removeAll();
            JLabel jLabel = new JLabel(content);
            jLabel.setBounds(0, 0, 200, 150);
            jDialog.add(jLabel);
            //給彈框設定大小
            jDialog.setSize(200, 150);
            //要把彈框在設定為頂層 -- 置頂效果
            jDialog.setAlwaysOnTop(true);
            //要讓jDialog居中
            jDialog.setLocationRelativeTo(null);
            //讓彈框
            jDialog.setModal(true);
            //讓jDialog顯示出來
            jDialog.setVisible(true);
        }
    }
}