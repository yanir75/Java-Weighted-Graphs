package api;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;

public class ParseData {
    public HashMap<String, String>[] Edges;
    public HashMap<String, String>[] Nodes;

    public ParseData() throws FileNotFoundException {}
    Gson gson = new Gson();
    try{
        Reader reader = new FileReader("C:\\Users\\netan\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\G1.json";
        ParseData p = gson.fromJson(reader, ParseData.class);
        System.out.println(Arrays.toString(p.Edges));
        System.out.println(Arrays.toString(p.Nodes));
    }
    catch(FileNotFoundException e;){
        e.printStackTrace();
        System.out.println("\nJson file wasn't found!");
    }

//    public ParseData() throws FileNotFoundException {
//    }
}


//class Parse{
//    public HashMap<String, String>[] Edges;
//    public HashMap<String, String>[] Nodes;
//
//
//    public Parse(){
//
//    }
//    public static void main(String[] args) throws IOException {
//        Gson gson = new Gson();
//        try (Reader reader = new FileReader("C:\\Users\\netan\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\G1.json")) {
//            Parse p = gson.fromJson(reader, Parse.class);
//            System.out.println(Arrays.toString(p.Edges));
//            System.out.println(Arrays.toString(p.Nodes));
//        }
//        catch (FileNotFoundException e){
//            e.printStackTrace();
//            System.out.println("\nJson file wasn't found!");
//        }
//    }
//}


