import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String args[]) throws Exception {

        // INPUT SETTING
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;

        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            // INPUT SETTING 2
            int N = Integer.parseInt(line.trim());
            Map<Integer, Integer> numCoin = new HashMap<>();
            List<Integer> coins = new ArrayList<>();

            int total = 0;

            for (int i = 0; i < N; i++) {
                String[] parts = reader.readLine().split(" ");
                int value = Integer.parseInt(parts[0]);
                int num = Integer.parseInt(parts[1]);

                coins.add(value);
                numCoin.put(value, num);

                total += (value * num);
            }

            // 총 금액 반 구하기
            // 단, 2로 나눈 나머지가 0이 아니면 -> 0 반환
            int half = 0;
            if ((total % 2) != 0) {
                System.out.println(0);
                continue;
            } else {
                half = total / 2;
            }

            // DP 선언 : DP 값 -> 0원 포함 해당 금액을 만들수 있는지 vs 없는지
            boolean[] dp = new boolean[half+1];
            dp[0] = true;

            for(Map.Entry<Integer, Integer> entry : numCoin.entrySet()) {
                int val = entry.getKey();
                int count = entry.getValue();

                // half 부터 역순으로
                for(int i = half; i >= 0; i--) {
                    if(dp[i]) {
                        // 동전으로 만들 수 있는 값들을 기존 DP에 더해서 True로 전환
                        for(int j = 1; j <= count; j++) {
                            int possibleNum = i+ (val * j);

                            // 효율을 위해 굳이 half 넘어 가는 값은 통과
                            if(possibleNum > half) break;

                            // Dp 값 갱신
                            dp[possibleNum] = true;

                        }
                    }
                }
            }

            // dp[half]  값 판별후 반환
            if(dp[half]) {
                System.out.println(1);
            } else {
                System.out.println(0);
            }
        }
    }
}