package map;

import javax.imageio.ImageIO;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import java.io.*;

import tile.*;


public class mapComponent extends JComponent implements MouseListener, MouseMotionListener {
    public int cx, cy, ztSize, zmSize;
    public boolean isg, ish, isb;

    private int mOldX, mOldY;
    private mStructure msTile, msRoad, m;


    public mapComponent() {
        addMouseListener(this);
        addMouseMotionListener(this);


        ztSize= 24;
        zmSize= 24;// / 2;
        msTile = new mStructure(10, 10, ztSize * 2, ztSize * 2);
        msRoad = new mStructure(10, 10, zmSize * 2, zmSize * 2); // (10 * 2, 10 * 2, zmSize * 2, zmSize * 2);
        setTile();
        init();
        isg= true;
        ish= false;
        isb= false;
    }

    public void init() {
        cx= cy= 0;

        msTile.init();
        msRoad.init();

        repaint();
    }

    public void setTile() {
        m= msTile;

        setPreferredSize(new Dimension(m.COLS * m.WIDTH,m.ROWS * m.HEIGH));
        revalidate();
        repaint();
    }

    public void setMap()  {
        m= msRoad;
        ish= true;

        setPreferredSize(new Dimension(m.COLS * m.WIDTH,m.ROWS * m.HEIGH));
        revalidate();
        repaint();
    }

    @Override protected void paintComponent(Graphics g) {
        int y = 0, x = 0, c = 0;

        for (int n = 0; n < msTile.MAX; n++) {
            g.drawImage(tile.tsTile.b[(int) msTile.m[n]],x,y, msTile.WIDTH, msTile.HEIGH,null);

            x+= msTile.WIDTH;
            c= ++c % msTile.COLS;
            if (c == 0) { x= 0; y+= msTile.HEIGH;		}
        }

        if (ish) {
            y= x= c= 0;
            for (int n = 0; n < msRoad.MAX; n++) {
                g.drawImage(tile.tsRoad.b[(int) msRoad.m[n]],x,y, msRoad.WIDTH, msRoad.HEIGH,null);

                x+= msRoad.WIDTH;
                c= ++c % msRoad.COLS;
                if (c == 0) { x= 0; y+= msRoad.HEIGH;        }
            }
        }

        if (isg) {
            y= x= c= 0;
            for (int n = 0; n < m.MAX; n++) {
                g.drawRect(x,y,m.WIDTH,m.HEIGH);

                x+= m.WIDTH;
                c= ++c % m.COLS;
                if (c == 0) { x= 0; y+= m.HEIGH;        }
            }
        }

        g.setColor(Color.RED);
        x= m.COLS * m.WIDTH;
        y= m.ROWS * m.HEIGH;
        for (int n = 0; n < 4; n++) {
            g.drawLine(0,cy + n,x,cy + n);
            g.drawLine(cx + n,0,cx + n,y);
        }
        g.setColor(Color.BLACK);
    }

    public void zoomIn() {
        msTile.WIDTH+= ztSize;
        msTile.HEIGH+= ztSize;
        msRoad.WIDTH+= zmSize;
        msRoad.HEIGH+= zmSize;
        cx= cy= 0;

        setPreferredSize(new Dimension(m.COLS * m.WIDTH,m.ROWS * m.HEIGH));
        revalidate();
        repaint();
    }

    public void zoomOut() {
        msTile.WIDTH-= ztSize;
        msRoad.WIDTH-= zmSize;
        if (msTile.WIDTH < ztSize) { msTile.WIDTH= ztSize; msRoad.WIDTH= zmSize;      }
        msTile.HEIGH-= ztSize;
        msRoad.HEIGH-= zmSize;
        if (msTile.HEIGH < ztSize) { msTile.HEIGH= ztSize; msRoad.HEIGH= zmSize;      }

        cx= cy= 0;

        setPreferredSize(new Dimension(m.COLS * m.WIDTH,m.ROWS * m.HEIGH));
        revalidate();
        repaint();
    }

    public void zoomFull() {
        msTile.WIDTH= ztSize;
        msTile.HEIGH= ztSize;
        msRoad.WIDTH= zmSize;
        msRoad.HEIGH= zmSize;
        cx= cy= 0;

        setPreferredSize(new Dimension(m.COLS * m.WIDTH,m.ROWS * m.HEIGH));
        revalidate();
        repaint();
    }

    public void add() {
        int row = m.r;
        for (int r = 0; r < tile.m.length; r++) {
            int cc = m.c;
            for (int c = 0; c < tile.m[0].length; c++) {
                int n = cc + row * m.COLS;
                if (n < m.MAX) m.m[n]= (char)tile.m[r][c];
                //(ish) ? (char)this.t.dirs[r][c] : (char)t.dirs[r][c];
                cc++;
            }
            row++;
        }

        repaint();
    }

    public void add(int r, int s, int e, boolean is) {
        if (tile.ch == 1) {
            switch(tile.ch) {
                case 1	:
                            break;
                case 2	:
                case 3	:
                case 4	:
                            break;
                case 5	:
            }
        }

        for (int c = s; c < e; c++) {
            int n = (is) ? c + r * m.COLS : r + c * m.COLS;
            if (n < m.MAX && n >= 0) m.m[n]= (char)tile.ch;
        }

        repaint();
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    @Override public void mousePressed(MouseEvent e) {
        mOldX= e.getX();
        mOldY= e.getY();
        //if (e.getButton() == 1) { add(); isb= false;		}
        //if (e.getButton() == 3) isb= !isb;
    }

    @Override public void mouseReleased(MouseEvent e) {
        if (tile.ch == -44) { add(); return;		}

        if (mOldY / m.HEIGH == e.getY() / m.HEIGH) {
            int ss = mOldX / m.WIDTH, ee = (e.getX() / m.WIDTH);
            if (ss > ee) add(mOldY / m.HEIGH, ee, ss + 1, true);
            else
                    add(mOldY / m.HEIGH, ss, ee + 1, true);
        }
        else {
                int ss = mOldY / m.HEIGH, ee = (e.getY() / m.HEIGH);
                if (ss > ee) add(mOldX / m.WIDTH, ee, ss + 1, false);
                else
                        add(mOldX / m.WIDTH, ss, ee + 1, false);
        }
    }

    @Override public void mouseMoved(MouseEvent e) {
        int c = e.getX() / m.WIDTH, r = e.getY() / m.HEIGH;
        if (c < m.COLS && r < m.ROWS) {
            m.c= c;
            m.r= r;
            cx= c * m.WIDTH;
            cy= r * m.HEIGH;

            if (isb) add();
        }

        repaint();
    }

    @Override public void mouseDragged(MouseEvent e) {}

    public void grid() {
        isg= !isg;
        repaint();
    }

    public void hid() {
        ish= !ish;
        repaint();
    }

    public void saveFile(File path) {
        try {
                PrintWriter w = new PrintWriter(new BufferedWriter(new FileWriter(path)));

                w.print(msTile.COLS+","+ msTile.ROWS+'\n');
                w.write(msTile.m,0,msTile.MAX);

                w.print(msRoad.COLS+","+msRoad.ROWS+'\n');
                w.write(msRoad.m,0,msRoad.MAX);

                w.flush();
                w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openFile(File f) {
        try {
                BufferedReader r = new BufferedReader(new FileReader(f));
                StringBuffer sb = new StringBuffer();
                int ch;

                while ((ch= r.read()) != ',') sb.append((char)ch);
                msTile.COLS= Integer.parseInt(sb.toString());
                sb.delete(0,sb.length());

                while ((ch= r.read()) != '\n') sb.append((char)ch);
                msTile.ROWS= Integer.parseInt(sb.toString());

                msTile.MAX= msTile.COLS * msTile.ROWS;

                msTile.m= null;
                msTile.m= new char[msTile.MAX];
                r.read(msTile.m,0,msTile.MAX);


                sb.delete(0,sb.length());

                while ((ch= r.read()) != ',') sb.append((char)ch);
                msRoad.COLS= Integer.parseInt(sb.toString());
                sb.delete(0,sb.length());

                while ((ch= r.read()) != '\n') sb.append((char)ch);
                msRoad.ROWS= Integer.parseInt(sb.toString());

                msRoad.MAX= msRoad.COLS * msRoad.ROWS;

                msRoad.m= null;
                msRoad.m= new char[msRoad.MAX];
                r.read(msRoad.m,0,msRoad.MAX);

                r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPreferredSize(new Dimension(m.COLS * m.WIDTH,m.ROWS * m.HEIGH));
        revalidate();
        repaint();
    }

    public void saveImage(File f) {
        BufferedImage b= new BufferedImage(msTile.COLS * tile.tsTile.width, msTile.ROWS * tile.tsTile.heigh,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g= (Graphics2D)b.getGraphics();
        for (int r = 0; r < msTile.ROWS; r++)
            for (int c = 0; c < msTile.COLS; c++) {
                int ch = msTile.m[c + r * msTile.COLS];
                if (ch != 0) g.drawImage(tile.tsTile.b[ch],c * tile.tsTile.width,r * tile.tsTile.heigh,null);
            }

        try {
                OutputStream out = new FileOutputStream(f); // 파일로 출력하기 위해 파일출력 스트림 생성

                ImageIO.write(b,"PNG",out); // 이미지 출력, 이미지를 파일출력 스트림을 통해 PNG 타입으로 출력

                out.close(); // 출력스트림 닫기
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openFile2(File f) {
        try {
            BufferedReader r = new BufferedReader(new FileReader(f));
            StringBuffer sb = new StringBuffer();
            int ch;

            while ((ch= r.read()) != ',') sb.append((char)ch);
            msTile.COLS= Integer.parseInt(sb.toString());

            sb.delete(0,sb.length());
            while ((ch= r.read()) != '\n') sb.append((char)ch);
            msTile.ROWS= Integer.parseInt(sb.toString());
            msTile.MAX= msTile.COLS * msTile.ROWS;

            msTile.m= null;
            msTile.m= new char[msTile.MAX];
            r.read(msTile.m,0, msTile.MAX);

/*
            sb.delete(0,sb.length());
            while ((ch= r.read()) != ',') sb.append((char)ch);
            msRoad.COLS= Integer.parseInt(sb.toString());

            sb.delete(0,sb.length());
            while ((ch= r.read()) != '\n') sb.append((char)ch);
            msRoad.ROWS= Integer.parseInt(sb.toString());
            msRoad.MAX= msRoad.COLS * msRoad.ROWS;

            msRoad.m= null;
            msRoad.m= new char[msRoad.MAX];
            r.read(msRoad.m,0,msRoad.MAX);
*/
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPreferredSize(new Dimension(m.COLS * m.WIDTH,m.ROWS * m.HEIGH));
        revalidate();
        repaint();
    }

    public void increaseWidthBtn() {
        m.increaseWidthBtn();

        setPreferredSize(new Dimension(m.COLS * m.WIDTH,m.ROWS * m.HEIGH));
        revalidate();
        repaint();
    }


    public void decreaseWidthBtn() {
        m.decreaseWidthBtn();

        setPreferredSize(new Dimension(m.COLS * m.WIDTH,m.ROWS * m.HEIGH));
        revalidate();
        repaint();
    }

    public void HeightBtn(Boolean b) {
        m.HeightBtn(b);

        setPreferredSize(new Dimension(m.COLS * m.WIDTH,m.ROWS * m.HEIGH));
        revalidate();
        repaint();
    }
}
