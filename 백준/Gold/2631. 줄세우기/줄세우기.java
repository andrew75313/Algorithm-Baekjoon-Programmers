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

        // DP ( 값 : i 번째 까지 연속된 아이들의 최대의 수 )
        int[] dp = new int[n];

        // 초기값 등록
        Arrays.fill(dp, 1);

        // Children 순서대로 자기 앞이 자기보다 작으면 +1 추가
        // dp 값은 j dp값 과 비교했을 때
        for(int i = 1; i < n ; i++) {
            for(int j = 0; j < i; j++) {
                if(children[j] < children[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        // dp 값중 최대 값 = 가장 긴 열 갯수 를 전체 수에서 뻄
        int result = 0;
        int maxLength = 0;
        for(int childrenLength : dp) {
            maxLength = Math.max(maxLength, childrenLength);
        }
        
        result = n - maxLength;
        System.out.println(result);
    }
}