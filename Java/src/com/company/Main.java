package com.company;
import java.io.*;
import java.util.*;

public class Main {
    // ngrams function
    public static HashMap<String, Integer> ngrams(int n, String str) {
        String word;
        HashMap<String, Integer> frequency = new HashMap<String, Integer>();
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - n + 1; i++) {
            word = concat(words, i, i + n);
            if (frequency.containsKey(word))
                frequency.put(word, frequency.get(word) + 1);
            else
                frequency.put(word, 1);
        }
        return sortedHashMap(frequency);
    }
    public static String concat(String[] words, int start, int end) {
        String sb = "";
        for (int i = start; i < end; i++)
            if(i>start)
                sb= sb + " "+words[i];
            else
                sb=sb+""+words[i];
        return sb;
    }
    //sort items of value
    public static HashMap <String, Integer> sortedHashMap(HashMap<String, Integer> map){
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        ArrayList<Integer> list = new ArrayList<>(map.values());
        Collections.sort(list, Collections.reverseOrder());
        for (int i=0; i<100; i++) {
            int num=list.get(i);
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue().equals(num)) {
                    sortedMap.put(entry.getKey(), num);
                }
            }
        }
        return sortedMap;
    }
    //eading files and separating punctuation
    public static String stringOperations (){
        String filename []={"UNUTULMUŞ DİYARLAR.txt","BİLİM İŞ BAŞINDA.txt","BOZKIRDA.txt","DEĞİŞİM.txt","DENEMELER.txt"};
        String allString="";
        for(String file:filename) {
            String line = null;
            try {
                BufferedReader bf = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream(file), "UTF8"));
                line = bf.readLine();
                while (line != null) {
                    allString = allString + line;
                    line = bf.readLine();
                }
                bf.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        allString = allString.replaceAll("(?<=\\S)(?:(?<=\\p{Punct})|(?=\\p{Punct}))(?=\\S)", " ").replaceAll("\\s+", " ");
        allString = allString.replaceAll("[»,«,-,“,’]", " $0 ").replaceAll("\\s+", " ");
        allString = allString.replace("! . .", "!..").replace("? . .", "?..");
        allString = allString.replace("( ? )", "(?)").replace("( ! )", "(!)").replace("( * )", "(*)");
        allString = allString.replace(". . .", "...").replace(". .", "..");
        allString = allString.toLowerCase();
        return allString;
    }

    public static void runtime (int n, String allString){
        long startTime = System.nanoTime();
        HashMap<String, Integer> frequency = ngrams(n,allString);
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(n+"-Gram Runtime: "+totalTime / 1000000+ " miliseconds");
        for(int i=0; i<100; i++){
            System.out.println(i+1+".VALUE --> "+frequency.keySet().toArray()[i]+" FREQUENCY --> "+frequency.values().toArray()[i]);
        }
        System.out.println();
    }


    public static void main(String[] args) throws IOException {

        String allString=stringOperations();
         runtime(1,allString);
         runtime(2,allString);
         runtime(3,allString);

    }
}
