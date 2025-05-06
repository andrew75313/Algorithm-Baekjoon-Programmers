import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N, M, fuel;
    static int[][] map;
    static int taxiRow, taxiCol;
    static Map<Integer, int[]> passengers = new HashMap<>();
    static int pathToPassenger;
    static int pathToTarget;

    // 방향키
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String args[]) throws Exception {

        // INPUT SETTING
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] line = reader.readLine().split(" ");
        // 맵 크기
        N = Integer.parseInt(line[0]);
        // 승객 수
        M = Integer.parseInt(line[1]);
        // 초기 연료값
        fuel = Integer.parseInt(line[2]);

        // 지도
        // 1은 벽
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            line = reader.readLine().split(" ");
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(line[j]);
            }
        }

        // 택시 시작 위치 (1,1) 부터 시작 하는 것 고려
        line = reader.readLine().split(" ");
        taxiRow = Integer.parseInt(line[0]) - 1;
        taxiCol = Integer.parseInt(line[1]) - 1;

        // 승객 기록 (1,1) 부터 시작 하는 것 고려
        int passengerId = 2;
        for (int i = 0; i < M; i++) {
            line = reader.readLine().split(" ");
            int startR = Integer.parseInt(line[0]) - 1; // 출발 행
            int startC = Integer.parseInt(line[1]) - 1; // 출발 열
            int endR = Integer.parseInt(line[2]) - 1; // 도착 행
            int endC = Integer.parseInt(line[3]) - 1; // 도착 열

            passengers.put(passengerId, new int[]{startR, startC, endR, endC});
            map[startR][startC] = passengerId;

            passengerId++;
        }

        // <조건>
        // 승객 순서 : 최단거리 -> 순번이 작은 승객부터 -> BFS로 거리 구하기
        // fuel = 이동할 수 있는 최대 거리
        // 단, 최대 거리임에도 target 도달 못하면 버림 (0 딱 떨어지면 그건 허용)
        // 한명이라도 실패하면 그 즉시 하루 전체 실패 -1 반환
        // 승객 이동이 성공하면 다음 승객 전, 소모 이동 거리의 두배 추가
        // return 남은 fuel

        // <동작>
        // 1. 택시 위치 기준  BFS 로 넓혀 가면서 가장 가까운 승객 찾기 + 그때까지의 LEVEL 할때마다 fuel 감소
        // 2. 해당 승객 위치에서 target 까지 이동 BFS + 그때까지 LEVEL 할때 마다 기록 + fuel 감소
        // 단, target 전 연료 다 떨어지면 -1 반환 하고 즉시 종료
        // 3. target 위치를 택시 위치로 갱신
        // 4. fuel 에 LEVEL*2 더해서 갱신
        // 5. M 번 반복

        for (int drive = 0; drive < M; drive++) {
            pathToPassenger = 0;
            pathToTarget = 0;

            // 가까운 승객 찾기
            int foundPassengerId = findPassenger();

            // 막혀있을때
            if (foundPassengerId == -1) {
                System.out.println(-1);
                return;
            } else {
                taxiRow = passengers.get(foundPassengerId)[0];
                taxiCol = passengers.get(foundPassengerId)[1];
            }

            // fuel 계산
            fuel -= pathToPassenger;
            if (fuel < 0) {
                System.out.println(-1);
                return;
            }

            // target으로 이동
            if (!driveToTarget(foundPassengerId)) {
                System.out.println(-1);
                return;
            }
            ;

            fuel -= pathToTarget;
            if (fuel < 0) {
                System.out.println(-1);
                return;
            }

            // 택시 위치 갱신
            taxiRow = passengers.get(foundPassengerId)[2];
            taxiCol = passengers.get(foundPassengerId)[3];

            // fuel 갱신
            fuel += (2 * pathToTarget);
        }

        //모두 성공
        System.out.println(fuel);
    }

    public static int findPassenger() {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[N][N];
        // 우선 순위 판별 행 -> 열 순
        List<int[]> nears = new ArrayList<>();

        // 택시 출발시 즉시 손님이 있을 경우
        if (map[taxiRow][taxiCol] >= 2) {
            int immediateId = map[taxiRow][taxiCol];
            map[taxiRow][taxiCol] = 0;
            pathToPassenger = 0;
            return immediateId;
        }

        // init
        queue.add(new int[]{taxiRow, taxiCol});
        visited[taxiRow][taxiCol] = true;

        // 레벨
        int level = 0;

        // BFS
        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int currR = curr[0];
                int currC = curr[1];

                // 상하좌우 이동
                // 단 맵을 벗어다면 안됨 + 방문한 곳이면 안됨 + 1이면 안됨
                // 0이 아닐 경우, 바로 그 숫자 반환 종료
                for (int d = 0; d < 4; d++) {
                    int nextR = currR + dy[d];
                    int nextC = currC + dx[d];

                    if (0 <= nextR && nextR < N &&
                            0 <= nextC && nextC < N &&
                            !visited[nextR][nextC] &&
                            map[nextR][nextC] != 1) {
                        // 사용자 아이디가 있을 경우
                        if (map[nextR][nextC] >= 2) {
                            nears.add(new int[]{nextR, nextC, map[nextR][nextC]});
                        }

                        queue.add(new int[]{nextR, nextC});
                        visited[nextR][nextC] = true;
                    }
                }
            }

            level++;

            // 행이 작은것 우선, 열이 작은것 우선
            if (!nears.isEmpty()) {

                Collections.sort(nears, Comparator
                        .comparingInt((int[] a) -> a[0])
                        .thenComparingInt(a -> a[1])
                        .thenComparingInt(a -> a[2]));

                int passengerId = nears.get(0)[2];

                map[passengers.get(passengerId)[0]][passengers.get(passengerId)[1]] = 0;

                pathToPassenger = level;

                return passengerId;
            }
        }

        return -1;
    }

    public static boolean driveToTarget(int passengerId) {
        // BFS로 움직이면서 Target 도달시 종료
        int startR = passengers.get(passengerId)[0];
        int startC = passengers.get(passengerId)[1];
        int targetR = passengers.get(passengerId)[2];
        int targetC = passengers.get(passengerId)[3];

        // 시작 도착지점이 같을 경우
        if (startR == targetR && startC == targetC) {
            pathToTarget = 0;
            return true;
        }


        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[N][N];

        // init
        queue.add(new int[]{taxiRow, taxiCol});
        visited[taxiRow][taxiCol] = true;

        int level = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int currR = curr[0];
                int currC = curr[1];

                for (int d = 0; d < 4; d++) {
                    int nextR = currR + dy[d];
                    int nextC = currC + dx[d];

                    if (0 <= nextR && nextR < N &&
                            0 <= nextC && nextC < N &&
                            !visited[nextR][nextC] &&
                            map[nextR][nextC] != 1) {

                        // target 도달시
                        if (nextR == targetR && nextC == targetC) {
                            pathToTarget = level+1;
                            return true;
                        }


                            queue.add(new int[]{nextR, nextC});
                            visited[nextR][nextC] = true;

                    }
                }
            }

            level++;
        }


        return false;
    }
}