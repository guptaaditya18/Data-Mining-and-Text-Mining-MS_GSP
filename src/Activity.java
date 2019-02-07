import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Activity {
    public static void main(String[] args) throws IOException{
        List<Sequence> inputSequence=getInput();
        execute(inputSequence);
        List<Sequence> outpuSequence=getOutput();
        System.out.println("");
        //return outpuSequence;

    }

    private static void execute(List<Sequence> inputSequence) {

    }

    private static List<Sequence> getOutput() {
        return new ArrayList<>();
    }

    private static List<Sequence> getInput() {
        InputData input = new InputData();
        return input.getDummyInput();
    }


}
