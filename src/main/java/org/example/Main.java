package org.example;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        File file = new File("lng.txt");

        ArrayList<Set<String>> list = Parser.group(Parser.parse(file));
        System.out.println(list.size());
        List<Set<String>> g = list.stream()
                .filter(x -> x.size() > 1)
                .sorted((o1, o2) -> Integer.compare(o2.size(), o1.size()))
                .toList();
        System.out.println(g.size());
        System.out.printf("time: %d seconds", (System.currentTimeMillis() - t1)/1000);
        new Scanner(System.in).nextLine();
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("New Group %d\n", i);
            for (String j : list.get(i)) {
                System.out.println(j);
            }
            new Scanner(System.in).nextLine();
        }
        System.out.printf("time: %d seconds", (System.currentTimeMillis() - t1)/1000);
    }
}