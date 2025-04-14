import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {

    // 0~9 비트마스크 만들기
    private static Map<Integer, Integer> ledPattern = Map.of(
            0, 0b1110111,
            1, 0b0010001,
            2, 0b0111110,
            3, 0b0111011,
            4, 0b1011001,
            5, 0b1101011,
            6, 0b1101111,
            7, 0b0110001,
            8, 0b1111111,
            9, 0b1111011
    );

    public static void main(String args[]) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] arr = reader.readLine().split(" ");
        // 최대 층수
        int n = Integer.parseInt(arr[0]);
        // 디스플레이 최대 자릿수
        int k = Integer.parseInt(arr[1]);
        // LED 최대 변경 횟수
        int p = Integer.parseInt(arr[2]);
        // 현재 층수
        int x = Integer.parseInt(arr[3]);

        // Map<Integer,Map<Integer,Integer>> 형태 각 변환 마다 반전시키는 LED 계산 map 만들기\
        Map<Integer, Map<Integer, Integer>> convertMap = new HashMap<>();
        for (int i = 0; i <= 9; i++) {
            convertMap.put(i, new HashMap<>());

            for (int j = 0; j <= 9; j++) {
                int mask1 = ledPattern.get(i);
                int mask2 = ledPattern.get(j);

                int commonLed = Integer.bitCount(mask1 & mask2);

                int count = Integer.bitCount(mask1) + Integer.bitCount(mask2) - 2 * commonLed;

                convertMap.get(i).put(j, count);
            }
        }


        int result = 0;

        // String format
        String currFloor = String.format("%0" + k + "d", x);

        // 1층 부터 n 층까지 전부 진행
        // k 자릿수 별로 돌아가면서 검증
        // 최종 바꾼 숫자가 n 이하여야
        // 바꾼 led 숫자가 p 이하여야
        for (int floor = 1; floor <= n; floor++) {
            // 효율상 현재층 패스
            if (floor == x) continue;

            // 효율을 위한 검증 boolean
            boolean isOver = false;

            int convertCount = 0;

            String targetFloor = String.format("%0" + k + "d", floor);

            // x와 자릿수 비교 하면서 카운트
            for (int i = 0; i < k; i++) {
                int currNum = currFloor.charAt(i) - '0';
                int targetNum = targetFloor.charAt(i) - '0';

                convertCount += convertMap.get(currNum).get(targetNum);

                // 횟수가 초과하면 바로 break;
                if (convertCount > p) {
                    isOver = true;
                    break;
                }
            }

            // 카운트 적을경우 최종 결과 증가
            if (!isOver) result++;

        }

        System.out.println(result);
    }
}
