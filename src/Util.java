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

    public static int getItemFromSequenceAtIndex(Sequence sequence, Index_of_Retrieval index){

        List<Set<Integer>> seq=sequence.getSequenceData();
        switch (index){
            case LAST:
                Set<Integer> lastset = seq.get(seq.size()-1);
                Iterator<Integer> it2 = lastset.iterator();
                int lastElement = 0;
                while(it2.hasNext()){
                    lastElement = it2.next();
                }
                return lastElement;
            case FIRST:
                return seq.get(0).iterator().next();
                default: return 0;

        }
    }
    public static List<List<Integer>> removeItemFromIndex(Sequence sequence,Index_of_Retrieval index){

        List<List<Integer>> seqDash = Util.setToList(sequence.getSequenceData());
        switch (index){
            case LAST:
                if(seqDash.get(seqDash.size()-1).size() == 1){
                    seqDash.remove(seqDash.size()-1);
                }else{
                    seqDash.get(seqDash.size()-1).remove((seqDash.get(seqDash.size()-1).size())-1);
                }
                return seqDash;
            case FIRST:
                if(seqDash.get(0).size()>1){
                    seqDash.get(0).remove(0);
                }else{
                    seqDash.remove(0);
                }
                return seqDash;
            case SECOND:
                if(seqDash.get(0).size() > 1)
                    seqDash.get(0).remove(1);
                else if (seqDash.get(1).size() == 1)
                    seqDash.remove(1);
                return seqDash;
            case SECOND_LAST:
                if(seqDash.get(sequence.getSequenceData().size()-1).size() > 1)
                    seqDash.get(seqDash.size()-1).remove((seqDash.get(seqDash.size()-1).size())-2);
                else if(seqDash.get(seqDash.size() - 2).size() == 1)
                    seqDash.remove((seqDash.size()) - 2);
                else{
                    seqDash.get(seqDash.size()-2).remove(seqDash.get(seqDash.size()-2).size()-2);
                }
            default: return seqDash;

        }

    }

//    public static boolean isFirstElementSmallerThanRest(Sequence sequence){
//        boolean firstElementSmallerThanRestCondition=true;
//        Double minsupFirstS1 = minSupItems.get(getItemFromSequenceAtIndex(sequence,Index_of_Retrieval.FIRST));
//        for(Set<Integer> setFromS1:s1){
//            for(int itemsFromS1:setFromS1){
//                if(!flag){
//                    flag = true;
//                    continue;
//                }
//                if(minsupFirstS1 >= minSupItems.get(itemsFromS1))
//                    firstElementSmallerThanRestCondition=false;
//            }
//        }
//        return firstElementSmallerThanRestCondition;
//    }
    private static List<Integer> convertSetListToIntegerList(List<Set<Integer>> sequenceList){
        List<Integer> list=new ArrayList<>();
        for(Set<Integer> sets:sequenceList){
            list.addAll(sets);
        }
        return list;

    }
    private static boolean isFirstSubsetOfSecond(List<Set<Integer>> candidateSequence, List<Set<Integer>> dataSequence) {

        int j=0;
        List<Integer> candidateList =convertSetListToIntegerList(candidateSequence);
        List<Integer> dataList =convertSetListToIntegerList(dataSequence);


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

    public static int getItemWithLeastMisValue(Sequence seq, HashMap<Integer, Double> minSupItems) {
        double min= Integer.MAX_VALUE;
        int itemWithLowestMis=seq.getSequenceData().get(0).iterator().next();

        for(Set<Integer> itemset: seq.getSequenceData()){
            for(Integer item:itemset){
                try {
                    if (minSupItems.get(item) < min) {
                        min = minSupItems.get(item);
                        itemWithLowestMis=item;
                    }
                }catch (NullPointerException ne){
                    System.out.println("item not found: "+item);
                }

            }
        }
        return itemWithLowestMis;
    }

    public static  List<Sequence> getPrunedMSGSPCandidates(List<Sequence> ck, HashMap<Integer, Double> minSupItems, Data data) {


        List<Sequence> unprunedSequences=new ArrayList<>();
        for(Sequence seq:ck){
            List<List<Integer>> seqCopy=Util.setToList(seq.getSequenceData());

            int elementWithLowestMis=Util.getItemWithLeastMisValue(seq,minSupItems);
            for(int i=0;i<seqCopy.size();i++){
                List<Integer> set=seqCopy.get(i);
                for(int j=0;j<set.size();j++){
                    int removedItem=set.remove(j);
                    if(set.isEmpty()){
                        seqCopy.removeAll(set);
                    }
                    //this is the condition for pruning
                    if(removedItem==elementWithLowestMis ){
                        Sequence prunedSequence=makeSequenceFromList(seqCopy);
                        float candidateSup=(float)Util.getcandidateCountForSequence(prunedSequence,data)/(float)data.getSequences().size();
                        //only if the below criteria is satisfied it is added else it is pruned.
                        if(candidateSup >= data.getItemMinSup().get(elementWithLowestMis)){
                            unprunedSequences.add(seq);
                        }
                    }
                    set.add(j,removedItem);
                }
            }

        }
        return new ArrayList<>();
    }
    private static Sequence makeSequenceFromList(List<List<Integer>> seqCopy) {
        List<Set<Integer>> listOfSets=new ArrayList<>();
        for(List<Integer> lists: seqCopy){
            Set<Integer> set=new LinkedHashSet<>();
            if(!lists.isEmpty()){

                set.addAll(lists);
            }
            if(!set.isEmpty())
                listOfSets.add(set);
        }
        return new Sequence(listOfSets);
    }


    public static int getcandidateCountForSequence(Sequence prunedSequence, Data data) {
        int count=0;
        for(Sequence sequence: data.getSequences()){
            List<Set<Integer>> dataSequence = sequence.getSequenceData();
                if(isFirstSubsetOfSecond(prunedSequence.getSequenceData(),dataSequence)){
                count++;

                }
        }
        return count;
    }
}
