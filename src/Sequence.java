import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Sequence {

    public List<HashMap<Item, Integer>> getTransactionSequence() {
        return transactionSequence;
    }

    public Sequence(List<List<Item>> samples) {
        this.samples = samples;
    }

    public void setTransactionSequence(List<HashMap<Item, Integer>> transactionSequence) {
        this.transactionSequence = transactionSequence;
    }

    public List<List<Item>> getSamples() {
        return samples;
    }

    public void setSamples(List<List<Item>> samples) {
        this.samples = samples;
    }

    List<HashMap<Item,Integer>> transactionSequence;
    List<List<Item>> samples;

}
