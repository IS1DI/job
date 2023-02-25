package org.example;

import java.util.*;
import java.util.regex.Pattern;

public class DSU {

    private final HashMap<String,HashMap<Integer, Integer>> strings;// номер -> индекс в строке -> индекс группы
    private final ArrayList<Integer> indexes; // key - номер строки; val -> индекс группы

    private final Set<Integer> parents;
    private static final String splitter = ";";
    private static final Pattern pattern = Pattern.compile("\\s*\"+\\s*\"\\s*");

    DSU(){
        strings = new HashMap<>();
        indexes = new ArrayList<>();
        parents = new HashSet<>();
    }

    public void add(String str, int index){
        String[] split = str.split(splitter);
        indexes.add(index);
        parents.add(index);
        int temp = -1;
        for(int i = 0; i < split.length; i++) {
            if (!isNone(split[i])) {
                if (strings.containsKey(split[i])) {
                    if (strings.get(split[i]).containsKey(i)) {
                        if (temp == -1) {
                            temp = find(strings.get(split[i]).get(i));
                            indexes.set(index, temp);
                            parents.remove(index);
                        } else {
                            //union
                            int parent = find(strings.get(split[i]).get(i));
                            indexes.set(parent, temp);
                            parents.remove(parent);
                            //union
                        }
                    } else {
                        if (temp == -1) {
                            strings.get(split[i]).put(i, index);
                        } else {
                            strings.get(split[i]).put(i, temp);
                        }
                    }
                } else {
                    HashMap<Integer, Integer> map = new HashMap<>();
                    if (temp == -1) {
                        map.put(i, index);
                    } else {
                        map.put(i, temp);
                    }
                    strings.put(split[i], map);
                }
            }
        }
    }
    public int find(int index){
        do{
            index = indexes.get(index);
        }while(index!=indexes.get(index));
        return index;
    }
    public ArrayList<Integer> getList(){
        return indexes;
    }

    public Set<Integer> getParents(){
        return parents;
    }
    private static boolean isNone(String str){
        return pattern.matcher(str).matches();
    }

}
