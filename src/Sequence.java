import java.util.List;
import java.util.Set;

public class Sequence {
    public Sequence(List<Set<Integer>> sequenceData) {
        this.sequenceData = sequenceData;
    }


    public List<Set<Integer>> getSequenceData() {
        return sequenceData;
    }

    public void setSequenceData(List<Set<Integer>> sequenceData) {
        this.sequenceData = sequenceData;
    }

    List<Set<Integer>> sequenceData;

}
