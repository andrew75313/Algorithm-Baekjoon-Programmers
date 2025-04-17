import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String args[]) throws Exception {

        // INPUT SETTING
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());

        List<String> wordList = new ArrayList<>();
        for(int i =0; i<n; i++) {
            wordList.add(reader.readLine());
        }
        
        int maxPrefix = 0;
        String[] result = new String[2];

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                String word1 = wordList.get(i);
                String word2 = wordList.get(j);

                int len = Math.min(word1.length(), word2.length());
                int prefixLen = 0;
                for (int k = 0; k < len; k++) {
                    if (word1.charAt(k) == word2.charAt(k)) {
                        prefixLen++;
                    } else {
                        break;
                    }
                }

                if (prefixLen > maxPrefix) {
                    maxPrefix = prefixLen;
                    result[0] = word1;
                    result[1] = word2;
                }
            }
        }

        System.out.println(result[0]);
        System.out.println(result[1]);


    }
}