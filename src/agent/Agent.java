package agent;

import env.Environment;
import graphics.IDrawable;
import math.Line;
import math.Ray;
import math.RayLineIntersection;
import math.vec2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Agent implements IDrawable {
    private vec2 pos, dir;
    private double r, sensorSpread, turnRate, speed;
    private int sensorCnt;
    private Environment world;
    private ZsuzsiActionState actionState;
    private IController controller;

    public double getTurnRate() { return turnRate; }
    public void setTurnRate(double turnRate) { this.turnRate = turnRate; }
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.black);
        double RAYLENGTH = r*15;
        double HITRADIUS = r / 2;
        for (RayLineIntersection is : getSensorHits()) {
            g.setColor(Color.black);
            vec2 end = vec2.add(pos, vec2.mult(is.getRay().getDir().normalized(), RAYLENGTH));
            g.drawLine((int)pos.getX(), (int)pos.getY(), (int)end.getX(), (int)end.getY());
            if (is.getDistance() < RAYLENGTH) {
                g.setColor(Color.red);
                int x = (int)(is.getIntersectionPoint().getX() - HITRADIUS);
                int y = (int)(is.getIntersectionPoint().getY() - HITRADIUS);
                g.fillOval(x, y, (int)HITRADIUS*2, (int)HITRADIUS*2);
            }
        }

        g.setColor(Color.white);
        g.fillOval((int)pos.getX() - (int)r, (int)pos.getY() - (int)r, (int)r*2, (int)r*2);
        vec2 tip = vec2.add(pos, vec2.mult(dir.normalized(), r));
        g.setColor(Color.black);
        g.drawLine((int)pos.getX(), (int)pos.getY(), (int)tip.getX(), (int)tip.getY());
    }

    public enum ZsuzsiActionState {
        ROTATE_RIGHT,
        ROTATE_LEFT,
        GO_FORWARD,
        GO_BACKWARD,
        IDLE
    }

    public Agent(vec2 pos, double r, int sensorCnt, double sensorSpread, Environment world, IController controller) {
        this.pos = pos;
        this.r = r;
        this.sensorCnt = sensorCnt;
        this.sensorSpread = sensorSpread;
        this.world = world;
        this.dir = new vec2(1,0);
        this.speed = r / 10;
        this.turnRate = Math.PI / 180;
        this.actionState = ZsuzsiActionState.IDLE;
        this.controller = controller;
    }

    private List<vec2> getSensorDirs() {
        List<vec2> lst = new ArrayList<>();
        vec2 first = vec2.rotate(dir, -sensorSpread/2);
        double stepSize = sensorSpread / (double) sensorCnt;
        for (int i = 0; i < this.sensorCnt; i++) {
            lst.add(vec2.rotate(first, i*stepSize).normalized());
        }
        return lst;
    }

    public List<RayLineIntersection> getSensorHits() {
        List<RayLineIntersection> lst = new ArrayList<>();
        for (vec2 rayDir : getSensorDirs()) {
            Ray r = new Ray(pos, rayDir);
            lst.add(world.castRay(r));
        }
        return lst;
    }

    public List<Double> getSensorDistances() {
        List<Double> lst = new ArrayList<>();
        for (RayLineIntersection intersection : getSensorHits()) {
            lst.add(intersection.getDistance());
        }
        return lst;
    }

    public void step() {
        switch (actionState) {
            case GO_FORWARD: this.pos = vec2.add(pos, vec2.mult(dir.normalized(), speed)); break;
            case GO_BACKWARD: this.pos = vec2.add(pos, vec2.mult(dir.normalized(), -speed)); break;
            case ROTATE_LEFT: this.dir = vec2.rotate(dir, turnRate); break;
            case ROTATE_RIGHT: this.dir = vec2.rotate(dir, -turnRate); break;
        }
        control();
    }

    public void control() {
        actionState = controller.getNextState(actionState, getSensorDistances(), pos, dir, sensorSpread);
    }
}
