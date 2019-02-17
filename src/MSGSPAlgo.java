import java.util.*;

public class MSGSPAlgo {

    public void getFinalSequence(Data data){
        List<HashMap<Sequence,Integer>> resultSeq=new ArrayList<>();
        //Util.printSequence(data.getSequences());
        int tot=data.getSequences().size();


        System.out.println(tot);

        //Sort acc to MIS:
        TreeMap<Integer, Double> sortedItemMinSup = Util.getSortedMapByvalue(data.getItemMinSup());


        System.out.println("Sorted item min sup:");
        System.out.println(sortedItemMinSup);

        HashMap<Integer, Integer> itemCountMap = Util.getItemCountMap(data,data.getItemMinSup());
        System.out.println("item count map");
        System.out.println(itemCountMap);

        Set<Integer> lvalues=getLvalues(new HashMap<>(sortedItemMinSup),data,itemCountMap,tot);


        HashMap<Sequence,Integer> c1 = getFirstLevelSeq(lvalues, itemCountMap, new HashMap<>(sortedItemMinSup), tot);
        //System.out.println("length 1 total number :"+c1.size());
        //Util.printSequence(c1);
        resultSeq.add(c1);
        int k=1;

        while(!resultSeq.get(k-1).isEmpty()){

            HashMap<Sequence, Integer> candidateCountMap = new HashMap<>();
            if(k==1){
                List<Sequence> c2Generation = getSecondLevelSequence(new HashMap<>(sortedItemMinSup), itemCountMap, lvalues, tot, data.getSDC());
                //Util.printSequence(c2Generation);
                candidateCountMap = Util.getcandidateCount(c2Generation, data);
            }
            else if(k==2){

                List<Sequence> CkGeneration = MScandidate_gen_SPM(new LinkedList<>(resultSeq.get(k-1).keySet()), new HashMap<>(sortedItemMinSup), tot, k-1);
                candidateCountMap = Util.getcandidateCount(CkGeneration, data);
            }


            HashMap<Sequence, Integer> Fk = Util.convertCandidatesToFinalSequence(candidateCountMap, data, tot);

            resultSeq.add(Fk);
            System.out.println("count of Sequence of length : "+k+ " is: "+resultSeq.get(k-1).size());
            Util.printSequence(resultSeq.get(k-1));
            k++;
        }

//        List<Sequence> c2Generation = getSecondLevelSequence(new HashMap<>(sortedItemMinSup), itemCountMap, lvalues, tot, data.getSDC());
//        HashMap<Sequence, Integer> c2 = getcandidateCount(c2Generation, data);
//
//
//        //Util.printSequence(c2);
//        System.out.println(c2.size());
//


        /*// Testing Subset Function.
        Set<Integer> testSet=new LinkedHashSet<>(Arrays.asList(7,19));
        List<Set<Integer>> testList=new ArrayList<>();
        testList.add(testSet);


        System.out.println();
        for(Sequence sequence: data.getSequences()){

            List<Set<Integer>> dataSequence = sequence.getSequenceData();
            //System.out.print("data sequence:"+dataSequence);

                //System.out.print("\t candidate sequence:"+candidateSequence.getSequenceData());
                if(isFirstSubsetOfSecond(testList,dataSequence)){

                    System.out.println("Yo man"+dataSequence);
                }



        }
        */


//        //line 17 from MS-GSP page:46
////
//
//        System.out.println("\n length 2 total number :"+f2.size());
//        //Util.printSequence(f2);
//
//
//        //int k = 3;
//
////        int k = 3;
////        List<Sequence> Ck = new ArrayList<Sequence>();
////        Ck =  MScandidate_gen_SPM(new LinkedList<>(f2.keySet()), new HashMap<>(sortedItemMinSup), tot, k);
////        System.out.println("cksize"+Ck.size());
////        HashMap<Sequence, Integer> c2count = getcandidateCount(Ck, data);
////        System.out.println(c2count.size());
////        Util.printSequence(c2count);
//
//
//        List<Sequence> Ck = MScandidate_gen_SPM(new LinkedList<>(f2.keySet()), new HashMap<>(sortedItemMinSup), tot, k);
////        System.out.println("cksize"+Ck.size());
//        HashMap<Sequence, Integer> c3count = getcandidateCount(Ck, data);
////        System.out.println(c3count.size());
////        Util.printSequence(c3count);
//
//        HashMap<Sequence,Integer> f3=new LinkedHashMap<>();
//        //Util.printSequence(c2);
//        //System.out.println(c2.size());
//        //line 17 from MS-GSP page:46
//
//        for(Map.Entry<Sequence,Integer> entry:c3count.entrySet()){
//            double min=-1;
//            boolean flag=false;
//            for(Set<Integer> sets: entry.getKey().getSequenceData()){
//                min=Util.getMinMisValue(entry.getKey(),data.getItemMinSup());
//                double count=(double)entry.getValue();
//                if(count/tot>=min) {
//                    flag=true;
//                }
//            }
//            if(flag) {
//                f3.put(new Sequence(entry.getKey().getSequenceData()), entry.getValue());
//            }
//        }
//
//        resultSeq.add(f3);
//        System.out.println("\n length 3 total number :"+f3.size());
//        Util.printSequence(f3);
//

    }



    //Aditya Gupta

        private List<Sequence> MScandidate_gen_SPM(List<Sequence> f2, HashMap<Integer, Double> minSupItems, int tot, int k){

            List<Sequence> ck = new ArrayList<Sequence>();
            for(int i = 0; i < f2.size(); i++){
                for(int j = 0; j < f2.size(); j++){

                    //select all pairs s1 and s2 from f(k-1)
                    List<Set<Integer>> s1 = f2.get(i).sequenceData;
                    List<Set<Integer>> s2 = f2.get(j).sequenceData;

                    boolean firstElementSmallerThanRestCondition=true;
                    boolean LastElementSmallerThanRestCondition=true;
                    int firstElementFromS1=s1.get(0).iterator().next();
                    int firstElementFromS2=s2.get(0).iterator().next();

                    //anoop - check if minsup of first element of s1 is less than the rest of the elements
                    boolean flag = false;
                    Double minsupFirstS1 = minSupItems.get(firstElementFromS1);
                    for(Set<Integer> setFromS1:s1){
                        for(int itemsFromS1:setFromS1){
                            if(!flag){
                                flag = true;
                                continue;
                            }
                            if(minsupFirstS1 >= minSupItems.get(itemsFromS1))
                                firstElementSmallerThanRestCondition=false;
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
                    //find len of s2 - (change to 'k-1')
                    /*for(Set<Integer> setFromS2:s2){
                        for(int itemsFromS2:setFromS2)
                            len++;
                    }*/

                    //check if minsup of last element of s2 is least
                    int count = 0;
                    for(Set<Integer> setFromS2:s2){
                        for(int itemsFromS2:setFromS2){
                            count++;
                            if(count == k-2)
                                continue;
                            if(minSupItems.get(lastElementFromS2)>=minSupItems.get(itemsFromS2)){
                                LastElementSmallerThanRestCondition=false;

                            }
                        }
                    }

                    //Join part
                    if(firstElementSmallerThanRestCondition){

                        if(checkEqualFunc1(s1,s2)) {
                            ck.addAll(joinPartOne(s1, s2, firstElementFromS1, minsupFirstS1, lastElementFromS2, minsupLastS2, minSupItems, k));
                        }
                    }else if(LastElementSmallerThanRestCondition){

                        if(checkEqualFunc2(s1, s2)){
                            ck.addAll(joinPartTwo(s1, s2, firstElementFromS1 , minsupFirstS1, firstElementFromS2 ,minsupLastS2, minSupItems, k));
                        }

                    }else{

                        if(checkEqualFunc3(s1, s2)){
                            System.out.println("Success");
                            ck.addAll(joinPartOne(s1, s2, firstElementFromS1, minsupFirstS1, lastElementFromS2, minsupLastS2, minSupItems, k));

                        }else if(checkEqualFunc4(s1, s2)){
                            ck.addAll(joinPartTwo(s1, s2, firstElementFromS1 , minsupFirstS1, firstElementFromS2 ,minsupLastS2, minSupItems, k));

                        }
                    }

                    //prune part

                }
            }
//            for (Sequence seqfromck:ck){
//                System.out.println(seqfromck.sequenceData);
//            }
            return ck;


        }
    //associated with joinpart2
    private boolean checkEqualFunc4(List<Set<Integer>> s1, List<Set<Integer>> s2) {
        //Copy s1 and s2 to list s1Dash and s2Dash
        List<List<Integer>> s1Dash = Util.setToList(s1);
        List<List<Integer>> s2Dash = Util.setToList(s2);

        //remove last from s1
        if(s1Dash.get(s1Dash.size()-1).size() == 1){
            s1Dash.remove(s1Dash.size()-1);
        }else{
            s1Dash.get(s1Dash.size()-1).remove((s1Dash.get(s1Dash.size()-1).size())-1);
        }


        //remove 1st from s2
        if(s2Dash.get(0).size()>1){
            s2Dash.get(0).remove(0);
        }else{
            s2Dash.remove(0);
        }

        if(s1Dash.equals(s2Dash))
            return true;

        return false;

    }

    //associated with join part1
    private boolean checkEqualFunc3(List<Set<Integer>> s1, List<Set<Integer>> s2) {
        System.out.println("yes");
        //Copy s1 and s2 to list s1Dash and s2Dash
        List<List<Integer>> s1Dash = Util.setToList(s1);
        List<List<Integer>> s2Dash = Util.setToList(s2);
        System.out.println(s1+"\t"+s2 +" Before removal");


        //remove 1st from s1
        if(s1Dash.get(0).size()>1){
            s1Dash.get(0).remove(0);
        }else{
            s1Dash.remove(0);
        }

        //remove last from s2
        if(s2Dash.get(s2Dash.size()-1).size() == 1){
            s2Dash.remove(s2Dash.size()-1);
        }else{
            s2Dash.get(s2Dash.size()-1).remove((s2Dash.get(s2Dash.size()-1).size())-1);
        }
        System.out.println(s1Dash+"\t"+s2Dash +" After removal");

        if(s1Dash.equals(s2Dash))
            return true;

        return false;

    }

    private  List<Sequence> joinPartOne( List<Set<Integer>> s1, List<Set<Integer>> s2, int firstElementFromS1, Double minsupFirstS1, int lastElementFromS2, Double minsupLastS2, HashMap<Integer, Double> minSupItems, int k){
            List<Sequence> cklocal = new ArrayList<Sequence>();

            boolean MSLastS2MSFirstS1 = false;

            if(minsupLastS2 > minsupFirstS1)
                MSLastS2MSFirstS1 = true;


            //find last item of s1, used multiple times below
            Iterator<Integer> it = s1.get(s1.size()-1).iterator();
            int lastElementFromS1 = 0;
            while(it.hasNext()){
                lastElementFromS1 = it.next();
            }

            if(minsupLastS2 > minsupFirstS1){

                //if the last item l in s2 is a separate element then
                if(s2.get(s2.size()-1).size() == 1){

                    List<Set<Integer>> c1temp = new ArrayList<Set<Integer>>();
                    for (Set<Integer> setFroms1:s1){
                        Set<Integer> stemp = new LinkedHashSet<Integer>();
                        for(Integer intfromset:setFroms1)
                            stemp.add(intfromset);
                        c1temp.add(stemp);
                    }
                    c1temp.add(s2.get(s2.size()-1));
                    Sequence c1 = new Sequence(c1temp);
                    cklocal.add(c1);



                    //if (the length and the size of s1 are both 2) AND (the last item of s2 is
                    //greater than the last item of s1) then
                    if ((s1.size() == 2 && s1.get(0).size()==1) && lastElementFromS2 > lastElementFromS1 ){

                        List<Set<Integer>> c2temp = new ArrayList<Set<Integer>>();
                        for (Set<Integer> setFroms1:s1){
                            Set<Integer> stemp = new LinkedHashSet<Integer>();
                            for(Integer intfromset:setFroms1)
                                stemp.add(intfromset);
                            c2temp.add(stemp);
                        }

                        c2temp.get(c2temp.size()-1).add(lastElementFromS2);
                        Sequence c2 = new Sequence(c2temp);
                        cklocal.add(c2);
                    }

                //else if ((the length of s1 is 2 and the size of s1 is 1) AND (the last item
                //of s2 is greater than the last item of s1)) OR (the length of s1
                //is greater than 2) then
                }else if (((s1.size()==1 && s1.get(0).size() == 2) && (lastElementFromS2 > lastElementFromS1)) || k>3){
                    List<Set<Integer>> c2temp = new ArrayList<Set<Integer>>();
                    for (Set<Integer> setFroms1:s1){
                        Set<Integer> stemp = new LinkedHashSet<Integer>();
                        for(Integer intfromset:setFroms1)
                            stemp.add(intfromset);
                        c2temp.add(stemp);
                    }

                    c2temp.get(c2temp.size()-1).add(lastElementFromS2);
                    Sequence c2 = new Sequence(c2temp);
                    cklocal.add(c2);
                }
            }
            return cklocal;
        }

        private boolean checkEqualFunc1(List<Set<Integer>> s1, List<Set<Integer>> s2){

            //Copy s1 and s2 to list s1Dash and s2Dash
            List<List<Integer>> s1Dash = Util.setToList(s1);
            List<List<Integer>> s2Dash = Util.setToList(s2);

            //remove 2nd from s1
            if(s1Dash.get(0).size() > 1)
                s1Dash.get(0).remove(1);
            else if (s1Dash.get(1).size() == 1)
                s1Dash.remove(1);
            //remove last from s2
            if(s2Dash.get(s2.size()-1).size() == 1)
                s2Dash.remove(s2Dash.size()-1);
            else
                s2Dash.get(s2Dash.size()-1).remove((s2Dash.get(s2Dash.size()-1).size()) - 1);

            //checkEquality
            if(s1Dash.equals(s2Dash))
                return true;

            return false;
        }

        private  List<Sequence> joinPartTwo( List<Set<Integer>> s1, List<Set<Integer>> s2, int firstElementFromS1, Double minsupFirstS1, int firstElementFromS2, Double minsupLastS2, HashMap<Integer, Double> minSupItems, int k){

            List<Sequence> cklocal = new ArrayList<Sequence>();
            boolean MSLastS2MSFirstS1 = false;


            //find last item of s1, used multiple times below
            Iterator<Integer> it = s1.get(s1.size()-1).iterator();
            int lastElementFromS1 = 0;
            while(it.hasNext())
                lastElementFromS1 = it.next();

            //Candidate sequences are generated by prepending first item of s1 with s2
            //MIS value of the first item of s1 is greater than that of the last item of s2.
            if(minsupLastS2 <= minsupFirstS1){

                //if the first item l in s1 is a separate element then
                if(s1.get(0).size() == 1){

                    List<Set<Integer>> c1temp = new ArrayList<Set<Integer>>();
                    for (Set<Integer> setFroms1:s2){
                        Set<Integer> stemp = new LinkedHashSet<Integer>();
                        for(Integer intfromset:setFroms1)
                            stemp.add(intfromset);
                        c1temp.add(stemp);
                    }
                    c1temp.add(0, s1.get(0));
                    Sequence c1 = new Sequence(c1temp);
                    cklocal.add(c1);

                    //if (the length and the size of s1 are both 2) AND (the last item of s2 is
                    //greater than the last item of s1) then
                    if ((s2.size() == 2 && s2.get(0).size()==1) && firstElementFromS1 > firstElementFromS1 ){

                        List<Set<Integer>> c2temp = new ArrayList<Set<Integer>>();
                        for (Set<Integer> setFroms1:s2){
                            Set<Integer> stemp = new LinkedHashSet<Integer>();
                            for(Integer intfromset:setFroms1)
                                stemp.add(intfromset);
                            c2temp.add(stemp);
                        }
                        //add l in beginning of c2
                        Set<Integer> newBeginning = new LinkedHashSet<Integer>();
                        newBeginning.add(firstElementFromS2);
                        for(Integer intfromoldBeginning:s1.get(0))
                            newBeginning.add(intfromoldBeginning);

                        //change first element of c2
                        c2temp.remove(0);
                        c2temp.add(0, newBeginning);
                        Sequence c2 = new Sequence(c2temp);
                        cklocal.add(c2);
                    }

                //else if ((the length of s1 is 2 and the size of s1 is 1) AND (the last item
                //of s2 is greater than the last item of s1)) OR (the length of s1
                //is greater than 2) then
                }else if (((s2.size()==1 && s2.get(0).size() == 2) && (firstElementFromS1 > firstElementFromS1)) || k>3){

                    List<Set<Integer>> c2temp = new ArrayList<Set<Integer>>();
                    for (Set<Integer> setFroms1:s2){
                        Set<Integer> stemp = new LinkedHashSet<Integer>();
                        for(Integer intfromset:setFroms1)
                            stemp.add(intfromset);
                        c2temp.add(stemp);
                    }

                    //add l in beginning of c2
                    Set<Integer> newBeginning = new LinkedHashSet<Integer>();
                    newBeginning.add(firstElementFromS2);
                    for(Integer intfromoldBeginning:s1.get(0)){
                        newBeginning.add(intfromoldBeginning);
                    }
                    //change first element of c2
                    c2temp.remove(0);
                    c2temp.add(0, newBeginning);
                    Sequence c2 = new Sequence(c2temp);
                    cklocal.add(c2);
                }

            }
            return cklocal;
        }

        private boolean checkEqualFunc2(List<Set<Integer>> s1, List<Set<Integer>> s2){

        //Copy s1 and s2 to list s1Dash and s2Dash
        List<List<Integer>> s1Dash = Util.setToList(s1);
        List<List<Integer>> s2Dash = Util.setToList(s2);

        //remove first from s1
        if(s1Dash.get(0).size() == 1)
            s1Dash.remove(0);
        else if (s1Dash.get(0).size() > 1)
            s1Dash.get(0).remove(0);

        //remove second last from s2
        if(s2Dash.get(s2.size()-1).size() > 1)
            s2Dash.get(s2Dash.size()-1).remove((s2Dash.get(s2Dash.size()-1).size())-2);
        else if(s2Dash.get(s2Dash.size() - 2).size() == 1)
            s2Dash.remove((s2Dash.size()) - 2);
        else{
            s2Dash.get(s2Dash.size()-2).remove(s2Dash.get(s2Dash.size()-2).size()-2);
        }

        //checkEquality
        if(s1Dash.equals(s2Dash))
            return true;

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
        List<Sequence> F2Values = new ArrayList<>();

        List<Integer> lvalueList = new ArrayList(lvalues);

        for (int i = 0; i < lvalueList.size(); i++) {

            int elementI = lvalueList.get(i);
            double firstSup = (double) itemCountMap.get(elementI) / (double) tot;

            if (firstSup >= minSupItems.get(elementI)) {
                for (int j = 0; j < lvalueList.size(); j++) {
                    int elementJ = lvalueList.get(j);
                    double secondSup = (double) itemCountMap.get(elementJ) / (double) tot;

                    if (secondSup >= minSupItems.get(elementI)
                            && Math.abs(secondSup - firstSup) <= sdc) {


                        //adding elements of type: <{x,y}>
                        if (elementI != elementJ) {
                            List<Set<Integer>> l1SetList = new ArrayList();
                            l1SetList.add(new LinkedHashSet<>(Arrays.asList(elementI, elementJ)));
                            F2Values.add(new Sequence(l1SetList));
                        }

                        //adding elements of type: <{x},{y}>
                        Set<Integer> firstElementSet = new LinkedHashSet<>();
                        firstElementSet.add(elementI);
                        Set<Integer> secondElementSet = new LinkedHashSet<>();
                        secondElementSet.add(elementJ);

                        List<Set<Integer>> l2SetList = new ArrayList();
                        l2SetList.add(firstElementSet);
                        l2SetList.add(secondElementSet);
                        F2Values.add(new Sequence(l2SetList));


                        //adding elements of tupe:<{y},{x}>
                        List<Set<Integer>> l3SetList=new ArrayList();
                        l3SetList.add(secondElementSet);
                        l3SetList.add(firstElementSet);
                        F2Values.add(new Sequence(l3SetList));


//                        //adding elements of type: <{x}{x}>
//                        List<Set<Integer>> l4SetList=new ArrayList();
//                        l4SetList.add(firstElementSet);
//                        l4SetList.add(firstElementSet);
//                        F2Values.add(new Sequence(l4SetList));


                    }

                }
            }
        }
        return Util.removeDuplicates(F2Values);
    }



}
