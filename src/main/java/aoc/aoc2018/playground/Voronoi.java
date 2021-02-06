package aoc.aoc2018.playground;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Voronoi extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 4599582538429916032L;
    static double p = 3;
    static BufferedImage I;
    static int px[], py[], color[], cells = 50, size = 1000;

    public Voronoi() throws IOException  {
        super("Voronoi Diagram");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int n = 0;
        Random rand = new Random();
        px = new int[cells];
        py = new int[cells];
        color = new int[cells];

        String s;
        int row = 0;
        int size_x = 0;
        int size_y = 0;
        // read input data
        while( (s = br.readLine()) != null){
            String[] chunks = s.split(", ");
            px[row] = Integer.valueOf(chunks[0]);
            py[row] = Integer.valueOf(chunks[1]);
            color[row] = rand.nextInt(16777215);
            size_x = Math.max(px[row], size_x);
            size_y = Math.max(py[row], size_y);
            row += 1;
        }
        
        size_x += 50;
        size_y += 50;

        setBounds(0, 0, size_x, size_y);
        I = new BufferedImage(size_x, size_y, BufferedImage.TYPE_INT_RGB);

        // for (int i = 0; i < cells; i++) {
        //     px[i] = rand.nextInt(size);
        //     py[i] = rand.nextInt(size);
        //     color[i] = rand.nextInt(16777215);

        // }

        for (int x = 0; x < size_x; x++) {
            for (int y = 0; y < size_y; y++) {
                n = 0;
                for (byte i = 0; i < cells; i++) {
                    if (distance(px[i], x, py[i], y) < distance(px[n], x, py[n], y)) {
                        n = i;

                    }
                }
                I.setRGB(x, y, color[n]);

            }
        }

        Graphics2D g = I.createGraphics();
        g.setColor(Color.BLACK);
        for (int i = 0; i < cells; i++) {
            g.fill(new Ellipse2D.Double(px[i] - 2.5, py[i] - 2.5, 5, 5));
        }

        try {
            ImageIO.write(I, "png", new File("target/day6_voronoi.png"));
        } catch (IOException e) {

        }

    }

    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    static double distance(int x1, int x2, int y1, int y2) {
        double d;
        //d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)); // Euclidian
        d = Math.abs(x1 - x2) + Math.abs(y1 - y2); // Manhattan
        // d = Math.pow(Math.pow(Math.abs(x1 - x2), p) + Math.pow(Math.abs(y1 - y2), p),
        // (1 / p)); // Minkovski
        return d;
    }

    public static void main(String[] args) throws IOException {
        new Voronoi().setVisible(true);
    }
}