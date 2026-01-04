public class Vec2 {
    public double x;
    public double y;

    public double w;
    public double h;

    Vec2(int x, int y) {
        this.x = x;
        this.y = y;

        this.w = x;
        this.h = y;
    }

    Vec2(float x, float y) {
        this.x = x;
        this.y = y;

        this.w = x;
        this.h = y;
    }

    Vec2(double x, double y) {
        this.x = x;
        this.y = y;

        this.w = x;
        this.h = y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
