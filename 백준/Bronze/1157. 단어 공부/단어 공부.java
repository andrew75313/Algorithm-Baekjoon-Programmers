import java.util.*;
import java.io.*;

public class Main {

    public static void main(String args[]) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String word = String.valueOf(reader.readLine()).toLowerCase();

        Map<Character, Integer> map = new HashMap<>();

        for(char c : word.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        Character result = ' ';
        int max = 0;
        for(Character c : map.keySet()) {
            int numChar = map.get(c);
            // 같은 갯수 오류 검증
            if(numChar == max) {
                result = '?';
                continue;
            }

            if(numChar > max) {
                result = c;
                max = map.get(c);
            }
        }

        System.out.println(Character.toUpperCase(result));
    }
}