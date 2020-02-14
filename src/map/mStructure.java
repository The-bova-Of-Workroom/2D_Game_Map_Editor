package map;


public class mStructure {
    public int COLS, ROWS, MAX, WIDTH, HEIGH;
    public char[] m;
    public int c, r;


    public mStructure(int cols, int rows, int width, int height) {
        WIDTH= width;
        HEIGH= height;
        COLS= cols;
        ROWS= rows;
        MAX= cols * ROWS;

        m= new char[MAX];
    }

    public void init() {
        c= r= 0;
        for (int n = 0; n < MAX; n++) m[n]= 0;
    }

    public void increaseWidthBtn() {
        COLS++;
        MAX= COLS * ROWS;

        char[] newm = new char[MAX];
        int newp = 0, oldp = 0;
        for (int rr = 0; rr < ROWS; rr++) {
            for (int cc = 0; cc < c; cc++) {
                newm[newp]= m[oldp];
                newp++;
                oldp++;
            }
            newp++;
            for (int cc = c + 1; cc < COLS; cc++) {
                newm[newp]= m[oldp];
                newp++;
                oldp++;
            }
        }
        if (c == COLS) c= COLS - 1;

        m= null;
        m= newm;
    }

    public void decreaseWidthBtn() {
        COLS--;
        MAX= COLS * ROWS;

        char[] newm = new char[MAX];
        int newp = 0, oldp = 0;
        for (int rr = 0; rr < ROWS; rr++) {
            for (int cc = 0; cc < c; cc++) {
                newm[newp]= m[oldp];
                newp++;
                oldp++;
            }
            oldp++;
            for (int cc = c; cc < COLS; cc++) {
                newm[newp]= m[oldp];
                newp++;
                oldp++;
            }
        }
        if (c == COLS) c= COLS - 1;

        m= null;
        m= newm;
    }

    public void HeightBtn(Boolean b) {
        int newp, oldp, rr;
        if (b) {
            ROWS--;
            rr= ROWS;
            newp= r * COLS;
            oldp= newp + COLS;
        }
        else {
                rr= ROWS;
                ROWS++;
                newp= (r + 1) * COLS;
                oldp= r * COLS;
        }

        MAX= COLS * ROWS;

        char[] newm = new char[MAX];
        int np = 0, op = 0;
        for (int rc = 0; rc < r; rc++) {
            for (int cc = 0; cc < COLS; cc++) {
                newm[np]= m[op];
                np++;
                op++;
            }
        }
        for (int rc = r; rc < rr; rc++) {
            for (int cc = 0; cc < COLS; cc++) {
                newm[newp]= m[oldp];
                newp++;
                oldp++;
            }
        }
        if (r == ROWS) r= ROWS - 1;

        m= null;
        m= newm;
    }

}
