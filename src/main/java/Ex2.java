import GUI.MyGraph_GUI;
import api.*;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        try {
            ParseToGraph pd = new ParseToGraph(json_file);
            DirectedWeightedGraph ans = new MyGraph(pd.getNodes(), pd.size);
            return ans;
        }
        catch (FileNotFoundException e){
            System.err.println("File not found!");
            e.printStackTrace();
        }
        return null;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = new MyGraphAlgo();
        ans.load(json_file);
        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        DirectedWeightedGraph graph = getGrapg(json_file);
        new MyGraph_GUI(graph);
    }

    public static void main(String[] args) {

        try {
             ParseToGraph pd = new ParseToGraph(args[0]);
            MyGraph mg = new MyGraph(pd.getNodes(),pd.size);
            new MyGraph_GUI(mg);
        }
        catch (Exception e){
            new MyGraph_GUI(new MyGraph());
        }
    }
}