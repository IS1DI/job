package org.example;
import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

public class Parser {
    private static final Pattern pattern = Pattern.compile("^\"[^\"]*\"$");
    private static final String splitter = ";";
    /*a*/
    public static ArrayList<String> parse(File file) {
        ArrayList<String> outputString = new ArrayList<>();
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNext()) {
                String string = scan.next();
                if(isCorrectString(string))
                    outputString.add(string);
            }
        } catch (Exception e){

        }
        return outputString;

    }
    private static boolean isCorrectString(String str){

        String[] split = str.split(splitter);
        for (String i: split) {
            if(!pattern.matcher(i).matches()){
                return false;
            }
        }
        return true;
    }

    public static ArrayList<Set<String>> group(ArrayList<String> strings){
        DSU dsu = new DSU();
        for(int i = 0; i< strings.size(); i++){
            dsu.add(strings.get(i),i);

        }
        List<Integer> list = dsu.getList();
        HashMap<Integer,Set<String>> map = new HashMap<>();
        for(int i = 0; i < strings.size(); i++){
            int temp = dsu.find(list.get(i));
            if(map.containsKey(temp)){
                map.get(temp).add(strings.get(i));
            }else{
                Set<String> set = new HashSet<>();
                set.add(strings.get(i));
                map.put(temp,set);
            }
        }
        return new ArrayList<>(map.values());

    }

}

