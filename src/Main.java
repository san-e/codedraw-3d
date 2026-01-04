import codedraw.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Main {
    static int maxHeight;
    static int maxWidth;
    static int FPS;
    static CodeDraw window;
    static List<SceneObject> scene = new ArrayList<>();

    static Vec3 position = new Vec3(0,0,0);

    public static void main(String[] args) throws IOException {
        maxHeight = 800;
        maxWidth = 2400;
        FPS = 60;
        window = new CodeDraw(maxWidth, maxHeight);

        window.setColor(Color.BLACK);
        window.setLineWidth(0.1);

        double dt = 1.0/FPS;
        int i = 0;
        double angle = 0;


        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(null);
        ObjReader penger = new ObjReader(fileChooser.getSelectedFile().getAbsolutePath());
        scene.add(new SceneObject(penger, new Vec3(1,0,1)));
        scene.add(new SceneObject(penger, new Vec3(-1,0,1)));
        while(true) {
            double dx = i * dt;
            angle += 2 * Math.PI * dt * 0.1;
            angle %= 360;

            for (var e : window.getEventScanner()) {
                if (Objects.requireNonNull(e) instanceof KeyPressEvent event) {
                    Key key = event.getKey();
                    switch (key) {
                        case W -> position.z -= dt;
                        case S -> position.z += dt;
                        case D -> position.x -= dt;
                        case A -> position.x += dt;
                        case SPACE -> position.y += dt;
                        case SHIFT -> position.y -= dt;
                        default -> {
                        }
                    }
                }
            }


            for (SceneObject obj : scene) {
                for (int[] f : obj.object.faceBuffer) {
                    for (int j = 0; j < f.length; j++) {
                        Vec3 a = penger.vertexBuffer.get(f[j] - 1);
                        Vec3 b = penger.vertexBuffer.get(f[(j + 1) % f.length] - 1);
                        a = translate_y(translate_z(rotate_xz(a, angle), 1), 0);
                        b = translate_y(translate_z(rotate_xz(b, angle), 1), 0);

                        drawLine3D(applyPosition(translate(a, obj.position)), applyPosition(translate(b, obj.position)));
                    }
                }
            }
            window.show(1000/FPS);
            window.clear();
            i++;
        }
    }

    private static Vec3 applyPosition(Vec3 point) {
        return translate(point, position);
    }

    private static Vec3 translate(Vec3 point, Vec3 translation) {
        return new Vec3(translate_x(point, translation.x).x,
                translate_y(point, translation.y).y,
                translate_z(point, translation.z).z);
    }

    private static Vec3 translate_x(Vec3 a, double xOffset) {
        return new Vec3(a.x + xOffset, a.y, a.z);
    }

    private static Vec3 translate_y(Vec3 a, double yOffset) {
        return new Vec3(a.x, a.y + yOffset, a.z);
    }

    private static Vec3 translate_z(Vec3 a, double zOffset) {
        return new Vec3(a.x, a.y, a.z+zOffset);
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

    private static void drawLine3D(Vec3 a, Vec3 b) {
        Vec2 c = project3D(a);
        Vec2 cc = project3D(b);
        if (c != null && cc != null) {
            Vec2 aa = screenProject(c);
            Vec2 bb = screenProject(cc);
            drawLine(aa, bb);
        }
    }

    private static void drawPoint(Vec2 point) {
        window.drawPoint(point.w, point.h);
    }

    private static void drawPoint3D(Vec3 point) {
        Vec2 c = project3D(point);
        if (c != null) {
            drawPoint(screenProject(c));
        }
    }

    private static void drawPoint(int x, int y) {
        window.drawPoint(x, y);
    }
}