import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static int[][] land;
    private static int n;
    private static int l;
    private static int r;

    private static int[] dx = {-1, 1, 0, 0};
    private static int[] dy = {0 ,0, -1, 1};

    public static void main(String args[]) throws Exception {

        // INPUT SETTING
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int[] arr = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        // n x n 땅
        n = arr[0];
        // l이상 일경우 국경선 열기
        l = arr[1];
        // r이하 일경우 국경선 열기
        r = arr[2];

        land = new int[n][n];
        for(int i = 0; i < n; i++) {
            int[] row = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for(int j = 0; j < n; j++) {
                land[i][j] = row[j];
            }
        }

        // BFS로 주변을 탐색해 가면서
        // 열려있는 연합국들을 하나씩
            // 연합국 덩어리들 끼리 int 캐스팅해서 똑같이 인원수를 나눠 가지기
        // 1개 밖에 아닌 것도 어차피 /1 이라 계속 진행
        // 모든 국가를 전부 탐색했다면 다음 일차로 넘어가기
        int day = 0;
        while (true) {
            boolean isMove = movePeople();

            if(isMove) {
                day++;
            } else {
                break;
            }
        }

        System.out.println(day);
    }

    private static boolean movePeople() {
        boolean isMove = false;

        boolean[][] visited = new boolean[n][n];

        for(int x = 0; x < n; x++) {
            for(int y = 0; y < n; y++) {

                // 방문점검 으로 효율 높이기
                if(visited[x][y]) continue;

                List<int[]> allies = bfs(x, y, visited);

                // 국가가 1개일 경우 패스
                // 여러 개일 경우 사람 재분배
                if(allies.size() == 1) {
                    continue;
                } else {
                    int totalPeople = 0;
                    int size = allies.size();
                    for(int[] coordinate : allies) {
                        totalPeople += land[coordinate[0]][coordinate[1]];
                    }

                    int avgPeople = totalPeople/size;

                    for(int[] coordinate : allies) {
                        land[coordinate[0]][coordinate[1]] = avgPeople;
                    }

                    // 전체 움직임에 넣기
                    isMove = true;
                }

            }
        }
        
        return isMove;
    }

    private static List<int[]> bfs(int x, int y, boolean[][] visited) {
        List<int[]> allies =  new ArrayList<>();
        allies.add(new int[]{x, y});

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});

        visited[x][y] = true;

        while(!queue.isEmpty()) {
            int size = queue.size();

            for(int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int currX = curr[0];
                int currY = curr[1];

                // 상하좌우 움직이면서 유효확인
                for(int d = 0; d < 4; d++) {
                    int nextX = currX + dx[d];
                    int nextY = currY + dy[d];

                    if(0 <= nextX && nextX < n &&
                            0<= nextY && nextY < n &&
                            !visited[nextX][nextY]) {
                        // 인구수 차이가 l 이상
                        int diff = Math.abs(land[currX][currY] - land[nextX][nextY]);

                        if(l <= diff && diff <= r) {
                            allies.add(new int[]{nextX, nextY});
                            visited[nextX][nextY] =true;
                            queue.add(new int[]{nextX, nextY});
                        }
                    }
                }
            }
        }

        return allies;
    }
}
