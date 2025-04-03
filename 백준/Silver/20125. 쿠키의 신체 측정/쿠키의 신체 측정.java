import java.io.*;
import java.util.*;

public class Main {

    public static void main(String args[]) throws Exception {
        // 입력된 정보 List -> Array로 바꾸기
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());

        List<char[]> inputList = new ArrayList<>();
        for(int i = 0; i < n; i++){
            inputList.add(reader.readLine().toCharArray());
        }
        char[][] tray = inputList.toArray(new char[0][]);

        // [심장 찾기] + [팔 길이 찾기]
        // 각 행을 살펴보면서 연속된 *가3 개 이상인 행이 심장라인
        int heartRow = 0;
        int heartColumn = 0;
        for(int r = 0; r < n; r++) {
            String row = String.valueOf(tray[r]);
            if(row.contains("***")) {
                heartRow = r;
                break;
            }
        }

        // heartRow +-1 총 3개행의 인덱스별 모두 * 이라면 (r, 해당 인덱스)가 심장 위치
        // 동시게 팔 길이까지 계산
        int rightArm = 0;
        int leftArm = 0;
        boolean isFound = false;
        for(int c = 0; c < n; c++) {
            // 심장 찾기
            if(!isFound &&
                    tray[heartRow][c] == '*' &&
                    tray[heartRow-1][c] == '*' &&
                    tray[heartRow+1][c] == '*') {
                heartColumn = c;
                isFound =true;
                continue;
            }

            // 오른팔 / 왼팔 늘이기
            if(!isFound && tray[heartRow][c] == '*') leftArm++;
            if(isFound && tray[heartRow][c] == '*') rightArm++;
        }

        // [허리 길이 찾기]
        int waist = 0;
        int waistRow = 0;
        for(int r = heartRow+1; r < n; r++) {
            String crumble = String.valueOf(tray[r]).replace("_", "");

            if(crumble.equals("*")) {
                waist++;
                waistRow = r;
            } else {
                break;
            }
        }

        // [왼다리 오른다리 길이 찾기]
        int leftLeg = 0;
        int rightLeg = 0;
        for(int r = waistRow + 1; r < n; r++) {
            char leftLegChar = tray[r][heartColumn-1];
            char rightLegChar = tray[r][heartColumn+1];

            if(leftLegChar == '_' && rightLegChar == '_') break;

            // 왼다리 늘리기
            if(leftLegChar == '*') leftLeg++;

            // 오른다리 늘리기
            if(rightLegChar == '*') rightLeg++;
        }

        // 심장은 각 좌표에서 +1 씩 해줘야함
        System.out.println((heartRow+1)+" "+(heartColumn+1));
        // 길이 : 왼팔 오른팔 허리 왼다리 오른다리
        System.out.println(leftArm+" "+rightArm+" "+waist+" "+leftLeg+" "+rightLeg);
    }

}
