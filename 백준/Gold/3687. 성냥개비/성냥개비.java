import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String args[]) throws Exception {

        // INPUT SETTING
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());

        // 주어진 성냥으로 만들 수 있는 최소숫자
        String[] match = new String[8];
        match[2] = "1";
        match[3] = "7";
        match[4] = "4";
        match[5] = "2";
        match[6] = "0";
        match[7] = "8";

        // 만들수 있는 최솟값 DP
        String[] minDP = new String[101];

        // DP 초기화
        for (int i = 2; i <= 7; i++) {
            minDP[i] = match[i];
        }
        // 처음은 따로
        minDP[6] = "6";

        // minDP update
        for (int i = 8; i <= 100; i++) {
            // 성냥으로 만들 수 있는 최솟값들을 추가
            for (int j = 2; j <= 7; j++) {
                // 이전값이 없을 경우 갱신 안함
                if (minDP[i - j] != null) {

                    String updateNum = minDP[i - j] + match[j];

                    // minDP[i] 갱신
                    if (minDP[i] == null) {
                        minDP[i] = updateNum;
                    } else {
                        long minNum = Math.min(Long.parseLong(updateNum), Long.parseLong(minDP[i]));
                        minDP[i] = String.valueOf(minNum);
                    }

                }
            }
        }


        // 테스트 마다 최대 최솟값 찾기
        for (int test = 0; test < n; test++) {
            int num = Integer.parseInt(reader.readLine());

            // <최솟값 찾기>
            String min = minDP[num];

            // <최댓값 찾기>
            String max = findMaximum(num);

            System.out.println(min + " " + max);
        }
    }

    static String findMaximum(int target) {
        // 뒤를 최대한 채워야함
        // 가장 작은 1(2개로 만듬) 최대한 뒤를 채워야함
        // 단, 홀수 일 경우 맨 앞을 3개로 -> 7을 만들어야함

        int remainder = target % 2;

        // 2 이상의 짝수일 경우 & 홀수 일경우
        String result = "7";
        if (remainder == 0) {
            result = "1";
        }

        for (int i = 1; i < (target / 2); i++) {
            result = result + "1";
        }

        return result;
    }
}