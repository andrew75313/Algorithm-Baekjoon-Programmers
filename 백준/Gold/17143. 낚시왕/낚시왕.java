import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int R;
    static int C;

    public static void main(String args[]) throws Exception {

        // INPUT SETTING
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(reader.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // 상어 맵
        // 해당 그리드에 어떤 상어가 있는지 한번에 등록
        Map<Integer, Map<Integer, int[]>> sharks = new HashMap<>();

        // M개의 상어 정보 입력 받기 + 그리드에 넣기
        // r c 상어위치  s 속력 d 이동방향 z 크기

        int num = 0;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(reader.readLine());

            int r = Integer.parseInt(st.nextToken()); // 행
            int c = Integer.parseInt(st.nextToken()); // 열

            int s = Integer.parseInt(st.nextToken()); // 속력
            int d = Integer.parseInt(st.nextToken()); // 방향
            int z = Integer.parseInt(st.nextToken()); // 크기

            sharks.putIfAbsent(c, new HashMap<>());
            sharks.get(c).put(r, new int[]{s, d, z});
        }

        // 1 . <낚시왕 행동>
        // 오른쪽 +1
        // 위치 R 중 가장 낚시왕과 가까운 상어 잡기 = 상어 사라지기
        // 2. <상어 이동>
        // 주어진 속도로 한번 이동
        // 단, 판 끝에 도달하면 방향바꾸기
        // 단, 이 그 위치에 상어가 있다면, 무게가 큰 상어만 살아남기


        int total = 0;
        for (int index = 1; index <= C; index++) {

            Map<Integer, int[]> columnSharks = sharks.get(index);

            // 낚시 왕 행동
            // 가까운 상어 잡기 + 크기합 갱신
            if (columnSharks != null) {
                for (int r = 1; r <= R; r++) {
                    if (columnSharks.get(r) != null) {
                        total += columnSharks.get(r)[2];
                        columnSharks.remove(r);
                        break;
                    }
                }
            }

            // 방향키
            Map<Integer, int[]> direction = new HashMap<>();
            direction.put(1, new int[]{-1, 0});
            direction.put(2, new int[]{1, 0});
            direction.put(3, new int[]{0, 1});
            direction.put(4, new int[]{0, -1});

            // 상어들 다음 행동 기록
            Map<Integer, Map<Integer, int[]>> newSharks = new HashMap<>();
            for (Integer colSharksKey : sharks.keySet()) {
                Map<Integer, int[]> colSharks = sharks.get(colSharksKey);

                for(Integer key : colSharks.keySet()) {

                    int r = key;
                    int c = colSharksKey;

                    int[] shark = sharks.get(c).get(r);

                    int speed = shark[0];
                    int dir = shark[1];
                    int weight = shark[2];

                    // <움직이기>
                    // 아무것도 안움직이는 초기값
                    int distance = speed;

                    // 반복 주기를 없애기
                    if(dir == 1 || dir == 2) distance %= 2*(R - 1);
                    if(dir == 3 || dir == 4) distance %= 2*(C - 1);

                    // distance 움직이기 그냥 둘다 한번에 처리
                    for(int d = 0; d < distance; d++) {
                        int[] currDir = direction.get(dir);

                        int rowD = currDir[0];
                        int colD = currDir[1];


                        // 방향 바꾸기
                        if(R < r + rowD || r + rowD < 1 || C < c + colD || c + colD < 1) {
                            if (dir == 1) dir = 2;
                            else if (dir == 2) dir = 1;
                            else if (dir == 3) dir = 4;
                            else if (dir == 4) dir = 3;

                            currDir = direction.get(dir);

                            rowD = currDir[0];
                            colD = currDir[1];
                        }

                        // 새롭게 위치 갱신
                        r += rowD;
                        c += colD;
                    }

                    // 기존 샤크와 비교
                    newSharks.putIfAbsent(c, new HashMap<>());

                    // 새로운 자료 넣기
                    Map<Integer, int[]> originInnerMap = newSharks.get(c);

                    if(originInnerMap.get(r) == null ||
                    originInnerMap.get(r)[2] < weight) {
                        originInnerMap.put(r, new int[]{speed, dir, weight});
                    }
                }
            }

            // 갱신
            sharks = newSharks;
        }

        System.out.println(total);
    }

}