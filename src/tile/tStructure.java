package tile;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.io.IOException;


public class tStructure {
    public BufferedImage[] b;
    public int width, heigh, max, row, col;


    public tStructure(int width, int height, int col, int row, String path) {
        this.width= width;
        this.heigh= height;
        this.row= row;
        this.col= col;

        max= row * col;

        b= new BufferedImage[max];

        try {
                BufferedImage b = ImageIO.read(getClass().getResource(path));
                for (int r = 0; r < row; r++)
                    for (int c = 0; c < col; c++) this.b[c + r * col]= b.getSubimage(c * width,r * heigh,width,heigh);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
