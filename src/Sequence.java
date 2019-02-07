import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Sequence {
    public List<HashMap<Item, Integer>> getTransactionSequence() {
        return transactionSequence;
    }

    public Sequence(List<HashMap<Item, Integer>> transactionSequence) {
        this.transactionSequence = transactionSequence;
    }

    public void setTransactionSequence(List<HashMap<Item, Integer>> transactionSequence) {
        this.transactionSequence = transactionSequence;
    }

    List<HashMap<Item,Integer>> transactionSequence;
}
