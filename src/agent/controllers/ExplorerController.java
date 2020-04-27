package agent.controllers;

import agent.Agent;
import agent.IController;
import math.vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ExplorerController implements IController {

    private JFrame frame;
    private JPanel panel;
    private BufferedImage canvas;
    private double sensorDist;
    private static final double TRESHOLD = 150;
    private double sensorSpread;

    private vec2 toCanvasSpace(vec2 v) {
        return v;
    }

    private vec2 toWorldSpace(vec2 v) {
        return v;
    }

    private void addPoint(vec2 point) {
        Graphics2D g = (Graphics2D)canvas.getGraphics();
        g.translate(300,300);
        g.scale(0.1,-0.1);
        g.setColor(Color.white);
        g.fillRect((int)point.getX(), (int)point.getY(), 30,30);
        g.dispose();
    }

    private void clearCanvas() {
        Graphics g = canvas.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0,600,600);
    }

    public ExplorerController(double sensorDist, double sensorSpread) {
        this.sensorDist = sensorDist;
        this.sensorSpread = sensorSpread;
        frame = new JFrame("PointCloud");
        frame.setLocation(800,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas = new BufferedImage(600,600, BufferedImage.TYPE_INT_RGB);
        clearCanvas();
        panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.drawImage(canvas,0,0, null);
            }
        };
        frame.add(panel);
        frame.setSize(600,600);
        frame.setVisible(true);
    }

    @Override
    public Agent.ZsuzsiActionState getNextState(Agent.ZsuzsiActionState currentState, List<Double> distances, vec2 position, vec2 dir) {
        List<vec2> sensorDirs = getSensorDirs(position, dir, sensorSpread, distances.size());
        for (int i = 0; i < distances.size(); i++) {
            vec2 sensorDir = sensorDirs.get(i);
            double dist = distances.get(i);
            if (dist > sensorDist) continue;
            vec2 coord = vec2.add(position, vec2.mult(sensorDir, dist));
            addPoint(toCanvasSpace(coord));
        }
        panel.repaint();

        if (distances.get(2) < TRESHOLD ||distances.get(4) < TRESHOLD || distances.get(3) < TRESHOLD) {
            return Agent.ZsuzsiActionState.ROTATE_RIGHT;
        }
        return Agent.ZsuzsiActionState.GO_FORWARD;
    }


    private List<vec2> getSensorDirs(vec2 pos, vec2 dir, double sensorSpread, int sensorCnt) {
        List<vec2> lst = new ArrayList<>();
        vec2 first = vec2.rotate(dir, -sensorSpread/2);
        double stepSize = sensorSpread / (double) sensorCnt;
        for (int i = 0; i < sensorCnt; i++) {
            lst.add(vec2.rotate(first, i*stepSize).normalized());
        }
        return lst;
    }


}
