import java.lang.Math;

public class Vec {
    double x;
    double y;

    public Vec(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void print() {
        System.out.println(x + ", " + y);
    }

    public static double distance(Vec vec1, Vec vec2) {
        return Math.sqrt(Math.pow(vec2.x - vec1.x, 2) + Math.pow(vec2.y - vec1.y, 2));
    }

    public Vec add(Vec vec2) {
        return new Vec(x + vec2.x, y + vec2.y);
    }

    public Vec sub(Vec vec2) {
        return new Vec(x - vec2.x, y - vec2.y);
    }

    public double mag() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public Vec norm_vec() {
        double magnitude = mag();
        return new Vec(x/magnitude, y/magnitude);
    }

    public double[] convarr() {
        double[] arr = {x, y};
        return arr;
    }

    public Vec mul(double n) {
        return new Vec(n * x, n * y);
    }

    public Vec div(double n) {
        return new Vec(x/n, y/n);
    }
}
