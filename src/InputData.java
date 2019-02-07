import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InputData {
    public static List<Sequence> getDummyInput() {
        List<Sequence> sequences=new ArrayList<>();
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
        set1.add(it3);
        set1.add(it2);

        Set<Item> set3=new HashSet<>();
        set1.add((it4));
        set1.add(it5);
        set1.add(it5);

        Set<Item> set4=new HashSet<>();
        set1.add((it1));
        set1.add(it2);

        Set<Item> set5=new HashSet<>();
        set1.add((it1));
        set1.add(it2);

        Set<Item> set6=new HashSet<>();
        set1.add((it1));
        set1.add(it2);

        Set<Item> set7=new HashSet<>();
        set1.add((it1));
        set1.add(it2);




        List<Set<Item>> samples=new ArrayList<>();

        samples.add(set1);
        samples.add(set2);
        samples.add(set3);
        samples.add(set4);
        samples.add(set5);
        samples.add(set6);
        samples.add(set7);



        Sequence seq=new Sequence(samples);

        return new ArrayList<>();
    }
}
