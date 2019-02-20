import java.util.HashMap;
import java.util.List;


/**
 * Class for Storing the data in the needed format specified.
 */
public class Data {
    private List<Sequence> sequences;
    private HashMap<Integer,Double> itemMinSup;
    private Double SDC;

    public Data(List<Sequence> sequences, HashMap<Integer, Double> itemMinSup, Double SDC) {
        this.sequences = sequences;
        this.itemMinSup = itemMinSup;
        this.SDC = SDC;
    }

    public List<Sequence> getSequences() {
        return sequences;
    }

    public void setSequences(List<Sequence> sequences) {
        this.sequences = sequences;
    }

    public HashMap<Integer, Double> getItemMinSup() {
        return itemMinSup;
    }

    public void setItemMinSup(HashMap<Integer, Double> itemMinSup) {
        this.itemMinSup = itemMinSup;
    }

    public Double getSDC(){
        return SDC;
    }

    public void setSDC(Double SDC){
        this.SDC = SDC;
    }
}
