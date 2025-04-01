import java.io.*;
import java.util.*;

public class Main {

    public static void main(String args[]) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        String[] inputArr = input.split(" ");

        int tableLength = Integer.parseInt(inputArr[0]);
        int distance = Integer.parseInt(inputArr[1]);

        input = reader.readLine();
        // 배열로 변환
        char[] table = input.toCharArray();

        int total = 0;

        // 사람(P)을 기준으로 거리 K 이내 햄버거 탐색 하면서 먹을 수 있도록 함
        for (int index = 0; index < tableLength; index++) {
            // 사람 아니면 패스
            if(table[index] != 'P') continue;

            // -distance 부터 distance 까지 가장 가까운 햄버거 먹고 X 처리
            for(int d = Math.max((index - distance), 0); d <= Math.min((index + distance), tableLength -1); d++) {
                // 햄버거 발견하면 먹기 X 표기
                if(table[d] == 'H') {
                    table[d] = 'X';
                    total++;
                    break;
                }
            }

        }


        System.out.println(total);
    }
}
