import java.util.*;
import java.io.*;

public class Main {
    
    public static void main(String args[]) throws Exception {
    
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());

        // layer 수  0 -> 6 -> 12 -> 18 -> ...
        // layer 구간 1 -> 2~7-> 8~19 -> 20 ~37
        // n이 어디 layer 순서만 알면 최소 개수방 반환 가능 13:3레이어 58:레이어
        int numLayer = 0;
        int start = 1;
        int end = 1;
        int layerOrder = 1;

        while (n > end) {
            layerOrder++;
            numLayer += 6;
            end += numLayer;
        }

        System.out.println(layerOrder);
    }
}