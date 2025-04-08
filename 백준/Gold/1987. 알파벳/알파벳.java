import java.io.*;
import java.util.*;

public class Main {

    // 문제 값
    static int R = 0;
    static int C = 0;
    static char[][] grid;

    // 문제 답
    static int MAX = 0;

    // direction
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String args[]) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] inputArr = reader.readLine().split(" ");

        R = Integer.parseInt(inputArr[0]);
        C = Integer.parseInt(inputArr[1]);

        grid = new char[R][C];

        for (int r = 0; r < R; r++) {
            grid[r] = reader.readLine().toCharArray();
        }

        // 변수 설정
        Set<Character> charSet = new HashSet<>();
        
        // 초기값 설정
        charSet.add(grid[0][0]);
        int[] start = {0, 0};
        int count = 1;

        dfs(start, charSet, count);

        System.out.println(MAX);
    }

    public static void dfs(int[] coordinate, Set<Character> charSet, int count) {
        int x = coordinate[0];
        int y = coordinate[1];

        // 최댓값 갱신
        MAX = Math.max(MAX, count);

        // 상하좌우 이동하면서 재귀 진행
        for(int d = 0; d<4; d++) {
            int nextX = x + dx[d];
            int nextY = y + dy[d];

            // grid 안에서 있어야할 것
            // charSet 에 포함하지 않을 것
            if(0 <= nextX && nextX < R &&
            0 <= nextY && nextY < C &&
            !charSet.contains(grid[nextX][nextY])) {
                // charSet 넣기
                charSet.add(grid[nextX][nextY]);

                // 재귀
                dfs(new int[]{nextX, nextY}, charSet, count+1);

                // (위에서 전부 완료한 다음) 이번 분기 Backtracking
                charSet.remove(grid[nextX][nextY]);
            }
        }
    }
}