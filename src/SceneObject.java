public class SceneObject {
    ObjReader object;
    Vec3 position;
    boolean rotation;
    
    SceneObject(ObjReader object, Vec3 position) {
        this.object = object;
        this.position = position;
    }
}
