import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Activity {
    public static void main(String[] args) throws IOException{
        Data inputSequence=getInput();
        System.out.println("");
        MSGSPAlgo algo=new MSGSPAlgo();
        algo.getFinalSequence(inputSequence);

    }


    private static void execute(List<Sequence> inputSequence) {

    }


    private static List<Sequence> getOutput() {
        return new ArrayList<>();
    }

    private static Data getInput() {
        InputDataUtil input = new InputDataUtil();
        return input.parseTxt();
    }


}
