package graphics;

import env.Environment;
import math.Line;
import math.Ray;
import math.RayLineIntersection;
import math.vec2;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Scene extends Canvas {
    protected Environment env;
    private JFrame container;
    private vec2 offset;

    public Scene(JFrame frame) {
        env = new Environment();
        this.container = frame;
        offset = new vec2(-720/2,-720/2);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                env.step();
                repaint();
            }
        };

        Timer timer = new Timer("timer");
        long period = (long) 1000.0 / 60;
        timer.scheduleAtFixedRate(timerTask, 1000, period);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(-offset.getX(), -offset.getY());
        g2d.scale(0.2,-0.2);
        env.paint(g2d);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ZSUZSI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 720);
        frame.getContentPane().add(new Scene(frame));
        frame.setVisible(true);

        /*Line l = new Line(new vec2(10,-5), new vec2(10,5));
        Ray r = new Ray(vec2.ZERO, new vec2(1,0));
        RayLineIntersection is = l.castRay(r);
        System.out.println(is.getIntersectionPoint().getX() + " " + is.getIntersectionPoint().getY());*/
    }
}
