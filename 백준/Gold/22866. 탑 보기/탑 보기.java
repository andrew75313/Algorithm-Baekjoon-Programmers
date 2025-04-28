import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

public class Main {
    public static void main(String args[]) throws Exception {

        // INPUT SETTING
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());

        String[] arr = reader.readLine().split(" ");
        int[] buildings = new int[n];

        for (int i = 0; i < n; i++) {
            buildings[i] = Integer.parseInt(arr[i]);
        }

        // 스택설정(인덱스) 결과를 위한 건물수, 가장가까운 건물 등록 Array 설정
        Stack<Integer> stack = new Stack<>();
        int[] count = new int[n];
        int[] near = new int[n];

        // 초기값 설정
        Arrays.fill(near, -1);

        // [왼쪽접근]
        //  Stack에 하나씩 빌딩을 넣어가면서
        // 해당 인덱스 빌딩보다 작으면 전부 pop
        // 단, 스택은 empty X
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && buildings[i] >= buildings[stack.peek()]) {
                stack.pop();
            }

            if (!stack.isEmpty()) {
                count[i] += stack.size();
                near[i] = stack.peek();
            }

            stack.push(i);
        }

        // [오른쪽 접근]
        // 오른쪽부터 하나씩 다시 카운트
        // 단 near과 비교해서 더 가까운 것으로 갱신
        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && buildings[i] >= buildings[stack.peek()]) {
                stack.pop();
            }

            if (!stack.isEmpty()) {
                count[i] += stack.size();

                // 왼쪽 vs 오른쪽
                // 가까운 인덱스가 없을 경우 -1 -> 등록 하고, 그렇지 않을 경우만 비교
                //
                if(near[i] == -1) {
                    near[i] = stack.peek();
                } else if (Math.abs(near[i] - i) > Math.abs(stack.peek() - i)) {
                    near[i] = stack.peek();
                }
            }

            stack.push(i);
        }


        // 결과 반환
        for (int i = 0; i < n; i++) {
            if (count[i] == 0) {
                System.out.println(0);
            } else {
                System.out.println(count[i] + " " + (near[i]+1));
            }

        }
    }
}