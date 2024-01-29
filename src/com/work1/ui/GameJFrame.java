package com.work1.ui;

import cn.hutool.core.io.IoUtil;
import domain.GameInfo;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Properties;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {


    //一個二維數組用來管理圖片數據
    int[][] date = new int[4][4];
    //紀錄二維數組中位置
    int x = 0;
    int y = 0;
    //設置亂數數據
    Random r = new Random();
    //圖片路徑
    String path = "..\\puzzlegame\\image\\animal\\animal1\\";

    //定義一個勝利判定用數組
    int[][] win = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };
    //定義計數器變量
    int step = 0;

    //創建菜單選項的項目
    JMenuItem replayItem = new JMenuItem("Restart");
    JMenuItem reLoginItem = new JMenuItem("Register");

    JMenuItem girl = new JMenuItem("Sexy Girl");
    JMenuItem animal = new JMenuItem("Animal");
    JMenuItem sport = new JMenuItem("Sport");
    JMenuItem closeItem = new JMenuItem("Close Game");
    JMenuItem accountItem = new JMenuItem("Link");

    JMenu saveJMenu = new JMenu("Save");
    JMenu loadJMenu = new JMenu("Load");

    JMenuItem saveItem0 = new JMenuItem("Save0(null)");
    JMenuItem saveItem1 = new JMenuItem("Save1(null)");
    JMenuItem saveItem2 = new JMenuItem("Save2(null)");
    JMenuItem saveItem3 = new JMenuItem("Save3(null)");
    JMenuItem saveItem4 = new JMenuItem("Save4(null)");

    JMenuItem loadItem0 = new JMenuItem("Load0(null)");
    JMenuItem loadItem1 = new JMenuItem("Load1(null)");
    JMenuItem loadItem2 = new JMenuItem("Load2(null)");
    JMenuItem loadItem3 = new JMenuItem("Load3(null)");
    JMenuItem loadItem4 = new JMenuItem("Load4(null)");


    public GameJFrame() {
        //初始化視窗
        initJFrame();
        //初始化菜單
        initJMenuBar();
        //初始化數據(打亂圖片)
        initDate();
        //初始化圖片
        initImage();
        //將視窗顯示出來
        this.setVisible(true);
    }

    private void initDate() {
        int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            int index = r.nextInt(arr.length);
            int temp;
            temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            date[i / 4][i % 4] = arr[i];
        }

//        int number = 0;
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                if (date[i][j] == 0) {
//                    x = j;
//                    y = i;
//                }
//                date[i][j] = arr[number];
//                number++;
//            }
//
//        }
//
    }


    private void initImage() {
        //清空原本的圖片
        this.getContentPane().removeAll();
        //顯示勝利的圖片
        if (victory()) {
            JLabel winJLabel = new JLabel(new ImageIcon("C:\\Users\\Jason Kuo\\IdeaProjects\\puzzlegame\\image\\win.png"));
            winJLabel.setBounds(203, 300, 197, 73);
            this.getContentPane().add(winJLabel);
        }
        //顯示計步器
        JLabel stepCount = new JLabel("Step：" + step);
        stepCount.setBounds(460, 73, 100, 20);
        this.getContentPane().add(stepCount);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //導入被打亂的數據
                int num = date[i][j];
                //創建圖片
                ImageIcon icon = new ImageIcon(path + num + ".jpg");
                //創建jLable容器
                JLabel jLabel = new JLabel(icon);
                //設定座標與邊界(bounds)
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                //美化邊框(增加邊框樣式)
                jLabel.setBorder(new BevelBorder(0));
                //將jLable容器加入視窗中(content pane才是內容視窗)
                this.getContentPane().add(jLabel);
            }

        }
        //創建背景圖片
        ImageIcon backGroundImage = new ImageIcon("..\\puzzlegame\\image\\background.png");
        JLabel backGroundJLabel = new JLabel(backGroundImage);
        backGroundJLabel.setBounds(40, 30, 508, 560);
        this.getContentPane().add(backGroundJLabel);

        //刷新視窗
        this.getContentPane().repaint();
    }


    private void initJMenuBar() {
        //創建菜單
        JMenuBar jMenuBar = new JMenuBar();
        //創建菜單選項
        JMenu funtionMenu = new JMenu("Function");
        JMenu aboutMenu = new JMenu("About Me");
        JMenu changeImage = new JMenu("change Image");


        //把5個存檔，添加到saveJMenu中
        saveJMenu.add(saveItem0);
        saveJMenu.add(saveItem1);
        saveJMenu.add(saveItem2);
        saveJMenu.add(saveItem3);
        saveJMenu.add(saveItem4);

        //把5個讀檔，添加到loadJMenu中
        loadJMenu.add(loadItem0);
        loadJMenu.add(loadItem1);
        loadJMenu.add(loadItem2);
        loadJMenu.add(loadItem3);
        loadJMenu.add(loadItem4);


        //將菜單選項綁定滑鼠點擊事件
        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
        girl.addActionListener(this);
        animal.addActionListener(this);
        sport.addActionListener(this);
        saveItem0.addActionListener(this);
        saveItem1.addActionListener(this);
        saveItem2.addActionListener(this);
        saveItem3.addActionListener(this);
        saveItem4.addActionListener(this);
        loadItem0.addActionListener(this);
        loadItem1.addActionListener(this);
        loadItem2.addActionListener(this);
        loadItem3.addActionListener(this);
        loadItem4.addActionListener(this);

        //將每個菜單選項的項目加入選項中
        funtionMenu.add(changeImage);
        funtionMenu.add(replayItem);
        funtionMenu.add(reLoginItem);
        funtionMenu.add(closeItem);
        funtionMenu.add(saveJMenu);
        funtionMenu.add(loadJMenu);

        changeImage.add(girl);
        changeImage.add(animal);
        changeImage.add(sport);

        aboutMenu.add(accountItem);
        //將選項加入菜單
        jMenuBar.add(funtionMenu);
        jMenuBar.add(aboutMenu);
        //讀取存檔，修改菜單上的内容
        getGameInfo();

        this.setJMenuBar(jMenuBar);

        //設置關閉模式
        this.setDefaultCloseOperation(3);
    }

    public void getGameInfo() {
        //1.創建File對象表示所有存檔所在的文件夾
        File file = new File("C:\\Users\\Jason Kuo\\IdeaProjects\\puzzlegame\\save");
        //2.進入文件夾獲取到裡面所有的存檔文件
        File[] files = file.listFiles();
        //3.得到每一個存檔
        for (File f : files) {
            //f ：依次表示每一個存檔文件
            //獲取每一個存檔文件中的步數
            GameInfo gi = null;
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                gi = (GameInfo) ois.readObject();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //獲取到了步數
            int step = gi.getStep();

            //把存檔的步數同步到菜單當中
            //save0 ---> 0
            //save1 ---> 1
            //...

            //獲取存檔的文件名 save0.data
            String name = f.getName();
            //獲取存檔的序號（索引）
            int index = name.charAt(4) - '0';
            //修改菜單上所表示的文字信息
            saveJMenu.getItem(index).setText("Save" + index + "(" + step + ")Step");
            loadJMenu.getItem(index).setText("Load" + index + "(" + step + ")Step");
        }

    }


    private void initJFrame() {
        //設置視窗大小
        this.setSize(603, 680);
        //設置視窗標題
        this.setTitle("Puzzle Game v1.0");
        //設置視窗至頂
        this.setAlwaysOnTop(true);
        //設置視窗居中
        this.setLocationRelativeTo(null);
        //取消居中放置(layout佈局)
        this.setLayout(null);
        //加入鍵盤監聽功能
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //建立觀看完整圖片快捷鍵A
        //A:65
        int code = e.getKeyCode();
        if (code == 65) {
            this.getContentPane().removeAll();
            ImageIcon icon = new ImageIcon(path + "all.jpg");
            //創建jLable容器
            JLabel allImage = new JLabel(icon);
            allImage.setBounds(83, 134, 420, 420);
            this.getContentPane().add(allImage);
            //建立背景圖片
            ImageIcon backGroundImage = new ImageIcon("..\\puzzlegame\\image\\background.png");
            JLabel backGroundJLabel = new JLabel(backGroundImage);
            backGroundJLabel.setBounds(40, 30, 508, 560);
            this.getContentPane().add(backGroundJLabel);

            //刷新視窗
            this.getContentPane().repaint();

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //判定勝利後鎖定鍵盤
        if (victory()) {
            return;
        }
        //對方向進行判斷
        //左:37 上:38 右:39 下:40
        int code = e.getKeyCode();
        if (code == 37) {
            if (y == 3) {
                return;
            }
            date[x][y] = date[x][y + 1];
            date[x][y + 1] = 0;
            y++;
            //每移動一次計步器+1
            step++;
            initImage();
        } else if (code == 38) {
            if (x == 3) {
                return;
            }
            //x+1 = 將空白下方的圖片向上一格(實際上就是將空白圖片向下,下面的圖片向下)
            date[x][y] = date[x + 1][y];
            date[x + 1][y] = 0;
            x++;
            //每移動一次計步器+1
            step++;
            initImage();
        } else if (code == 39) {
            if (y == 0) {
                return;
            }
            date[x][y] = date[x][y - 1];
            date[x][y - 1] = 0;
            y--;
            //每移動一次計步器+1
            step++;
            initImage();
        } else if (code == 40) {
            if (x == 0) {
                return;
            }
            date[x][y] = date[x - 1][y];
            date[x - 1][y] = 0;
            x--;
            //每移動一次計步器+1
            step++;
            initImage();

        } else if (code == 65) {
            initImage();
        } else if (code == 87) {
            date = new int[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 0}
            };
            initImage();
        }
    }

    public boolean victory() {
        for (int i = 0; i < date.length; i++) {
            for (int j = 0; j < date[i].length; j++) {
                if (date[i][j] != win[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    //滑鼠點擊事件方法
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        //判斷功能選單
        if (obj == replayItem) {
            //計步器歸零
            step = 0;
            //重新打亂圖片順序
            initDate();
            //重新加載圖片
            initImage();
        } else if (obj == reLoginItem) {
            //隱藏遊戲視窗
            this.setVisible(false);
            //打開登錄介面
            new LoginJFrame();
        } else if (obj == closeItem) {
            //關閉遊戲
            System.exit(0);
        } else if (obj == accountItem) {
            //設置彈跳視窗
            JDialog jd = new JDialog();
            //插入二維碼圖片
            Properties pro = new Properties();
            try {
                FileInputStream fis = new FileInputStream("..\\puzzlegame\\game.properties");
                pro.load(fis);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            String pathStr =(String) pro.get("account");

            JLabel jLabel = new JLabel(new ImageIcon(pathStr));
            //設定表格大小
            jLabel.setBounds(0, 0, 258, 258);
            //將圖片加入彈跳視窗中
            jd.getContentPane().add(jLabel);
            //設定彈跳視窗大小
            jd.setSize(344, 344);
            //視窗至頂
            jd.setAlwaysOnTop(true);
            //視窗至中
            jd.setLocationRelativeTo(null);
            //設定該視窗不關閉無法操控其他視窗
            jd.setModal(true);
            //顯示彈跳視窗
            jd.setVisible(true);
        } else if (obj == girl) {
            //重新加載Sexy Girl圖片
            //重置計步器
            step = 0;
            //設置亂數
            int index = r.nextInt(13) + 1;
            //更改圖片路徑
            path = "..\\puzzlegame\\image\\girl\\girl" + index + "\\";
            //初始化數據
            initDate();
            //初始化圖片
            initImage();
        } else if (obj == animal) {
            //重新加載animal Girl圖片
            //重置計步器
            step = 0;
            //設置亂數
            int index = r.nextInt(8) + 1;
            //更改圖片路徑
            path = "..\\puzzlegame\\image\\animal\\animal" + index + "\\";
            //初始化數據
            initDate();
            //初始化圖片
            initImage();
        } else if (obj == sport) {
            //重新加載Sport Girl圖片
            //重置計步器
            step = 0;
            //設置亂數
            int index = r.nextInt(10) + 1;
            //更改圖片路徑
            path = "..\\puzzlegame\\image\\sport\\sport" + index + "\\";
            //初始化數據
            initDate();
            //初始化圖片
            initImage();
        } else if (obj == saveItem0 || obj == saveItem1 || obj == saveItem2 || obj == saveItem3 || obj == saveItem4) {
            JMenuItem item = (JMenuItem) obj;
            String itemName = item.getText();
            int index = itemName.charAt(4) - '0';

            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("C:\\Users\\Jason Kuo\\IdeaProjects\\puzzlegame\\save\\save" + index + ".txt"));
                GameInfo gi = new GameInfo(date, x, y, path, step);
                oos.writeObject(gi);
                oos.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            //修改一下存檔item上的展示訊息
            //存檔0(XX步)
            item.setText("Save" + index + "(" + step + "step)");
            //修改一下讀檔item上的展示訊息
            loadJMenu.getItem(index).setText("Save" + index + "(" + step + "step)");

        } else if (obj == loadItem0 || obj == loadItem1 || obj == loadItem2 || obj == loadItem3 || obj == loadItem4) {
            JMenuItem item = (JMenuItem) obj;
            String loadItem = item.getText();
            int itemIndex = loadItem.charAt(4) - '0';
            GameInfo gi = null;
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("C:\\Users\\Jason Kuo\\IdeaProjects\\puzzlegame\\save\\save" + itemIndex + ".txt"));
                gi = (GameInfo) ois.readObject();
                ois.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            date = gi.getData();
            x = gi.getX();
            y = gi.getY();
            step = gi.getStep();
            path = gi.getPath();

            initImage();
        }
    }
}
