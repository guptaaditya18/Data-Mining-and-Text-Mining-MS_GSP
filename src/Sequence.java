import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Sequence {

    public List<HashMap<Item, Integer>> getTransactionSequence() {
        return transactionSequence;
    }

    public Sequence(List<Set<Item>> samples) {
        this.samples = samples;
    }

    public void setTransactionSequence(List<HashMap<Item, Integer>> transactionSequence) {
        this.transactionSequence = transactionSequence;
    }

    public List<Set<Item>> getSamples() {
        return samples;
    }

    public void setSamples(List<Set<Item>> samples) {
        this.samples = samples;
    }

    List<HashMap<Item,Integer>> transactionSequence;
    List<Set<Item>> samples;

}
