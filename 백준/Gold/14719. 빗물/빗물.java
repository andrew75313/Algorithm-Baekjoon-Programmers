import java.io.*;
import java.util.*;

public class Main {

    public static void main(String args[]) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] inputArr = reader.readLine().split(" ");

        int H = Integer.parseInt(inputArr[0]);
        int W = Integer.parseInt(inputArr[1]);

        int[] heights = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        int result = 0;

        // 왼 오 최대 높이 설정
        int maxLeft = -1;
        int maxRight = -1;

        // 최대 높이 인덱스 설정
        int[] leftHeights = new int[W];
        int[] rightHeights = new int[W];

        for(int l = 0; l < W; l++){
            maxLeft = Math.max(maxLeft, heights[l]);
            leftHeights[l] = maxLeft;
        }

        for(int r = W-1; r >=0; r--) {
            maxRight = Math.max(maxRight, heights[r]);
            rightHeights[r] = maxRight;
        }

        // 물웅덩이 구하기
        for(int i = 0; i < W; i++) {
            result += Math.min(leftHeights[i], rightHeights[i]) - heights[i];
        }

        System.out.println(result);
    }
}
