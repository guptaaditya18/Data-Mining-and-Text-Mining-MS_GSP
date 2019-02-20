import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Activity {
    //Start of activity for MSGSP Exceution
    public static void main(String[] args) throws IOException{

        Data inputSequence=getInput();


        MSGSPAlgo algo=new MSGSPAlgo();
        algo.getFinalSequence(inputSequence);

    }

    private static List<Sequence> getOutput() {
        return new ArrayList<>();
    }

    private static Data getInput() {
        InputDataUtil input = new InputDataUtil();
        return input.parseTxt();
    }


}
