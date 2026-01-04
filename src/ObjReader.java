import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjReader {
    List<Vec3> vertexBuffer = new ArrayList<>();
    List<int[]> faceBuffer = new ArrayList<>();

    public ObjReader(String path) throws IOException {
        // Creating BufferedReader for Input
        BufferedReader bfri = new BufferedReader(
                new InputStreamReader(System.in));

        BufferedReader bfro
                = new BufferedReader(new FileReader(path));
        String st;
        String[] line;

        while ((st = bfro.readLine()) != null) {
            line = st.split(" ");
            if (st.isEmpty()) {
                continue;
            }
            switch (line[0]) {
                case "v":
                    if (line.length == 4) {
                        double x = Double.parseDouble(line[1]);
                        double y = Double.parseDouble(line[2]);
                        double z = Double.parseDouble(line[3]);
                        vertexBuffer.add(new Vec3(x, y, z));
                    }
                    else if (line.length == 5) {
                        double x = Double.parseDouble(line[1]);
                        double y = Double.parseDouble(line[2]);
                        double z = Double.parseDouble(line[3]);
                        double w = Double.parseDouble(line[4]);
                        vertexBuffer.add(new Vec3(x/w, y/w, z/w));
                    }
                    break;
                case "f":
                    int[] indices = new int[line.length-1];
                    for (int i = 1; i < line.length; i++) {
                        String index = line[i];
                        int a = Integer.parseInt(index.split("/")[0]);
                        indices[i-1] = a;
                    }
                    faceBuffer.add(indices);

            }
        }
    }
}
