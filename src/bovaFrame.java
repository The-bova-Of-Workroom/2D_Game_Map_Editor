import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import map.*;
import tile.*;


public class bovaFrame extends JFrame implements ActionListener {
    public static int SCALE = 3;

    private JFileChooser chooser;

    private JToolBar toolBar;
    private JButton newBtn, openBtn, saveBtn, saveImage, openBtn2, saveBtn2, clearBtn;
    private JButton undoBtn, redoBtn;
    private JToggleButton gridBtn, hideBtn;
    private JToggleButton layerBtns[];
    private JButton zoomInBtn, zoomOutBtn, zoomFullBtn;
    private JButton increaseWidthBtn, decreaseWidthBtn, increaseHeightBtn, decreaseHeightBtn;

    private tileComponent tComponent;
    private mapComponent mComponent;


    public bovaFrame() {
        super("2D Game Map Editor");

        new tile();

        chooser= new JFileChooser("D:");

        toolBarInit();

        tComponent= new tileComponent();
        JScrollPane tScroll = new JScrollPane(tComponent);

        mComponent= new mapComponent();
        JScrollPane mScroll = new JScrollPane(mComponent);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,tScroll,mScroll);
        //split.setDividerLocation(280);
        //split.setTopComponent(tScroll);
        //split.setBottomComponent(mScroll);
        split.setOneTouchExpandable(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(split,BorderLayout.CENTER);
        setLayout(new BorderLayout());
        add(toolBar,BorderLayout.NORTH);
        add(panel,BorderLayout.CENTER);

        pack();
        setSize(new Dimension(700 * SCALE,500 * SCALE));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void saveImage() {
        FileNameExtensionFilter fef = new FileNameExtensionFilter("MAP PNG","png");
        chooser.setFileFilter(fef);

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) mComponent.saveImage(chooser.getSelectedFile());
    }

    public void saveFile() {
        FileNameExtensionFilter fef = new FileNameExtensionFilter("MAP m+","m+");
        chooser.setFileFilter(fef);

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) mComponent.saveFile(chooser.getSelectedFile());
    }

    public void openFile() {
        FileNameExtensionFilter fef = new FileNameExtensionFilter("MAP m+","m+");
        chooser.setFileFilter(fef);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) mComponent.openFile(chooser.getSelectedFile());
    }

    public void openFile2() {
        FileNameExtensionFilter fef = new FileNameExtensionFilter("MAP m+","m+");
        chooser.setFileFilter(fef);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) mComponent.openFile2(chooser.getSelectedFile());
    }

    @Override public void actionPerformed(ActionEvent e) {
        Object o= e.getSource();

        if (o == newBtn) mComponent.init();
        if (o == openBtn) openFile();
        if (o == saveBtn) saveFile();
        if (o == saveImage) saveImage();
        if (o == openBtn2) openFile2();
        if (o == saveBtn2); // saveFile2();
       // if (o == redoBtn) { mComponent.repaint(); mComponent.m.hori();		}

        if (o == gridBtn) mComponent.grid();
        if (o == hideBtn) mComponent.hid();

        if (o == layerBtns[0]) {
            mComponent.setTile();

            tile.ts = tile.tsTile;
            tComponent.init();
        }
        if (o == layerBtns[1]) {
            mComponent.setMap();

            tile.ts = tile.tsRoad;
            tComponent.init();
        }

        // if (o == layerBtns[2]) /// { mComponent.repaint(); mComponent.m.ch= 2;		}

        if (o == zoomInBtn) mComponent.zoomIn();
        if (o == zoomOutBtn) mComponent.zoomOut();
        if (o == zoomFullBtn) mComponent.zoomFull();

        if (o == increaseWidthBtn) mComponent.increaseWidthBtn();
        if (o == decreaseWidthBtn) mComponent.decreaseWidthBtn();
        if (o == increaseHeightBtn) mComponent.HeightBtn(false);
        if (o == decreaseHeightBtn) mComponent.HeightBtn(true);

    }

    public JButton makeBtn(String text, String icon, String tooltip) {
        JButton newBtn;

        try {
            ImageIcon i = new ImageIcon(getClass().getResource(icon));
            int w = 16 * SCALE, h = w;
            BufferedImage bi = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(i.getImage(),0,0,w,h,null);

            newBtn= new JButton(new ImageIcon(bi)); // i);
        } catch (Exception e) {
            newBtn= new JButton(text);
        }

        newBtn.setToolTipText(tooltip);
        newBtn.addActionListener(this);
		/*
			if(borderedButtons) {
				newBtn.setBorder(new LineBorder(Color.gray, 1, false));
		 	else if (compactToolbars) {
		*/
        newBtn.setBorder(new EmptyBorder(0, 0, 0,0)); //16 * SCALE,16 * SCALE));
		/* }
				newBtn.setBorderPainted(false);
		 */
        return newBtn;
    }

    public JToggleButton makeToggleBtn(String text, String icon, String tooltip) {
        JToggleButton newBtn;

        try {
            ImageIcon i = new ImageIcon(getClass().getResource(icon));
            int w = 16 * SCALE, h = w;
            BufferedImage bi = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(i.getImage(),0,0,w,h,null);

            newBtn= new JToggleButton(new ImageIcon(bi)); // i);
        } catch (Exception e) {
            newBtn= new JToggleButton(text);
        }

        newBtn.setToolTipText(tooltip);
        newBtn.addActionListener(this);
        newBtn.setBorder(new EmptyBorder(0,0,0,0));

        return newBtn;
    }

    public void toolBarInit() {
        toolBar= new JToolBar();
        toolBar.setEnabled(false);

        newBtn= makeBtn("New", "/toolbar/new.gif", "New mStructure");
        openBtn= makeBtn("Open", "/toolbar/open.gif", "Open mStructure");
        saveBtn= makeBtn("Save", "/toolbar/save.gif", "Save mStructure");
        saveImage= makeBtn("Image", "/toolbar/save.gif", "Save mStructure image");
        openBtn2= makeBtn("Open txt", "/toolbar/open.gif", "Open txt data");
        saveBtn2= makeBtn("Save2", "/toolbar/save.gif", "Save mStructure");
        clearBtn = makeBtn("Clear", "/toolbar/clear.gif", "Reset mStructure (Delete all tiles)");
        toolBar.add(newBtn);
        toolBar.add(openBtn);
        toolBar.add(saveBtn);
        toolBar.addSeparator();
        toolBar.addSeparator();
        toolBar.add(saveImage);
        toolBar.addSeparator();
        toolBar.addSeparator();
        toolBar.addSeparator();
        toolBar.addSeparator();
        toolBar.add(openBtn2);
        toolBar.add(saveBtn2);
        toolBar.addSeparator();
        toolBar.addSeparator();
        toolBar.addSeparator();

        toolBar.add(clearBtn);
        undoBtn = makeBtn("Undo", "/toolbar/undo.gif", "Undo");
        redoBtn = makeBtn("Redo", "/toolbar/redo.gif", "Redo");
        toolBar.add(undoBtn);
        toolBar.add(redoBtn);
        toolBar.addSeparator();
        toolBar.addSeparator();
        toolBar.addSeparator();

        gridBtn= makeToggleBtn("Grid", "/toolbar/grid.gif", "Show/Hide Grid");
        hideBtn= makeToggleBtn("Hide", "/toolbar/hide.gif", "Hide other layers");
        toolBar.add(gridBtn);
        toolBar.add(hideBtn);
        toolBar.addSeparator();
        toolBar.addSeparator();
        toolBar.addSeparator();

        ButtonGroup layerGroup= new ButtonGroup();
        layerBtns= new JToggleButton[3];
        layerBtns[2]= makeToggleBtn("Layer 3", "/toolbar/top.gif", "Edit the top layer");
        layerBtns[1]= makeToggleBtn("Layer 2", "/toolbar/mid.gif", "Edit the middle layer");
        layerBtns[0]= makeToggleBtn("Layer 1", "/toolbar/bottom.gif", "Edit the bottom layer");
        layerGroup.add(layerBtns[2]);
        layerGroup.add(layerBtns[1]);
        layerGroup.add(layerBtns[0]);
        toolBar.add(layerBtns[2]);
        toolBar.add(layerBtns[1]);
        toolBar.add(layerBtns[0]);
        toolBar.addSeparator();
        toolBar.addSeparator();
        toolBar.addSeparator();

        zoomInBtn= makeBtn("Zoom in", "/toolbar/zoomin.gif", "Zoom in");
        zoomOutBtn= makeBtn("Zoom out", "/toolbar/zoomout.gif", "Zoom out");
        zoomFullBtn= makeBtn("Zoom 100%", "/toolbar/zoomfull.gif", "Zoom to 100%");
        toolBar.add(zoomInBtn);
        toolBar.add(zoomOutBtn);
        toolBar.add(zoomFullBtn);
        toolBar.addSeparator();
        toolBar.addSeparator();
        toolBar.addSeparator();

        increaseWidthBtn= makeBtn("<-->", "/toolbar/increaseWidth.gif", "Increase field width");
        decreaseWidthBtn= makeBtn("-><-", "/toolbar/decreaseWidth.gif", "Decrease field width");
        increaseHeightBtn= makeBtn("\\/ +", "/toolbar/increaseHeight.gif", "Increase field height");
        decreaseHeightBtn= makeBtn("^^ -",  "/toolbar/decreaseHeight.gif", "Decrease field height");
        toolBar.add(increaseWidthBtn);
        toolBar.add(decreaseWidthBtn);
        toolBar.add(increaseHeightBtn);
        toolBar.add(decreaseHeightBtn);
        toolBar.setSize(new Dimension(800,50));

        gridBtn.setSelected(true);
        layerBtns[0].setSelected(true);
    }

}
