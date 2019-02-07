import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Activity {
    public static void main(String[] args) throws IOException{
        List<Sequence> inputSequence=getInput();
        execute(inputSequence);
        List<Sequence> outpuSequence=getOutput();
        //return outpuSequence;

    }

    private static void execute(List<Sequence> inputSequence) {
        
    }

    private static List<Sequence> getOutput() {
        return new ArrayList<>();
    }

    private static List<Sequence> getInput() {
        return new ArrayList<>();
    }
}
