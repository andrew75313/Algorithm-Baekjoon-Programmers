import java.io.*;
import java.util.*;

public class Main {

    public static void main(String args[]) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());

        int[] children = new int[n];
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(reader.readLine());
            children[i] = num;
        }

        // 최종 LIS 찾기
        List<Integer> lisList =  new ArrayList<>();

        for(int i = 0; i < n; i++) {
            int index = Collections.binarySearch(lisList, children[i]) ;

            // LIS 에 없을 경우 index 재지정
            if (index < 0) index = Math.abs(index) - 1;

            // index LIS 보다 작을 경우 -> 해당 위치 update 세팅
            if(index < lisList.size()) {
                lisList.set(index, children[i]);
            }

            // 크기랑 같거나 클 경우 뒤에 붙이기 -> 뒤에 세팅
            if(index >= lisList.size()) {
                lisList.add(children[i]);
            }
        }

        int result = n - lisList.size();

        System.out.println(result);
    }
}
