package br.ufg.inf.graphic;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class G extends JPanel {
    ArrayList<Integer> coordinates;
    int mar;

    public G() {
        coordinates = new ArrayList();
        mar = 50;
    }

    public ArrayList getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList coordinates) {
        this.coordinates = coordinates;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g1 = (Graphics2D) g;
        g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        g1.draw(new Line2D.Double(mar, mar, mar, height - mar));
        g1.draw(new Line2D.Double(mar, height - mar, width - mar, height - mar));
        double x = (double) (width - 2 * mar) / (coordinates.size() - 1);
        double scale = (double) (height - 2 * mar) / getMax();
        g1.setPaint(Color.BLUE);

        for (int i = 0; i < coordinates.size(); i++) {
            double x1 = mar + i * x;
            double y1 = height - mar - scale * coordinates.get(i);
            g1.fill(new Ellipse2D.Double(x1 - 2, y1 - 2, 4, 4));
        }
    }

    private int getMax() {
        int max = -Integer.MAX_VALUE;
        for (int i = 0; i < coordinates.size(); i++) {
            if (coordinates.get(i) > max) {
                max = (int) coordinates.get(i);
            }

        }
        return max;
    }
}
