import java.io.*;
import java.util.*;

public class Main {

    public static void main(String args[]) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] inputArr = reader.readLine().split(" ");

        int n = Integer.parseInt(inputArr[0]);
        int c = Integer.parseInt(inputArr[1]);

        int[] houses = new int[n];
        for (int i = 0; i < n; i++) {
            houses[i] = Integer.parseInt(reader.readLine());
        }

        Arrays.sort(houses);

        // 거리를 이분법으로 시행
        // 최소거리, 최대거리 정하기
        int left = 1;
        int right = houses[n-1] - houses[0];

        // 정답
        int answer = 0;

        while(left <= right) {
            int mid = left + (right - left)/2;

            // 마지막 설치 위치 ~ mid 위치 보다 큰 거리 일때만 router 갯수를 카운트
            // 첫번째는 설치

            int routerCount = 1;

            int lastRouter = houses[0];
            for (int i = 1; i < n; i++) {
                if((houses[i] - lastRouter) >= mid) {
                    routerCount++;
                    lastRouter = houses[i];
                }
            }

            // 설치 공유기가 c 이상으로 많아 버리면 left를 중간값으로 갱신
            // 그렇지 않을 경우 right 을 중간값으로 갱신
            if(routerCount >= c) {
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }

        }

        System.out.println(answer);
    }
}