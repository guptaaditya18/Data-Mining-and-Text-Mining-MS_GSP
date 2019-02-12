import java.util.*;

public class MSGSPAlgo {

    public void getFinalSequence(Data data){
        List<HashMap<Sequence,Integer>> resultSeq=new ArrayList<>();
        int tot=data.getSequences().size();


        //Sort acc to MIS:
        TreeMap<Integer, Double> sortedItemMinSup = getSortedMapByvalue(data.getItemMinSup());


//        System.out.println("Sorted item min sup:");
//        System.out.println(sortedItemMinSup);

        HashMap<Integer, Integer> itemCountMap = getItemCountMap(data);
//        System.out.println("item count map");
//        System.out.println(itemCountMap);

        Set<Integer> lvalues=getLvalues(new HashMap<>(sortedItemMinSup),data,itemCountMap,tot);


        HashMap<Sequence,Integer> c1 = getFirstLevelSeq(lvalues, itemCountMap, new HashMap<>(sortedItemMinSup), tot);
        System.out.println("length 1:");
        Util.printSequence(c1);
        resultSeq.add(c1);

        HashMap<Sequence, Integer> c2 = getcandidateCount(getSecondLevelSequence(new HashMap<>(sortedItemMinSup), itemCountMap, lvalues, tot, data.getSDC()), data);
        HashMap<Sequence,Integer> f2=new LinkedHashMap<>();

        for(Map.Entry<Sequence,Integer> entry:c2.entrySet()){
            double min=-1;
            boolean flag=false;
            for(Set<Integer> sets: entry.getKey().getSequenceData()){
                min=getMinMisValue(entry.getKey(),data.getItemMinSup());
                double count=(double)entry.getValue();
                if(count/tot>=min) {
                    flag=true;
                }
            }
            if(flag) {
                f2.put(new Sequence(entry.getKey().getSequenceData()), entry.getValue());
            }
        }

        resultSeq.add(f2);
        System.out.println("\n length 2:");
        Util.printSequence(f2);
    }

    private double getMinMisValue(Sequence sequence, HashMap<Integer, Double> itemMinSup) {
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

    private HashMap<Sequence,Integer> getcandidateCount(List<Sequence> c2, Data data) {
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

    private boolean isFirstSubsetOfSecond(List<Set<Integer>> candidateSequence, List<Set<Integer>> dataSequence) {

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

    private HashMap<Sequence,Integer> getFirstLevelSeq(Set<Integer> lvalues, HashMap<Integer, Integer> itemCountMap, HashMap<Integer, Double> sortedItemMinSup,int tot) {
        HashMap<Sequence,Integer> F1Values=new LinkedHashMap<>();

        for(int i:lvalues){
            double count=(double)itemCountMap.get(i);
            if(count/tot >=sortedItemMinSup.get(i)){
                Set<Integer> set=new HashSet<>();
                set.add(i);
                List<Set<Integer>> l1SetList=new ArrayList();
                l1SetList.add(set);
                F1Values.put(new Sequence(l1SetList),itemCountMap.get(i));
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
        return lvalues;
    }

    private List<Sequence> getSecondLevelSequence(HashMap<Integer, Double> minSupItems, HashMap<Integer, Integer> itemCountMap, Set<Integer> lvalues, int tot, Double sdc) {
        List<Sequence> F2Values=new ArrayList<>();

        List<Integer> lvalueList=new ArrayList(lvalues);
        for(int i=0;i<lvalueList.size();i++){

            int elementI=lvalueList.get(i);
            double countFirst=(double)itemCountMap.get(elementI);
            if(countFirst/tot >= minSupItems.get(elementI)){
                for(int j=i+1;j<lvalueList.size();j++){
                    int elementJ=lvalueList.get(j);
                    double countSecond=(double)itemCountMap.get(elementJ);
                    if(countSecond/tot >=minSupItems.get(elementI) && Math.abs(minSupItems.get(elementJ)-minSupItems.get(elementI))<=sdc){

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
