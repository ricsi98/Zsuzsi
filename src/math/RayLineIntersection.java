package math;

public class RayLineIntersection {
    private Ray ray;
    private Line line;
    private double distance;

    public RayLineIntersection(Ray ray, Line line, double distance) {
        this.ray = ray;
        this.line = line;
        this.distance = distance;
    }

    public Ray getRay() { return ray; }
    public Line getLine() { return line; }
    public double getDistance() { return distance; }

    public vec2 getIntersectionPoint() {
        return vec2.add(ray.getOrigin(), vec2.mult(ray.getDir(), distance));
    }
}
