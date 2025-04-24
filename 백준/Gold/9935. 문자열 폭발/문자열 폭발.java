import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String args[]) throws Exception {

        // INPUT SETTING
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str = reader.readLine();
        String bomb = reader.readLine();

        // str의 char하나씩 stack에 넣으면서 만약 bomb 일경우 bomb pop
        // 단, char는 bomb 길이 만큼

        int n = bomb.length();

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < str.length(); i++) {
            stack.push(str.charAt(i));

            int size = stack.size();

            // 스택이 bomb 보다 길 때
            if (size >= n) {

                boolean isMatch = true;

                // 맨 끝에 n개가 비슷한지 확인
                // 전부 아닐 때만 바로 break;
                for (int index = 0; index < n; index++) {
                    if (bomb.charAt(index) != stack.get(size - n + index)) {
                        isMatch = false;
                    }
                }

                // 전부 맞으면 pop
                if (isMatch) {
                    for (int index = 0; index < n; index++) {
                        stack.pop();
                    }
                }
            }
        }

        if (stack.isEmpty()) {
            str = "FRULA";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (char c : stack) {
                stringBuilder.append(c);
            }

            str = stringBuilder.toString();
        }


        System.out.println(str);
    }
}


