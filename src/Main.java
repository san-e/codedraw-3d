import codedraw.*;

import java.awt.*;

class Main {
    static int maxHeight;
    static int maxWidth;
    static int FPS;
    static CodeDraw window;

    public static void main(String[] args) {
        maxHeight = 800;
        maxWidth = 800;
        FPS = 60;
        window = new CodeDraw(maxWidth, maxHeight);

        window.setColor(Color.RED);
        window.setLineWidth(1);

        Vec3[] cube = new Vec3[] {  new Vec3(0.5, 0.5, 0.5),
                                    new Vec3(-0.5, 0.5, 0.5),
                                    new Vec3(-0.5, -0.5, 0.5),
                                    new Vec3(0.5, -0.5, 0.5),

                                    new Vec3(0.5, 0.5, -0.5),
                                    new Vec3(-0.5, 0.5, -0.5),
                                    new Vec3(-0.5, -0.5, -0.5),
                                    new Vec3(0.5, -0.5, -0.5)};

        Vec2[] lines = new Vec2[] { new Vec2(0, 1), new Vec2(0, 3),
                                    new Vec2(2, 1), new Vec2(2, 3),

                                    new Vec2(4, 5),new Vec2(4, 7),
                                    new Vec2(6, 5),new Vec2(6, 7),

                                    new Vec2(0, 4),new Vec2(1, 5),
                                    new Vec2(2, 6),new Vec2(3, 7),};

        double dt = 1.0/FPS;
        int i = 0;
        double angle = 0;
        while(true) {
            double dx = i * dt;
            angle += 2 * Math.PI * dt * 0.1;
            angle = angle % 360;
            for (Vec2 line : lines) {
                Vec3 temp0 = new Vec3(cube[(int) line.x]);
                Vec3 temp1 = new Vec3(cube[(int) line.y]);

                temp0 = rotate_xz(temp0, angle);
                temp1 = rotate_xz(temp1, angle);

//                temp0 = rotate_xy(temp0, angle);
//                temp1 = rotate_xy(temp1, angle);

                temp0 = translate_z(temp0, Math.sin(dx)+2);
                temp1 = translate_z(temp1, Math.sin(dx)+2);

                drawLine3D(temp0, temp1);
            }
//            for (Vec3 f : fs) {
//                for (int j = 0; j < 3; j++) {
//                    Vec3 a = vs[(int)f.get(j)];
//                    Vec3 b = vs[(int)f.get((j+1)%3)];
//                    drawLine3D(a, b, angle);
//                }
//            }
            window.show(1000/FPS);
            window.clear();
            i++;
        }
    }

    private static void drawLine3D(Vec3 a, Vec3 b) {
        Vec2 c = project3D(a);
        Vec2 cc = project3D(b);
        if (c != null && cc != null) {
            Vec2 aa = screenProject(c);
            Vec2 bb = screenProject(cc);
            drawLine(aa, bb);
        }
    }

    private static Vec3 translate_z(Vec3 a, double x) {
        return new Vec3(a.x, a.y, a.z+x);
    }

    private static Vec3 rotate_xz(Vec3 point, double angle) {
        return new Vec3( point.x * Math.cos(angle) - point.z * Math.sin(angle),
                        point.y,
                        point.x * Math.sin(angle) + point.z * Math.cos(angle));
    }

    private static Vec3 rotate_yz(Vec3 point, double angle) {
        return new Vec3(  point.x,
                        point.y * Math.cos(angle) - point.z * Math.sin(angle),
                        point.y * Math.sin(angle) + point.z * Math.cos(angle));
    }

    private static Vec3 rotate_xy(Vec3 point, double angle) {
        return new Vec3( point.x * Math.cos(angle) - point.y * Math.sin(angle),
                point.x * Math.sin(angle) + point.y * Math.cos(angle),
                point.z);
    }

    private static Vec2 screenProject(Vec2 point) {
        int scale = Math.min(maxWidth, maxHeight);

        double xOffset = (maxWidth  - scale) * 0.5;
        double yOffset = (maxHeight - scale) * 0.5;

        return new Vec2(((point.x + 1) / 2) * scale + xOffset,
                        (1 -((point.y + 1) / 2)) * scale + yOffset);
    }

    private static Vec2 project3D(Vec3 point) {
        if (point.z <= 0) {
            return null;
        }
        return new Vec2(point.x / point.z, point.y / point.z);
    }

    private static void drawLine(Vec2 start, Vec2 end) {
        window.drawLine(start.x, start.y, end.x, end.y);
    }

    private static void drawPoint(Vec2 point) {
        window.drawPoint(point.w, point.h);
    }

    private static void drawPoint(int x, int y) {
        window.drawPoint(x, y);
    }
}