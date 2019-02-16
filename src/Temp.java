















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





//    //testing - remove it
//    List<Sequence> f2Dash = new LinkedList<>(f2.keySet());
//    Set<Integer> s1 = new LinkedHashSet<Integer>();
//    List<Set<Integer>> lDash = new ArrayList<Set<Integer>>();
//        s1.add(4);
//                lDash.add(s1);
//                Set<Integer> s2 = new LinkedHashSet<Integer>();
//        s2.add(17);
//        lDash.add(s2);
//        Sequence sDash = new Sequence(lDash);
//        f2Dash.add(sDash);
//
//        System.out.println("f2Dash");
//        Util.printSequence(f2Dash);
//
//        Set<Integer> s3 = new LinkedHashSet<Integer>();
//        List<Set<Integer>> lDash2 = new ArrayList<Set<Integer>>();
//        s3.add(3);
//        lDash2.add(s3);
//        Set<Integer> s4 = new LinkedHashSet<Integer>();
//        s4.add(17);
//        lDash2.add(s4);
//
//        Sequence sDash2 = new Sequence(lDash2);
//        f2Dash.add(sDash2);
//        System.out.println("f2Dash");
//        Util.printSequence(f2Dash);