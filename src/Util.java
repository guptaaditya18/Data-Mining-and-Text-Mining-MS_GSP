import java.util.*;

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

    public static double getMinMisValue(Sequence sequence, HashMap<Integer, Double> itemMinSup) {
        double min= Integer.MAX_VALUE;

        for(Set<Integer> itemset: sequence.getSequenceData()){
            for(Integer item:itemset){
                try {
                    if (itemMinSup.get(item) < min) {
                        min = itemMinSup.get(item);
                    }
                }catch (NullPointerException ne){
                    System.out.println("item not found: "+item);
                }

            }
        }
        return min;

    }
    public static HashMap<Sequence,Integer> getcandidateCount(List<Sequence> c2, Data data) {
        HashMap<Sequence,Integer> candidateSequenceMap=new LinkedHashMap<>();

        for(Sequence sequence: data.getSequences()){

            List<Set<Integer>> dataSequence = sequence.getSequenceData();
            //System.out.print("data sequence:"+dataSequence);
            for(Sequence candidateSequence:c2){
                //System.out.print("\t candidate sequence:"+candidateSequence.getSequenceData());
                if(isFirstSubsetOfSecond(candidateSequence.getSequenceData(),dataSequence)){

                    candidateSequenceMap.put(candidateSequence,candidateSequenceMap.getOrDefault(candidateSequence,0)+1);
                }


            }
        }
        return candidateSequenceMap;
    }

    private static boolean isFirstSubsetOfSecond(List<Set<Integer>> candidateSequence, List<Set<Integer>> dataSequence) {

        int j=0;
        for(int i=0;i<dataSequence.size() && j<candidateSequence.size();i++){
            if(dataSequence.get(i).containsAll(candidateSequence.get(j))){
                j++;
            }
        }
        if(j==candidateSequence.size()){
            return true;
        }

        return false;
    }
    public static TreeMap<Integer, Double> getSortedMapByvalue(HashMap<Integer, Double> itemMinSup) {
        Comparator<Integer> valueComparator =  new Comparator<Integer>() {
            public int compare(Integer k1, Integer k2) {

                return (itemMinSup.get(k1)>itemMinSup.get(k2)?1:-1);
            }
        };
        TreeMap<Integer,Double> map=new TreeMap<>(valueComparator);
        map.putAll(itemMinSup);
        return map;
    }
    public static List<Sequence> removeDuplicates(List<Sequence> f2Values) {

        Map<List<Set<Integer>>,Integer> actualSequenceList=new HashMap<>();
        List<Sequence> sequenceList=new ArrayList<>();
        for(Sequence s:f2Values){
            actualSequenceList.put(s.getSequenceData(),1);
        }
        for(List<Set<Integer>> seq:actualSequenceList.keySet()){
            sequenceList.add(new Sequence(seq));
        }
        return sequenceList;
    }
    public static HashMap<Integer,Integer> getItemCountMap(Data data, HashMap<Integer, Double> itemMinSup){
        HashMap<Integer, Integer> itemCountSeq = new HashMap<>();
        for (Sequence s : data.getSequences()) {
            HashMap<Integer, Integer> itemCountSet = new HashMap<>();
            for (Set<Integer> sets : s.getSequenceData()) {
                for (int i : sets) {
                    itemCountSet.put(i, 1);
                }
            }
            for (Map.Entry<Integer, Integer> entry : itemCountSet.entrySet()) {
                itemCountSeq.put(entry.getKey(), itemCountSeq.getOrDefault(entry.getKey(), 0) + 1);
            }
        }
        for(Map.Entry<Integer,Double>itemSup:itemMinSup.entrySet()){
            if(itemCountSeq.get(itemSup.getKey())==null){
                itemCountSeq.put(itemSup.getKey(),0);
            }
        }
        return itemCountSeq;
    }
    public static HashMap<Sequence, Integer> convertCandidatesToFinalSequence(HashMap<Sequence, Integer> c2, Data data, double tot) {
        HashMap<Sequence,Integer> finalSequence= new LinkedHashMap<>();
        for(Map.Entry<Sequence,Integer> entry:c2.entrySet()){
            double min=-1;
            boolean flag=false;

            min=getMinMisValue(entry.getKey(),data.getItemMinSup());

            if((float)entry.getValue()/tot>min){
                finalSequence.put(new Sequence(entry.getKey().getSequenceData()), entry.getValue());
            }
        }
        return finalSequence;
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
