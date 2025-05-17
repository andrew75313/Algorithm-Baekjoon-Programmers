import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    public static int N;
    public static int M;
    public static int cctvCount;
    public static List<CCTV> cctvList = new ArrayList<>();
    // 최솟값 전역에서 관리 -> DFS에서 계속 가지고 다닐 수 없기 때문에
    public static int minimum = Integer.MAX_VALUE;

    // 방향키
    public static int[] dx = {-1, 1, 0, 0};
    public static int[] dy = {0, 0, -1, 1};

    // CCTV 타입별 방향정하기 (5번 빼고)
    static int[][][] directions = {
            {},
            {{0}, {1}, {2}, {3}},
            {{0, 1}, {2, 3}},
            {{0, 2}, {0, 3}, {1, 2}, {1, 3}},
            {{0, 1, 2}, {0, 1, 3}, {2, 3, 0}, {2, 3, 1}},
            {{0,1,2,3}}
    };

    // CCTV를 쉽게 다루기 위해 클래스 생성
    public static class CCTV {
        // coordinate
        int x;
        int y;
        // type,
        int type;

        public CCTV(int x, int y, int type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }
    }

    public static void main(String args[]) throws Exception {

        // INPUT SETTING
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 사무실 크기 N * M
        String[] nm = reader.readLine().split(" ");
        N = Integer.parseInt(nm[0]);
        M = Integer.parseInt(nm[1]);

        int[][] office = new int[N][M];

        // 맵에 채우기
        for (int i = 0; i < N; i++) {
            String[] line = reader.readLine().split(" ");
            for (int j = 0; j < M; j++) {
                int num = Integer.parseInt(line[j]);
                // CCTV 일 경우 저장
                if (num != 0 && num != 6) {
                    cctvList.add(new CCTV(i, j, num));
                }

                office[i][j] = num;
            }
        }

        cctvCount = cctvList.size();

        // 나머지 카메라 마다 DFS 진행
        // DFS 끝마다 O의 갯수를 셈
        int depth = 0;
        dfs(office, depth);

        // 최솟값 반환
        System.out.println(minimum);
    }

    public static void dfs(int[][] map, int depth) {
        // 끝까지 도달했을 경우에 minimum 갱신
        if (depth == cctvCount) {
            minimum = Math.min(countZero(map), minimum);
            return;
        }

        // depth 번째 CCTV 의 좌표에서 다음 좌표로 DFS
        CCTV cctv = cctvList.get(depth);
        for(int[] dirs : directions[cctv.type]) {
            // CCTV 당 새로운 임시 office 생성 해서 DFS
            int[][] tempMap = new int[N][M];
            for(int r = 0; r < N; r++) {
                tempMap[r] = Arrays.copyOf(map[r], M);
            }

            for(int dir : dirs) {
                // tempMap 방향 갱신
                updateWatchingArea(tempMap, cctv.x, cctv.y, dir);
            }

            dfs(tempMap, depth+1);
        }
    }

    // 좌표기준 방향으로감시 영역 체크 하기
    public static void updateWatchingArea(int[][] map, int x, int y, int direction) {
        int newX = x + dx[direction];
        int newY = y + dy[direction];

        // 벽만날때까지
        // 0을 -1로 감시영역으로 바꾸기
        while (0 <= newX && newX < N &&
                0 <= newY && newY < M &&
                map[newX][newY] != 6) {
            // 다른 CCTV를 마주쳤을 때도 중지
            if (map[newX][newY] == 0) {
                map[newX][newY] = -1;
            }
            newX += dx[direction];
            newY += dy[direction];
        }
    }

    public static int countZero(int[][] map) {
        int count = 0;

        for(int[] row : map) {
            for(int cell : row) {
                if(cell == 0) count++;
            }
        }

        return count;
    }
}