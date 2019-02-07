import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Activity {
    public static void main(String[] args) throws IOException{
        List<Sequence> inputSequence=getInput();
        printSequence(inputSequence);
        execute(inputSequence);
        List<Sequence> outpuSequence=getOutput();
        System.out.println("");
        //return outpuSequence;

    }
    public static void printSequence(List<Sequence> sequence){
        for(Sequence s: sequence){
            System.out.print("<");
            for(Set<Item> sets: s.getSamples()){
                System.out.print("{");
                for(Item i: sets){
                    System.out.print(i.getName()+",");
                }
                System.out.print("}");

            }
            System.out.println(">");
        }
    }

    private static void execute(List<Sequence> inputSequence) {

    }

    private static List<Sequence> getOutput() {
        return new ArrayList<>();
    }

    private static List<Sequence> getInput() {
        InputDataUtil input = new InputDataUtil();
        return input.getDummyInput().getSequences();
    }


}
