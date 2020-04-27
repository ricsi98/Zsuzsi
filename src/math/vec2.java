package math;

public class vec2 {
    private double x, y;

    public vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {return x;}
    public double getY() {return y;}
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}

    public double L2() { return Math.sqrt(x*x + y*y); }
    public vec2 normalized() { return new vec2(x / this.L2(), y / this.L2()); }
    public double getAngle() { return Math.atan2(y, x); }

    public static vec2 add(vec2 a, vec2 b) { return new vec2(a.x + b.x, a.y + b.y); }
    public static vec2 sub(vec2 a, vec2 b) { return new vec2(a.x - b.x, a.y - b.y); }
    public static vec2 mult(vec2 v, double s) { return new vec2(v.x * s, v.y * s); }
    public static double dot(vec2 a, vec2 b) { return a.x * b.x + a.y * b.y; }
    public static vec2 rotate(vec2 v, double angle) {
        return new vec2(v.x * Math.cos(angle) - v.y * Math.sin(angle), v.x * Math.sin(angle) + v.y * Math.cos(angle));
    }
    public static double cross(vec2 a, vec2 b) { return a.getX() * b.getY() - a.getY() * b.getX(); }

    public static final vec2 ZERO = new vec2(0,0);
    public static final vec2 EAST = new vec2(1,0);
    public static final vec2 WEST = new vec2(-1, 0);
    public static final vec2 NORTH = new vec2(0,1);
    public static final vec2 SOUTH = new vec2(0,-1);

    @Override
    public String toString() {
        return x + " " + y;
    }

}
