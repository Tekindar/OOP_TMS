package hk.edu.polyu.comp.comp2021.tms.Reference;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileReader;

public class PlayChessFF {

    public static String CheckMoves(int[][] codeBoard) {
        String PosibleMoves = "";
        for(int i=0;i<4;i++) {
            for(int o=0;o<4;o++) {
                if(codeBoard[i][o]==2) {
                    try {
                        if(codeBoard[i-1][o-1] == 1) {//判断左前是否有黑兵
                            PosibleMoves+="x"+i+o+(i-1)+(o-1);
                        }
                    }catch(Exception e){}
                    try {
                        if(codeBoard[i-1][o+1] == 1) {//判断右前是否有黑兵
                            PosibleMoves+="x"+i+o+(i-1)+(o+1);
                        }
                    }catch(Exception e){}
                    try {
                        if(codeBoard[i-1][o] == 0) {//判断前方是否为空
                            PosibleMoves+="g"+i+o+(i-1)+o;
                        }
                    }catch(Exception e){}
                }
            }
        }
        return PosibleMoves;
    }

    public static String CheckMoves_b(int[][] codeBoard) {
        String PosibleMoves = "";
        for(int i=0;i<4;i++) {
            for(int o=0;o<4;o++) {
                if(codeBoard[i][o]==1) {
                    try {
                        if(codeBoard[i+1][o-1] == 2) {//判断左后是否有白兵
                            PosibleMoves+="x"+i+o+(i+1)+(o-1);
                        }
                    }catch(Exception e){}
                    try {
                        if(codeBoard[i+1][o+1] == 2) {//判断右后是否有白兵
                            PosibleMoves+="x"+i+o+(i+1)+(o+1);
                        }
                    }catch(Exception e){}
                    try {
                        if(codeBoard[i+1][o] == 0) {//判断后方是否为空
                            PosibleMoves+="g"+i+o+(i+1)+o;
                        }
                    }catch(Exception e){}
                }
            }
        }
        return PosibleMoves;
    }

    private static JPanel createPanel(boolean a) {
        // 创建一个 JPanel, 使用 1 行 1 列的网格布局
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // 设置容器的位置和宽高
        panel.setBounds(0, 0, 790, 820);

        // 设置 panel 的背景
        panel.setOpaque(a);

        return panel;
    }

    static void printBoard(int[][] a) {
        for(int i=0;i<4;i++) {
            for(int o=0;o<4;o++) {
                if(a[i][o]==1) {
                    System.out.print("B ");
                }else if(a[i][o]==2) {
                    System.out.print("W ");
                }else {
                    System.out.print("O ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        double c = 1.0;
        int[][] codeBoard = {{1,1,1,1},{0,0,0,0},{0,0,0,0},{2,2,2,2}};
        String boardString = "";
        Double bestRatio = 0.0;
        int best = -1;
        int end = 0;
        JFrame f = new JFrame();
        f.setSize(790, 820);
        f.setLayout(null);

        JLayeredPane layeredPane = new JLayeredPane();

        // 层数: 100
        JPanel LabelLayer = createPanel(true);
        layeredPane.add(LabelLayer, 3, 2);

        // 层数: 200, 层内位置: 0（层内顶部）
        JPanel ButtonLayer = createPanel(false);
        layeredPane.add(ButtonLayer, 3, 0);

        JPanel OptionLayer = createPanel(false);
        layeredPane.add(OptionLayer, 3, 1);

        f.setLayeredPane(layeredPane);


        int[] Posw1 = {30, 600};
        int[] Posw2 = {220,600};
        int[] Posw3 = {410,600};
        int[] Posw4 = {600,600};

        JLabel temp = new JLabel("0");
        JButton bt1= new JButton("♙");
        JButton bt2= new JButton("♙");
        JButton bt3= new JButton("♙");
        JButton bt4= new JButton("♙");
        JButton[] whiteSets = {bt1,bt2,bt3,bt4};
        bt1.setLocation(Posw1[0],Posw1[1]);
        bt2.setLocation(Posw2[0],Posw2[1]);
        bt3.setLocation(Posw3[0],Posw3[1]);
        bt4.setLocation(Posw4[0],Posw4[1]);
        bt1.setBorder(BorderFactory.createEmptyBorder());
        bt2.setBorder(BorderFactory.createEmptyBorder());
        bt3.setBorder(BorderFactory.createEmptyBorder());
        bt4.setBorder(BorderFactory.createEmptyBorder());
        bt1.setSize(160,160);
        bt2.setSize(160,160);
        bt3.setSize(160,160);
        bt4.setSize(160,160);
        JLabel b1 = new JLabel("♟",0);
        JLabel b2 = new JLabel("♟",0);
        JLabel b3 = new JLabel("♟",0);
        JLabel b4 = new JLabel("♟",0);
        JLabel[] blackSets = {b1,b2,b3,b4};
        JLabel lb1 = new JLabel("",0);
        JLabel lb2 = new JLabel("",0);
        JLabel lb3 = new JLabel("",0);
        JLabel lb4 = new JLabel("",0);
        JLabel lb5 = new JLabel("",0);
        JLabel lb6 = new JLabel("",0);
        JLabel lb7 = new JLabel("",0);
        JLabel lb8 = new JLabel("",0);
        JLabel lb9 = new JLabel("",0);
        JLabel lb10 = new JLabel("",0);
        JLabel lb11 = new JLabel("",0);
        JLabel lb12 = new JLabel("",0);
        JLabel lb13 = new JLabel("",0);
        JLabel lb14 = new JLabel("",0);
        JLabel lb15 = new JLabel("",0);
        JLabel lb16 = new JLabel("",0);
        bt1.setFont(new Font("Times New Romans", Font.BOLD, 60));
        bt2.setFont(new Font("Times New Romans", Font.BOLD, 60));
        bt3.setFont(new Font("Times New Romans", Font.BOLD, 60));
        bt4.setFont(new Font("Times New Romans", Font.BOLD, 60));
        b1.setFont(new Font("Times New Romans", Font.BOLD, 60));
        b2.setFont(new Font("Times New Romans", Font.BOLD, 60));
        b3.setFont(new Font("Times New Romans", Font.BOLD, 60));
        b4.setFont(new Font("Times New Romans", Font.BOLD, 60));


        b1.setSize(160,160);
        b2.setSize(160,160);
        b3.setSize(160,160);
        b4.setSize(160,160);
        lb1.setSize(160,160);
        lb2.setSize(160,160);
        lb3.setSize(160,160);
        lb4.setSize(160,160);
        lb5.setSize(160,160);
        lb6.setSize(160,160);
        lb7.setSize(160,160);
        lb8.setSize(160,160);
        lb9.setSize(160,160);
        lb10.setSize(160,160);
        lb11.setSize(160,160);
        lb12.setSize(160,160);
        lb13.setSize(160,160);
        lb14.setSize(160,160);
        lb15.setSize(160,160);
        lb16.setSize(160,160);

        lb1.setLocation(30,  30);
        lb2.setLocation(220, 30);
        lb3.setLocation(410, 30);
        lb16.setLocation(600,30);
        lb7.setLocation( 30, 220);
        lb8.setLocation(220,220);
        lb9.setLocation(410,220);
        lb14.setLocation(600,220);
        lb4.setLocation(30, 410);
        lb5.setLocation(220,410);
        lb6.setLocation(410,410);
        lb13.setLocation(600,410);
        lb10.setLocation(30, 600);
        lb11.setLocation(220,600);
        lb12.setLocation(410,600);
        lb15.setLocation(600,600);

        b1.setLocation(30,  30);
        b2.setLocation(220, 30);
        b3.setLocation(410, 30);
        b4.setLocation(600, 30);


        lb7.setOpaque(true);
        lb8.setOpaque(true);
        lb9.setOpaque(true);
        lb1.setOpaque(true);
        lb4.setOpaque(true);
        lb2.setOpaque(true);
        lb3.setOpaque(true);
        lb5.setOpaque(true);
        lb6.setOpaque(true);
        lb10.setOpaque(true);
        lb11.setOpaque(true);
        lb12.setOpaque(true);
        lb13.setOpaque(true);
        lb14.setOpaque(true);
        lb15.setOpaque(true);
        lb16.setOpaque(true);

        lb1.setBackground(Color.white);
        lb2.setBackground(Color.white);
        lb3.setBackground(Color.white);
        lb4.setBackground(Color.white);
        lb5.setBackground(Color.white);
        lb6.setBackground(Color.white);
        lb7.setBackground(Color.white);
        lb8.setBackground(Color.white);
        lb9.setBackground(Color.white);
        lb10.setBackground(Color.white);
        lb11.setBackground(Color.white);
        lb12.setBackground(Color.white);
        lb13.setBackground(Color.white);
        lb14.setBackground(Color.white);
        lb15.setBackground(Color.white);
        lb16.setBackground(Color.white);

        ButtonLayer.add(b1);
        ButtonLayer.add(b2);
        ButtonLayer.add(b3);
        ButtonLayer.add(b4);
        ButtonLayer.add(bt4);
        LabelLayer.add(lb1);
        LabelLayer.add(lb2);
        LabelLayer.add(lb3);
        LabelLayer.add(lb4);
        LabelLayer.add(lb5);
        LabelLayer.add(lb6);
        LabelLayer.add(lb7);
        LabelLayer.add(lb8);
        LabelLayer.add(lb9);
        LabelLayer.add(lb11);
        LabelLayer.add(lb10);
        LabelLayer.add(lb12);
        LabelLayer.add(lb13);
        LabelLayer.add(lb14);
        LabelLayer.add(lb15);
        LabelLayer.add(lb16);
        ButtonLayer.add(bt1);
        ButtonLayer.add(bt2);
        ButtonLayer.add(bt3);
        f.setVisible(true);

        bt1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OptionLayer.removeAll();
                if(Integer.parseInt(temp.getText())%2==0) {
                    String moves = CheckMoves(codeBoard);
                    System.out.println(moves);
                    for(int i=0;i<moves.length()/5;i++) {
                        if((bt1.getLocation().x-30)/190 == Integer.parseInt(""+moves.charAt(5*i+2))&&(bt1.getLocation().y-30)/190 == Integer.parseInt(""+moves.charAt(5*i+1))) {
                            JButton nbt = new JButton();
                            nbt.setSize(160,160);
                            nbt.setLocation(Integer.parseInt(""+moves.charAt(5*i+4))*190+30,Integer.parseInt(""+moves.charAt(5*i+3))*190+30);
                            nbt.setOpaque(true);
                            nbt.setBackground(new Color(244,184,115));
                            nbt.setBorder(BorderFactory.createEmptyBorder());
                            nbt.setOpaque(true);
                            OptionLayer.add(nbt);
                            f.repaint();
                            System.out.println(nbt.getLocation());

                            nbt.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    OptionLayer.removeAll();
                                    codeBoard[(bt1.getLocation().y-30)/190][(bt1.getLocation().x-30)/190] = 0;
                                    if(codeBoard[(nbt.getLocation().y-30)/190][(nbt.getLocation().x-30)/190] == 1) {
                                        for(int i=0;i<4;i++) {
                                            if(blackSets[i].getLocation().x==nbt.getLocation().x&&blackSets[i].getLocation().y==nbt.getLocation().y) {
                                                Container parent = blackSets[i].getParent();
                                                parent.remove(blackSets[i]);
                                                blackSets[i].setLocation(0,0);
                                                parent.validate();
                                                parent.repaint();
                                                f.repaint();
                                            }
                                        }
                                    }
                                    codeBoard[(nbt.getLocation().y-30)/190][(nbt.getLocation().x-30)/190] = 2;
                                    nbt.setOpaque(false);
                                    bt1.setLocation(nbt.getLocation());
                                    for(int i=0;i<4;i++) {
                                        if(codeBoard[0][i]==2) {
                                            JOptionPane.showConfirmDialog(null, "You win");
                                            System.exit(0);
                                        }else if(CheckMoves(codeBoard).length()==0) {
                                            JOptionPane.showConfirmDialog(null, "It's a tie");
                                            System.exit(0);
                                        }
                                    }

                                    temp.setText(""+(Integer.parseInt(temp.getText())+1));
                                }
                            });
                        }
                    }
                }
            }
        });
        bt2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OptionLayer.removeAll();
                if(Integer.parseInt(temp.getText())%2==0) {
                    String moves = CheckMoves(codeBoard);
                    System.out.println(moves);
                    for(int i=0;i<moves.length()/5;i++) {
                        if((bt2.getLocation().x-30)/190 == Integer.parseInt(""+moves.charAt(5*i+2))&&(bt2.getLocation().y-30)/190 == Integer.parseInt(""+moves.charAt(5*i+1))) {
                            JButton nbt = new JButton();
                            nbt.setSize(160,160);
                            nbt.setLocation(Integer.parseInt(""+moves.charAt(5*i+4))*190+30,Integer.parseInt(""+moves.charAt(5*i+3))*190+30);
                            nbt.setOpaque(true);
                            nbt.setBackground(new Color(244,184,115));
                            nbt.setBorder(BorderFactory.createEmptyBorder());
                            nbt.setOpaque(true);
                            OptionLayer.add(nbt);
                            f.repaint();
                            System.out.println(nbt.getLocation());

                            nbt.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    OptionLayer.removeAll();
                                    codeBoard[(bt2.getLocation().y-30)/190][(bt2.getLocation().x-30)/190] = 0;
                                    if(codeBoard[(nbt.getLocation().y-30)/190][(nbt.getLocation().x-30)/190] == 1) {
                                        for(int i=0;i<4;i++) {
                                            if(blackSets[i].getLocation().x==nbt.getLocation().x&&blackSets[i].getLocation().y==nbt.getLocation().y) {
                                                System.out.println(blackSets[i].getName());
                                                Container parent = blackSets[i].getParent();
                                                parent.remove(blackSets[i]);
                                                blackSets[i].setLocation(0,0);
                                                parent.validate();
                                                parent.repaint();
                                                f.repaint();
                                            }
                                        }
                                    }
                                    codeBoard[(nbt.getLocation().y-30)/190][(nbt.getLocation().x-30)/190] = 2;
                                    nbt.setOpaque(false);
                                    bt2.setLocation(nbt.getLocation());
                                    for(int i=0;i<4;i++) {
                                        if(codeBoard[0][i]==2) {
                                            JOptionPane.showConfirmDialog(null, "You win");
                                            System.exit(0);
                                        }else if(CheckMoves(codeBoard).length()==0) {
                                            JOptionPane.showConfirmDialog(null, "It's a tie");
                                            System.exit(0);
                                        }
                                    }

                                    temp.setText(""+(Integer.parseInt(temp.getText())+1));
                                }
                            });
                        }
                    }
                }
            }
        });
        bt3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OptionLayer.removeAll();
                if(Integer.parseInt(temp.getText())%2==0) {
                    String moves = CheckMoves(codeBoard);
                    System.out.println(moves);
                    for(int i=0;i<moves.length()/5;i++) {
                        if((bt3.getLocation().x-30)/190 == Integer.parseInt(""+moves.charAt(5*i+2))&&(bt3.getLocation().y-30)/190 == Integer.parseInt(""+moves.charAt(5*i+1))) {
                            JButton nbt = new JButton();
                            nbt.setSize(160,160);
                            nbt.setLocation(Integer.parseInt(""+moves.charAt(5*i+4))*190+30,Integer.parseInt(""+moves.charAt(5*i+3))*190+30);
                            nbt.setOpaque(true);
                            nbt.setBackground(new Color(244,184,115));
                            nbt.setBorder(BorderFactory.createEmptyBorder());
                            nbt.setOpaque(true);
                            OptionLayer.add(nbt);
                            f.repaint();
                            System.out.println(nbt.getLocation());

                            nbt.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    OptionLayer.removeAll();
                                    codeBoard[(bt3.getLocation().y-30)/190][(bt3.getLocation().x-30)/190] = 0;
                                    if(codeBoard[(nbt.getLocation().y-30)/190][(nbt.getLocation().x-30)/190] == 1) {
                                        for(int i=0;i<4;i++) {
                                            if(blackSets[i].getLocation().x==nbt.getLocation().x&&blackSets[i].getLocation().y==nbt.getLocation().y) {
                                                Container parent = blackSets[i].getParent();
                                                parent.remove(blackSets[i]);
                                                blackSets[i].setLocation(0,0);
                                                parent.validate();
                                                parent.repaint();
                                                f.repaint();
                                            }
                                        }
                                    }
                                    codeBoard[(nbt.getLocation().y-30)/190][(nbt.getLocation().x-30)/190] = 2;
                                    nbt.setOpaque(false);
                                    bt3.setLocation(nbt.getLocation());
                                    for(int i=0;i<4;i++) {
                                        if(codeBoard[0][i]==2) {
                                            JOptionPane.showConfirmDialog(null, "You win");
                                            System.exit(0);
                                        }else if(CheckMoves(codeBoard).length()==0) {
                                            JOptionPane.showConfirmDialog(null, "It's a tie");
                                            System.exit(0);
                                        }
                                    }

                                    temp.setText(""+(Integer.parseInt(temp.getText())+1));
                                }
                            });
                        }
                    }
                }
            }
        });
        bt4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OptionLayer.removeAll();
                if(Integer.parseInt(temp.getText())%2==0) {
                    String moves = CheckMoves(codeBoard);
                    System.out.println(moves);
                    for(int i=0;i<moves.length()/5;i++) {
                        if((bt4.getLocation().x-30)/190 == Integer.parseInt(""+moves.charAt(5*i+2))&&(bt4.getLocation().y-30)/190 == Integer.parseInt(""+moves.charAt(5*i+1))) {
                            System.out.println(i);
                            JButton nbt = new JButton();
                            nbt.setSize(160,160);
                            nbt.setLocation(Integer.parseInt(""+moves.charAt(5*i+4))*190+30,Integer.parseInt(""+moves.charAt(5*i+3))*190+30);
                            nbt.setOpaque(true);
                            nbt.setBackground(new Color(244,184,115));
                            nbt.setBorder(BorderFactory.createEmptyBorder());
                            nbt.setOpaque(true);
                            OptionLayer.add(nbt);
                            f.repaint();
                            System.out.println(nbt.getLocation());

                            nbt.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    OptionLayer.removeAll();
                                    codeBoard[(bt4.getLocation().y-30)/190][(bt4.getLocation().x-30)/190] = 0;
                                    if(codeBoard[(nbt.getLocation().y-30)/190][(nbt.getLocation().x-30)/190] == 1) {
                                        for(int i=0;i<4;i++) {
                                            if(blackSets[i].getLocation().x==nbt.getLocation().x&&blackSets[i].getLocation().y==nbt.getLocation().y) {
                                                Container parent = blackSets[i].getParent();
                                                parent.remove(blackSets[i]);
                                                blackSets[i].setLocation(0,0);
                                                parent.validate();
                                                parent.repaint();
                                                f.repaint();
                                            }
                                        }
                                    }
                                    codeBoard[(nbt.getLocation().y-30)/190][(nbt.getLocation().x-30)/190] = 2;
                                    nbt.setOpaque(false);
                                    bt4.setLocation(nbt.getLocation());
                                    for(int i=0;i<4;i++) {
                                        if(codeBoard[0][i]==2) {
                                            JOptionPane.showConfirmDialog(null, "You win");
                                            System.exit(0);
                                        }else if(CheckMoves(codeBoard).length()==0) {
                                            JOptionPane.showConfirmDialog(null, "It's a tie");
                                            System.exit(0);
                                        }
                                    }

                                    temp.setText(""+(Integer.parseInt(temp.getText())+1));
                                }
                            });
                        }
                    }
                }
            }
        });
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        while(end==0) {
            bestRatio = -1.0;
            if(Integer.parseInt(temp.getText())%2==1) {
                boardString = "";
                String moveChosen = "";
                for(int i=0;i<4;i++) {
                    for(int o=0;o<4;o++) {
                        boardString += ""+codeBoard[i][o];
                    }
                }
                String temp2 = CheckMoves_b(codeBoard);
                if(temp2.length()==0) {
                    JOptionPane.showConfirmDialog(null, "It's a tie");
                    System.exit(0);
                }
                for(int i=0;i<temp2.length()/5;i++) {
                    String temp3 = "";
                    int ww=0;
                    int dd=0;
                    int ll=0;
                    try {
                        for(int o=0;o<5;o++) {
                            temp3+=""+temp2.charAt(i*5+o);
                        }
                        System.out.println("/Users/tonyfu/Desktop/HexapawnFF/"+boardString+temp3+".txt");
                        FileReader fd = new FileReader("/Users/tonyfu/Desktop/HexapawnFF/"+boardString+temp3+".txt");
                        int p=fd.read();
                        while(p!=-1){
                            if((char)p==87) {
                                ww++;
                            }else if((char)p==76) {
                                ll++;
                            }else if((char)p==68) {
                                dd++;
                            }
                            p=fd.read();
                        }
                        fd.close();
                        if(c*ww/(ww+ll+dd)>bestRatio) {
                            best = i;
                            bestRatio = c*ww/(ww+ll+dd);
                            moveChosen = temp3;
                        }
                    }catch(Exception e) {}
                }
                for(int t=0;t<blackSets.length;t++) {
                    if(blackSets[t].getLocation().x==(Integer.parseInt(""+moveChosen.charAt(2))*190+30)&&blackSets[t].getLocation().y==(Integer.parseInt(""+moveChosen.charAt(1))*190+30)) {
                        codeBoard[(blackSets[t].getLocation().y-30)/190][(blackSets[t].getLocation().x-30)/190] = 0;
                        if(codeBoard[Integer.parseInt(""+moveChosen.charAt(3))][Integer.parseInt(""+moveChosen.charAt(4))] == 2) {
                            for(int i=0;i<4;i++) {
                                if(whiteSets[i].getLocation().x==(Integer.parseInt(""+moveChosen.charAt(4))*190+30)&&whiteSets[i].getLocation().y==(Integer.parseInt(""+moveChosen.charAt(3))*190+30)) {
                                    System.out.println(whiteSets[i].getLocation());
                                    Container parent = whiteSets[i].getParent();
                                    parent.remove(whiteSets[i]);
                                    whiteSets[i].setLocation(0,0);
                                    parent.validate();
                                    parent.repaint();
                                    f.repaint();
                                }
                            }
                        }
                        codeBoard[Integer.parseInt(""+moveChosen.charAt(3))][Integer.parseInt(""+moveChosen.charAt(4))] = 1;
                        blackSets[t].setLocation(Integer.parseInt(""+moveChosen.charAt(4))*190+30,Integer.parseInt(""+moveChosen.charAt(3))*190+30);
                        f.repaint();
                    }
                }
                for(int i=0;i<4;i++) {
                    if(codeBoard[3][i]==1) {
                        JOptionPane.showConfirmDialog(null, "You loss");
                        System.exit(0);
                    }else if(CheckMoves(codeBoard).length()==0) {
                        JOptionPane.showConfirmDialog(null, "It's a tie");
                        System.exit(0);
                    }
                }

                temp.setText(""+(Integer.parseInt(temp.getText())+1));
            }
        }



    }

}
