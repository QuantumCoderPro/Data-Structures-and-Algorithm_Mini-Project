import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> wordsList = new ArrayList<String>(); // List to store unique words
        ArrayList<Integer> hashList = new ArrayList<Integer>(); // List to store corresponding hash keys
        ArrayList<Integer> newHashList = new ArrayList<Integer>();
        ArrayList<Integer> probelist = new ArrayList<Integer>();
        // Inserting the Elements

        try (BufferedReader buffer = new BufferedReader(
                new FileReader("C:\\Users\\Zakir\\Desktop\\EEX4465 MP\\file7.txt"))) {
            String str;

            while ((str = buffer.readLine()) != null) {
                String[] words = str.replaceAll("[^a-zA-Z ]", "").split(" ");

                for (String word : words) {

                    int hash = 0;
                    for (int i = 0; i < word.length(); i++) {
                        char c = word.charAt(i);
                        if (c >= 'a' && c <= 'z') {
                            hash += (int) c - 97;
                        } else if (c >= 'A' && c <= 'Z') {
                            hash += (int) c - 39;
                        }
                    }

                    int index = wordsList.indexOf(word);
                    if (index == -1) {

                        wordsList.add(word);
                        hashList.add(hash);
                    } else {
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        wordsHKFileWrite("C:\\Users\\Zakir\\Desktop\\EEX4465 MP\\wordsHK7.txt", wordsList, hashList);
        QuadraticFunction(167, 1, 1, hashList, wordsList, probelist, newHashList);
    }



    public static void wordsHKFileWrite(String outputFile, ArrayList<String> wordList, ArrayList<Integer> newHashValues) {
        try {

            FileWriter writer = new FileWriter(outputFile);
            writer.write("Word index j\tWord\tHash key, kj\n");
            for (int i = 0; i < wordList.size(); i++) {
                writer.write(i + "\t\t" + wordList.get(i) + "\t\t" + newHashValues.get(i) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void QuadraticFunction(int tableSize, int C1, int C2, ArrayList<Integer> hashList,
                                         ArrayList<String> wordsList, ArrayList<Integer> probelist, ArrayList<Integer> newHashList) {
        try {
            FileWriter writer = new FileWriter("C:\\Users\\Zakir\\Desktop\\EEX4465 MP\\wordsQHK7.txt");
            writer.write("Word index j \t Word \t Hash key, kj \t\t   New hash key, Qkj\tQuadratic h-f, h(j, i)\n");
            for (int j = 0; j < wordsList.size(); j++) {
                int kj = hashList.get(j);
                int newHash = kj;
                int i = 1;
                int hji = newHash % tableSize;
                while (probelist.contains(hji) && probelist.indexOf(hji) != j) {
                    hji = (kj + C1 * (i * i) + C2 * i) % tableSize;
                    newHash = (kj + C1 * (i * i) + C2 * i);
                    i++;
                }
                newHashList.add(newHash);
                probelist.add(hji);
                writer.write(j + "\t\t" + wordsList.get(j) + "\t\t" + kj + "\t\t\t" + newHashList.get(j) + "\t\t\t"
                        + probelist.get(j) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}