import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


class Point{
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
public class OmokGame {

    static final int BOARD_SIZE = 16;
    static String[][] board = new String[BOARD_SIZE][BOARD_SIZE];
    static int count = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init();
        while (true) {
            count++;
            printMsg();
            StringTokenizer st = new StringTokenizer(br.readLine());

            // 입력 개수 확인
           if (st.countTokens() != 2) {
                System.out.println("입력을 확인하세요.");
                count--;
                continue;
            }
            int xPos = Integer.parseInt(st.nextToken());
            int yPos = Integer.parseInt(st.nextToken());
            Point userPoint = new Point(xPos, yPos);
            //범위 확인
            if (!checkRange(userPoint)) {
                System.out.println("입력을 확인하세요.");
                count--;
                continue;
            }
            //입력한 자리에 돌이 이미 있는지 확인
            if (!checkArea(userPoint)) {
                System.out.println("입력을 확인하세요.");
                count--;
                continue;
            }
            putStone(userPoint);
            if (checkWin(userPoint)) {
                if(count % 2 == 0){
                    System.out.println("흰돌이 승리하셨습니다.");
                }else{
                    System.out.println("검은돌이 승리하셨습니다.");
                }
                printBoard();
                break;
            }

        }
    }

    private static void printBoard() {

        for (int i = 1; i < BOARD_SIZE; i++) {
            System.out.print("[");
            for (int j = 1; j < BOARD_SIZE; j++) {
                if (j == BOARD_SIZE - 1) {
                    System.out.print(board[i][j]);
                }else{
                    System.out.print(board[i][j]+", ");
                }
            }
            System.out.println("]");
        }
    }

    public static boolean checkArea(Point userPoint) {
        if (!board[userPoint.x][userPoint.y].equals("O")) {
            return false;
        }
        return true;
    }

    public static void putStone(Point userPoint) {
        if(count % 2 == 0){
            board[userPoint.x][userPoint.y] = "W";
        }else{
            board[userPoint.x][userPoint.y] = "B";
        }
    }

    public static boolean checkRange(Point userPoint) {
        if (userPoint.x < 1 || userPoint.x >= BOARD_SIZE || userPoint.y < 1 || userPoint.y >= BOARD_SIZE) {
            return false;
        }
        return true;

    }

    private static void init() {
        for (int i = 1; i < BOARD_SIZE; i++) {
            for (int j = 1; j < BOARD_SIZE; j++) {
                board[i][j] = "O";
            }
        }
        System.out.println("오목을 시작합니다.");
    }

    public static void printMsg() {
        StringBuilder sb = new StringBuilder();
        printBoard();
        if(count % 2 == 0) sb.append("흰 돌 차례입니다.").append("\n");
        else sb.append("검정 돌 차례입니다.").append("\n");
        System.out.print(sb);
    }

    public static boolean checkWin(Point userPoint) {
        String color = count % 2 == 0 ? "W" : "B";
        //가로
        int cnt1 = 0;
        for (int y = userPoint.y; y < BOARD_SIZE; y++) {
            if (board[userPoint.x][y].equals(color)){
                cnt1++;
            }else{
                break;
            }
        }

        for (int y = userPoint.y - 1; y > 0; y--) {
            if (board[userPoint.x][y].equals(color)){
                cnt1++;
            }else{
                break;
            }
        }

        if(cnt1 == 5){
            return true;
        }
        

        //세로
        int cnt2 = 0;

        for (int x = userPoint.x; x < BOARD_SIZE; x++) {
            if (board[x][userPoint.y].equals(color)){
                cnt2++;
            }else{
                break;
            }
        }

        for (int x = userPoint.x - 1; x > 0; x--) {
            if (board[x][userPoint.y].equals(color)){
                cnt2++;
            }else{
                break;
            }
        }

        if(cnt2 == 5){
            return true;
        }

        //대각 /
        int cnt3 = 0;

        int x = userPoint.x;
        int y = userPoint.y;

        while (checkRange(new Point(x,y)) && board[x][y].equals(color)) {
            x--;
            y++;
            cnt3++;
        }

        x = userPoint.x + 1;
        y = userPoint.y - 1;
        while (checkRange(new Point(x,y)) && board[x][y].equals(color)) {
            x++;
            y--;
            cnt3++;
        }


        if(cnt3 == 5){
            return true;
        }
        //대각 \
        int cnt4 = 0;

        x = userPoint.x;
        y = userPoint.y;

        while (checkRange(new Point(x,y)) && board[x][y].equals(color)) {
            x++;
            y++;
            cnt4++;
        }

        x = userPoint.x - 1;
        y = userPoint.y - 1;
        while (checkRange(new Point(x,y)) && board[x][y].equals(color)) {
            x--;
            y--;
            cnt4++;
        }


        if(cnt4 == 5){
            return true;
        }

        return false;
    }
}

/**
 * O O O O O
 * O O O O O
 * O O O O O
 *
 * B L B
 * L O
 */
