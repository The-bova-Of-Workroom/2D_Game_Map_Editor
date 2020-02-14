package tile;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class tileComponent extends JComponent implements MouseListener {
    private int XSIZE = 64, YSIZE = 64;

    private int mOldX, mOldY;

    private Rectangle[] rt;


    public tileComponent() {
        init();
        addMouseListener(this);
    }

    public void init() {
        tile.ch= 0;
        rt= null;

        rt= new Rectangle[tile.ts.max];
        int y = 0;
        for (int r = 0; r < tile.ts.row; r++) {
            int x = 0;
            for (int c = 0; c < tile.ts.col; c++) {
                rt[c + r * tile.ts.col]= new Rectangle(x,y,x + XSIZE,y + YSIZE);
                x+= XSIZE;
            }
            y+= YSIZE;
        }

        setPreferredSize(new Dimension(tile.ts.col * XSIZE, tile.ts.row * YSIZE));
        repaint();
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int n = 0; n < tile.ts.max; n++) {
            g.drawImage(tile.ts.b[n],rt[n].x,rt[n].y,XSIZE,YSIZE,null);
            g.drawRect(rt[n].x,rt[n].y,XSIZE,YSIZE);
        }
    }

    @Override public void mouseClicked(MouseEvent e) {	// Frame에서 mouse버튼이 클릭되고 놓아졌을 때(제자리에서! 마우스 누르고 -> 이동 -> 마우스 때면 동작안함!)
        //repaint();

        tile.ch= 0;
        for (int n = 0; n < tile.ts.max; n++)
            if (e.getX() > rt[n].x && e.getX() < rt[n].width && e.getY() > rt[n].y && e.getY() < rt[n].height) {
                tile.ch= n;
                break;
            }

        //System.out.println("dir="+t.dir);
    }

    @Override public void mouseEntered(MouseEvent e) {} // Frame안으로 마우스가 들어 올때 발생하는 이벤트
    @Override public void mouseExited(MouseEvent e) {}	// Frame밖으로 마우스가 나갈때 발생하는 이벤트
    @Override public void mousePressed(MouseEvent e) {	// 마우스 버튼이 눌려졌을 때
        mOldX= e.getX();
        mOldY= e.getY();
    }

    @Override public void mouseReleased(MouseEvent e) { // 마우스 버튼을 놓았을 때(마우스 누르고 -> 이동 -> 마우스 놓아도 동작합니다.)
        int x = e.getX(), y = e.getY(), temp;

        if (mOldX > x) { temp= mOldX; mOldX= x; x= temp;	}
        if (mOldY > y) { temp= mOldY; mOldY= y; y= temp;	}

        tile.m= null;
        tile.m= new int[((y - mOldY) / YSIZE) + 1][((x - mOldX) / XSIZE) + 1];
        x= mOldX / XSIZE;
        for (int r = 0; r < tile.m.length; r++) {
            tile.ch= x + (mOldY / YSIZE) * tile.ts.col;
            for (int c = 0; c < tile.m[0].length && tile.ch < tile.ts.max; c++) tile.m[r][c]= tile.ch++;
            mOldY+= YSIZE;
        }

        tile.ch= -44;
    }
}
