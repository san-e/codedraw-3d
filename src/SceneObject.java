public class SceneObject {
    public ObjReader object;
    public Vec3 position;
    public boolean rotate;
    
    SceneObject(ObjReader object, Vec3 position) {
        this.object = object;
        this.position = position;
    }
    SceneObject(ObjReader object, Vec3 position, boolean rotate) {
        this.object = object;
        this.position = position;
        this.rotate = rotate;
    }
}
