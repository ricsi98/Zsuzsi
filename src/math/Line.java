package math;

import graphics.IDrawable;

import java.awt.*;

public class Line implements IDrawable {
    private vec2 A, B;

    public Line(vec2 A, vec2 B) {
        this.A = A;
        this.B = B;
    }

    public vec2 getA() { return A; }
    public void setA(vec2 a) { A = a; }
    public vec2 getB() { return B; }
    public void setB(vec2 b) { B = b; }

    public RayLineIntersection castRay(Ray ray) {
        vec2 v1 = vec2.sub(ray.getOrigin(), A);
        vec2 v2 = vec2.sub(B, A);
        vec2 v3 = new vec2(-ray.getDir().getY(), ray.getDir().getX());

        double t1 = vec2.cross(v2, v1) / vec2.dot(v2, v3);
        double t2 = vec2.dot(v1, v3) / vec2.dot(v2, v3);

        if (t1 < 0) return new RayLineIntersection(ray, this, Double.MAX_VALUE);
        if (t2 < 0 || t2 > 1) return new RayLineIntersection(ray, this, Double.MAX_VALUE);
        return new RayLineIntersection(ray, this, t1);
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.black);
        g.drawLine((int)A.getX(), (int)A.getY(), (int)B.getX(), (int)B.getY());
    }
}
