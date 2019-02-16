import java.util.*;

public class Temp {



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
        int k = 3;
        List<Sequence> Ck =  MScandidate_gen_SPM(c2, new HashMap<>(sortedItemMinSup), tot, k);


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


    private List<Sequence> MScandidate_gen_SPM(List<Sequence> f2, HashMap<Integer, Double> minSupItems, int tot, int k){
        for(int i = 0; i < f2.size(); i++){
            for(int j = 0; j < f2.size(); j++){


                //select all pairs s1 and s2 from f(k-1)
                List<Set<Integer>> s1 = f2.get(i).sequenceData;
                List<Set<Integer>> s2 = f2.get(j).sequenceData;

                boolean firstElementSmallerThanRestCondition=true;
                boolean LastElementSmallerThanRestCondition=true;

                //anoop - check if minsup of first element of s1 is less than the rest of the elements
                boolean flag = false;
                int firstElementFromS1=s1.get(0).iterator().next();
                Double minsupFirstS1 = minSupItems.get(firstElementFromS1);
                for(Set<Integer> setFromS1:s1){
                    for(int itemsFromS1:setFromS1){
                        if(!flag){
                            flag = true;
                            continue;
                        }
                        if(minsupFirstS1 >= minSupItems.get(itemsFromS1)){
                            firstElementSmallerThanRestCondition=false;
                        }
                    }
                }

                //find last item of s2
                Set<Integer> lastset = s2.get(s2.size()-1);
                Iterator<Integer> it2 = lastset.iterator();
                int lastElementFromS2 = 0;
                while(it2.hasNext()){
                    lastElementFromS2 = it2.next();
                }


                //find minsup of last element
                Double minsupLastS2 = minSupItems.get(lastElementFromS2);
                int len = 0;

                //find len of s2 - (change to 'k')
                for(Set<Integer> setFromS2:s2){
                    for(int itemsFromS2:setFromS2)
                        len++;
                }

                //check if minsup of last element of s2 is least
                int count = 0;
                for(Set<Integer> setFromS2:s2){
                    for(int itemsFromS2:setFromS2){

                        if(count == len-1){
                            continue;
                        }

                        if(minSupItems.get(firstElementFromS1)>=minSupItems.get(itemsFromS2)){
                            LastElementSmallerThanRestCondition=false;
                            count++;
                        }
                    }
                }


                //Join part
                if(firstElementSmallerThanRestCondition){
                    List<Set<Integer>> c1 = joinPartOne(s1, s2, minsupFirstS1, minsupLastS2, minSupItems, k);
                }else if(LastElementSmallerThanRestCondition){

                }else{


                }


                //prune part



            }
        }

        return null;


    }

    private  List<Set<Integer>> joinPartOne(List<Set<Integer>> s1, List<Set<Integer>> s2, Double minsupFirstS1, Double minsupLastS2 , HashMap<Integer, Double> minSupItems, int k){
        boolean MSLastS2MSFirstS1 = false;

        if(minsupLastS2 > minsupFirstS1)
            MSLastS2MSFirstS1 = true;
        List<Integer> l1 = null;
        List<Integer> l2 = null;


        boolean equality = checkEqual(s1, s2);

        if(equality == true && MSLastS2MSFirstS1 == true){

        }else{
            return null;
        }

        return null;

    }

    private boolean checkEqual(List<Set<Integer>> s1, List<Set<Integer>> s2){
        if(s1.size() != s2.size()){
            return false;
        }else{

            for(int i = 0; i < s1.size();i++) {
                if (!s1.get(i).equals(s2.get(i)))
                    return false;
            }
        }
        return true;
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


















//    int lastElementFromS2=s2.get(s2.size()-1).
//
//
//
//            //all items in s1 and s2
//            List itemsInS1 = null;
//        List itemsInS2 = null;
//
//        for(int k = 0; k < s1.size(); k++){
//        Set<Integer> tempset = s1.get(k);
//        for(int l = 0; l < tempset.size(); l++){
//        //set does not maintain order
//        itemsInS1.add(tempset.get(l));
//        }
//        }
//
//        for(int k = 0; k < s2.size(); k++) {
//        Set<Integer> tempset = s2.get(k);
//        for (int l = 0; l < tempset.size(); l++) {
//        //set does not maintain order
//        itemsInS2.add(tempset.get(l));
//        }
//        }
//        //check if min of first in s1 less than all other
//        //and
//        //check if min of last in s2 less than all others
//        boolean misFirstS1Minimum = true;
//        boolean misLastS2minimum = true;
//
//        for(int k = 0; k < itemsInS1.size(); k++){
//        if(minSupItems.get(itemsInS1.get(0)) > minSupItems.get(itemsInS1.get(k))){
//        misFirstS1Minimum = false;
//        }
//        }
//
//        for(int k = 0; k < itemsInS2.size(); k++){
//        if(minSupItems.get(itemsInS1.get(itemsInS1.size())) > minSupItems.get(itemsInS1.get(k))){
//        misLastS2minimum = false;
//        }
//        }



