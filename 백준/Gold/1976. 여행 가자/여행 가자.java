import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.*;

public class Main {

    // 그래프만들기
    private static Map<Integer, List<Integer>> graph = new HashMap<>();

    // union find
    private static int[] parent;
    private static int[] rank;

    public static void main(String args[]) throws Exception {

        // INPUT SETTING
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 모든 도시 수
        int n = Integer.parseInt(reader.readLine());
        // 여행갈 도시의 수
        int m = Integer.parseInt(reader.readLine());
        // n번까지 연결
        for(int i = 1; i <= n; i++) {
            int[] arr = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

            // graph 세팅
            for(int j = 0; j < n; j++) {
                if(arr[j] == 1) {
                    graph.putIfAbsent(i, new ArrayList<Integer>());
                    graph.putIfAbsent(j+1, new ArrayList<Integer>());
                    graph.get(i).add(j+1);
                    graph.get(j+1).add(i);
                }
            }
        }
        // 여행 경로
        List<Integer> path = Arrays.stream(reader.readLine().split(" "))
            .map(Integer::parseInt)
            .collect(Collectors.toList());

        // union-find 세팅
        // 초기세팅
        parent = new int[n+1];
        rank =  new int[n+1];
        // 관계세팅
        for(int i = 1; i <= n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
        // union 세팅
        for(int key : graph.keySet()) {
            for(int val : graph.get(key)) {
                union(key, val);
            }
        }

        // 모든 경로의 부모가 같으면 YES 아니면 NO
        String result = "YES";
        Set<Integer> set = new HashSet<>();
        for(int destination : path) {
            set.add(find(destination));
        }

        if(set.size() > 1) result = "NO";

        System.out.println(result);
    }

    // 재귀 find
    public static int find(int x) {

        if(parent[x] != x) {
            parent[x] = find(parent[x]);
        }

        return parent[x];
    }

    // union
    public static void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if(rootX != rootY) {
            int rankX = rank[rootX];
            int rankY = rank[rootY];

            if(rankX > rankY) {
                parent[rootY] = rootX;
            } else if (rankX < rankY) {
                parent[rootX] = rootY;
            } else if (rankX == rankY) {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
}
