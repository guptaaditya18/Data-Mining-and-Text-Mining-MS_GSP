import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Util {
    public static void printSequence(List<Sequence> sequence){
        for(Sequence s: sequence){
            System.out.print("<");
            for(Set<Integer> sets: s.getSequenceData()){

                    System.out.print(sets);

            }
            System.out.println(">");
        }
    }
    public static List<List<Integer>> setToList(List<Set<Integer>> listOfSet){
        List<List<Integer>> listOfList = new ArrayList<List<Integer>>();
        for (Set<Integer> setFroms1:listOfSet){
            List<Integer> stemp = new ArrayList<Integer>();
            for(Integer intfromset:setFroms1)
                stemp.add(intfromset);
            listOfList.add(stemp);
        }

        return listOfList;
    }

    public static void printSequence(Map<Sequence,Integer> hashMap){


        for(Map.Entry<Sequence,Integer> entry:hashMap.entrySet()){
            System.out.print("<");
            for(Set<Integer> sets: entry.getKey().getSequenceData()){
                System.out.print(sets);
            }
            System.out.println(">" + "\t"+ entry.getValue());
        }
    }
}
