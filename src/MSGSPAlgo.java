import java.util.*;

public class MSGSPAlgo {

    public void getFinalSequence(Data data){
        List<List<Sequence>> resultSeq=new ArrayList<>();
        int tot=data.getSequences().size();


        //Sort acc to MIS:
        TreeMap<Integer, Double> sortedItemMinSup = getSortedMapByvalue(data.getItemMinSup());


        System.out.println("Sorted item min sup:");
        System.out.println(sortedItemMinSup);

        HashMap<Integer, Integer> itemCountMap = getItemCountMap(data);
        System.out.println("item count map");
        System.out.println(itemCountMap);

        Set<Integer> lvalues=getLvalues(new HashMap<>(sortedItemMinSup),data,itemCountMap,tot);


        List<Sequence> c1 = getFirstLevelSeq(lvalues, itemCountMap, new HashMap<>(sortedItemMinSup), tot);

        Util.printSequence(c1);
        resultSeq.add(getFirstLevelSeq(lvalues,itemCountMap,new HashMap<>(sortedItemMinSup),tot));

        List<Sequence> c2 = getSecondLevelSequence(new HashMap<>(sortedItemMinSup), itemCountMap, lvalues, tot, data.getSDC());

        HashMap<Sequence, Integer> c2Count = getcandidateCount(c2, data);
        Util.printSequence(c2Count);


    }

    private HashMap<Sequence,Integer> getcandidateCount(List<Sequence> c2, Data data) {
        HashMap<Sequence,Integer> candidateSequenceMap=new HashMap<>();
        for(Sequence sequence: data.getSequences()){
            List<Set<Integer>> dataSequence = sequence.getSequenceData();
            System.out.println(dataSequence);
            for(Sequence candidateSequence:c2){

                if(isFirstSubsetOfSecond(candidateSequence.getSequenceData(),dataSequence)){
                    candidateSequenceMap.put(candidateSequence,candidateSequenceMap.getOrDefault(candidateSequence,0)+1);
                }

            }
        }
        return candidateSequenceMap;
    }

    //
    private boolean isFirstSubsetOfSecond(List<Set<Integer>> candidateSequence, List<Set<Integer>> dataSequence) {

        return dataSequence.containsAll(candidateSequence);
    }

    private List<Sequence> getFirstLevelSeq(Set<Integer> lvalues, HashMap<Integer, Integer> itemCountMap, HashMap<Integer, Double> sortedItemMinSup,int tot) {
        List<Sequence> F1Values=new ArrayList<>();

        for(int i:lvalues){
            double count=(double)itemCountMap.get(i);
            if(count/tot >=sortedItemMinSup.get(i)){
                Set<Integer> set=new HashSet<>();
                set.add(i);
                List<Set<Integer>> l1SetList=new ArrayList();
                l1SetList.add(set);
                F1Values.add(new Sequence(l1SetList));
            }
        }
        return F1Values;
    }

    private TreeMap<Integer, Double> getSortedMapByvalue(HashMap<Integer, Double> itemMinSup) {
        Comparator<Integer> valueComparator =  new Comparator<Integer>() {
            public int compare(Integer k1, Integer k2) {

                return (itemMinSup.get(k1)>itemMinSup.get(k2)?1:-1);
            }
        };
        TreeMap<Integer,Double> map=new TreeMap<>(valueComparator);
        map.putAll(itemMinSup);
        return map;
    }

    private Set<Integer> getLvalues(HashMap<Integer, Double> minSupItems, Data data, HashMap<Integer, Integer> itemCountMap,int tot) {
        Set<Integer> lvalues=new HashSet<>();


        double mis=-1;
        for(Map.Entry<Integer,Double> entry: minSupItems.entrySet()){

            double count=(double)itemCountMap.get(entry.getKey());
            if(mis < 0 && count/tot >=entry.getValue() ){
                lvalues.add(entry.getKey());
                mis=entry.getValue();
            }else if(mis>0 && count/tot>=mis){
                lvalues.add(entry.getKey());
            }

        }
        System.out.println(lvalues);
        return lvalues;
    }

    private List<Sequence> getSecondLevelSequence(HashMap<Integer, Double> minSupItems, HashMap<Integer, Integer> itemCountMap, Set<Integer> lvalues, int tot, Double sdc) {
        List<Sequence> F2Values=new ArrayList<>();
        List<Sequence> sequenceTemp=new ArrayList<>();

        List<Integer> lvalueList=new ArrayList(lvalues);
        for(int i=0;i<lvalueList.size();i++){

            int elementI=lvalueList.get(i);
            double countFirst=(double)itemCountMap.get(elementI);
            if(countFirst/tot >= minSupItems.get(lvalueList.get(i))){
                for(int j=i+1;j<lvalueList.size();j++){
                    int elementJ=lvalueList.get(j);
                    double countSecond=(double)itemCountMap.get(elementJ);
                    if(countSecond/tot>=minSupItems.get(elementI) && Math.abs(minSupItems.get(elementI)-minSupItems.get(elementJ))<sdc){

                        //adding elements of type: <{x,y}>
                        List<Set<Integer>> l1SetList=new ArrayList();
                        l1SetList.add(new LinkedHashSet<>(Arrays.asList(elementI, elementJ)));

                        //adding elements of type: <{x},{y}>
                        Set<Integer> firstElementSet=new HashSet<>();
                        firstElementSet.add(elementI);
                        Set<Integer> secondElementSet=new HashSet<>();
                        secondElementSet.add(elementJ);

                        List<Set<Integer>> l2SetList=new ArrayList();
                        l2SetList.add(firstElementSet);
                        l2SetList.add(secondElementSet);

                        F2Values.add(new Sequence(l1SetList));
                        F2Values.add(new Sequence(l2SetList));
                    }
                }
            }
        }
        return F2Values;


    }
    private HashMap<Integer,Integer> getItemCountMap(Data data){
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
        return itemCountSeq;
    }

}
