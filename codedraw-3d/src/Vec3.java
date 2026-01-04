public class Vec3 {
    public double x;
    public double y;
    public double z;

    Vec3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Vec3(Vec3 point) {
        this.x = point.x;
        this.y = point.y;
        this.z = point.z;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

    public double get(int idx){
        return switch (idx) {
            case 0 -> x;
            case 1 -> y;
            case 2 -> z;
            default -> 0;
        };
    }
}
