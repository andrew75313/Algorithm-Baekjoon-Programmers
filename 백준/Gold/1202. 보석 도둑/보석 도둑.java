import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String args[]) throws Exception {

        // INPUT SETTING
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 총보석 N, 총 가방 K
        StringTokenizer st = new StringTokenizer(reader.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        // 보석 정보 무게M 가격V
        int[][] jewels = new int[N][2];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(reader.readLine());
            jewels[i][0] = Integer.parseInt(st.nextToken());
            jewels[i][1] = Integer.parseInt(st.nextToken());
        }

        // 가방당 최대 가능 무게 C
        int[] bags = new int[K];
        for (int i = 0; i < K; i++) {
            bags[i] = Integer.parseInt(reader.readLine());
        }

        // 훔칠 수 있는 최대 보석 "가격"
        // 단 보석은 한개씩만 넣을 수 있음

        // 시작

        // 가장 작은 가방부터 (오름차순정렬)
        // 가장 가벼운 보석부터
        Arrays.sort(bags);
        Arrays.sort(jewels, Comparator.comparingInt(a->a[0]));

        // 가방 하나씩
        // 가방에 들어갈 보석 찾기 (비싼 보석 순)
        // 가격 비싼 보석부터 넣기
        // 넣으면서 총합 계산
        long result = 0;

        PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
        int index = 0;

        for(int i = 0 ; i < K; i++) {
            int currBag = bags[i];

            // 비싼 보석 큐에서 하나씩 넣으면서 채우기
            // 단, 가방 무게 이하의 보석 일것
            while(index < N && jewels[index][0] <= currBag) {
                queue.add(jewels[index][1]);
                index++;
            }

            // 큐에 보석이 있다면
            // 결과 업데이트
            if(!queue.isEmpty()) {
                result += queue.poll();
            }
        }

        // 총합 반환
        System.out.println(result);
    }
}