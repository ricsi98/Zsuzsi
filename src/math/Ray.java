package math;

public class Ray {
    private vec2 origin, dir;

    public Ray(vec2 origin, vec2 dir) {
        this.origin = origin;
        this.dir = dir;
    }

    public vec2 getOrigin() { return origin; }
    public vec2 getDir() { return dir; }
}
