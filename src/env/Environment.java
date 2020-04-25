package env;

import agent.Agent;
import agent.controllers.ExplorerController;
import agent.controllers.RightTurner;
import graphics.IDrawable;
import math.Line;
import math.Ray;
import math.RayLineIntersection;
import math.vec2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Environment implements IDrawable {
    private List<Line> walls;
    private Agent zsuzsi;

    public Environment() {
        this.walls = new ArrayList<>();
        int SIZE = 1000;
        walls.add(new Line(new vec2(-SIZE, -SIZE), new vec2(-SIZE, SIZE)));
        walls.add(new Line(new vec2(-SIZE, SIZE), new vec2(SIZE, SIZE)));
        walls.add(new Line(new vec2(SIZE, SIZE), new vec2(SIZE, -SIZE)));
        walls.add(new Line(new vec2(SIZE, -SIZE), new vec2(-SIZE, -SIZE)));
        this.zsuzsi = new Agent(vec2.ZERO, 50, 5, Math.PI/2, this, new ExplorerController(50*15));
    }

    public void addWall(Line line) { this.walls.add(line); }
    public List<Line> getWalls() { return walls; }

    public RayLineIntersection castRay(Ray r) {
        RayLineIntersection intersection = null;
        for (Line l : walls) {
            RayLineIntersection currentIntersection = l.castRay(r);
            if (intersection == null) {
                intersection = currentIntersection;
            } else if (intersection.getDistance() > currentIntersection.getDistance()) {
                intersection = currentIntersection;
            }
        }
        return intersection;
    }

    public void step() { zsuzsi.step(); }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.red);
        for (Line l : walls) {
            l.paint(g);
        }
        zsuzsi.paint(g);
    }
}
