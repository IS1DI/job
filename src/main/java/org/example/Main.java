package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        File file;
        try {
            file = parseArgs(args);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        ArrayList<Set<String>> list = Parser.group(Parser.parse(file));
        System.out.println(list.size());
        List<Set<String>> g = list.stream()
                .sorted((o1, o2) -> Integer.compare(o2.size(), o1.size()))
                .toList();
        System.out.println(g.size());
        System.out.printf("time: %d seconds", (System.currentTimeMillis() - t1) / 1000);

        try (FileWriter fileWriter = new FileWriter("test.txt")) {
            fileWriter.write("Количество групп с более чем одним элементом - " + g.stream().filter(x -> x.size() > 1).count() + "\n");
            IntStream.range(0, g.size()).forEach(
                    i -> {
                        try {
                            fileWriter.write("Группа " + i + 1 + "\n");
                            g.get(i).forEach(x -> {
                                try {
                                    fileWriter.write(x + "\n");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.printf("\ntime: %d seconds", (System.currentTimeMillis() - t1) / 1000);
    }
    public static File parseArgs(String[] args) throws FileNotFoundException {
        File file;
        Pattern pattern = Pattern.compile("^.*\\.txt$");
        file = new File(Arrays.stream(args)
                .filter(i -> pattern.matcher(i).matches())
                .findFirst()
                .orElseThrow(FileNotFoundException::new));
        if(file.exists())
            return file;
        else
            throw new FileNotFoundException();
    }
}