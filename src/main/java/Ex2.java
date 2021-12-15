import GUI.MyGraph_GUI;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.MyGraph;
import api.ParseToGraph;
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
        DirectedWeightedGraph ans = null;
        // ****** Add your code here ******
        //
        // ********************************
        return ans;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = null;
        // ****** Add your code here ******
        //
        // ********************************
        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        // ****** Add your code here ******
        //
        // ********************************
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
//        drawGUI();

    }
}