import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class InputDataUtil {


    public Data parseTxt(){
//        System.out.println("hello");


        List<Sequence> seq=new ArrayList<>();

        try{
        BufferedReader br = new BufferedReader(new FileReader("Data/src/sample.txt"));
            String line;


            while((line = br.readLine()) != null) {
                int len1 = line.length();
                String seq1 = line.substring(2, len1 - 2);
                List<String> seq2 = Arrays.asList(seq1.split("}\\{"));
                int len2 = seq2.size();

                List<Set<Item>> seq3=new ArrayList<>();

                for (int i = 0; i < len2; i++) {
                    List<String> items = Arrays.asList(seq2.get(i).split(", "));
                    Set<Item> set1=new HashSet<>();

                    int len3 = items.size();
                    for(int j = 0; j < len3; j++){
                        Item it = new Item(items.get(j));
                        set1.add(it);
                    }
                    seq3.add(set1);

                }

                Sequence seq4=new Sequence(seq3);
                seq.add(seq4);
            }

            }catch (FileNotFoundException e){
            System.out.println("file not found");
        }
        catch( IOException e){
            System.out.println("IO Exception");
        }

        HashMap<Integer, Double> mis = new HashMap<>();
        Double sdc = 0.0;
        try{
            BufferedReader br = new BufferedReader(new FileReader("Data/src/sample.txt"));
            String line;
            while((line = br.readLine()) != null) {
                if(line.charAt(0) == 'S'){
                    int len1 = line.length();
                    String seq1 = line.substring(6, len1);
                    Double db = Double.parseDouble(seq1);
                    sdc = db;

                }else {
                    int len1 = rwline.length();
                    String seq1 = line.substring(4, len1 - 1);
                    List<String> seq2 = Arrays.asList(seq1.split("\\) = "));

                    Integer in = Integer.parseInt(seq2.get(0));
                    Double db = Double.parseDouble(seq2.get(1));
                    mis.put(in, db);
                }
            }

        }catch (FileNotFoundException e){
            System.out.println("file not found");
        }
        catch( IOException e){
            System.out.println("IO Exception");
        }

        Data data = new Data(seq,mis,sdc);

        return data;
    }


}
