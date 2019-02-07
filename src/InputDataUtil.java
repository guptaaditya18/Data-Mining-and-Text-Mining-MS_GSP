import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InputDataUtil {



    public Data getDummyInput() {
        ArrayList<Sequence> sequences = new ArrayList<>();
        //sequences.add(new Sequence());
        Item it1=new Item("Bread");
        Item it2=new Item("Clothes");
        Item it3=new Item("Cheese");
        Item it4=new Item("Cake");
        Item it5=new Item("Milk");

        Set<Item> set1=new HashSet<>();
        set1.add((it1));
        set1.add(it2);

        Set<Item> set2=new HashSet<>();
        set2.add(it3);
        set2.add(it2);

        Set<Item> set3=new HashSet<>();
        set3.add((it4));
        set3.add(it5);
        set3.add(it5);

        Set<Item> set4=new HashSet<>();
        set4.add((it1));
        set4.add(it2);

        Set<Item> set5=new HashSet<>();
        set5.add((it1));
        set5.add(it2);

        Set<Item> set6=new HashSet<>();
        set6.add((it1));
        set6.add(it2);

        Set<Item> set7=new HashSet<>();
        set7.add((it1));
        set7.add(it2);




        List<Set<Item>> samples=new ArrayList<>();

        samples.add(set1);
        samples.add(set2);
        samples.add(set3);
        samples.add(set4);
        samples.add(set5);
        samples.add(set6);
        samples.add(set7);



        Sequence seq1=new Sequence(samples);
        Sequence seq2=new Sequence(samples);
        Sequence seq3=new Sequence(samples);
        Sequence seq4=new Sequence(samples);
        Sequence seq5=new Sequence(samples);
        List<Sequence> seq=new ArrayList<>();
        seq.add(seq1);
        seq.add(seq2);
        seq.add(seq3);
        seq.add(seq4);
        seq.add(seq5);



        Data data=new Data();

        data.setSequences(seq);
        List<ItemDetail> itemDetailList=new ArrayList<>();
        ItemDetail i1=new ItemDetail(it1,0.03);
        ItemDetail i2=new ItemDetail(it2,0.05);
        ItemDetail i3=new ItemDetail(it3,0.06);
        ItemDetail i4=new ItemDetail(it4,0.08);
        ItemDetail i5=new ItemDetail(it5,0.10);
        itemDetailList.add(i1);
        itemDetailList.add(i2);
        itemDetailList.add(i3);
        itemDetailList.add(i4);
        itemDetailList.add(i5);
        data.setItemDetails(itemDetailList);


        return data;
    }
}
