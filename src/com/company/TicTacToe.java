package com.company;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    private static char[][] map;        // game matrix
    private static int SIZE = 3;        // fild size
    private static int WINNER_SEQUENCE = SIZE;        // количество Х/О подряд для победы
    private static int X,Y;
//    private static boolean MAP_IS_EMPTY = true;
//    private static int MOVE_NUMBER = 0;
    private static int[][] HUMAN_MOVES = new int[2][SIZE*SIZE/2+1];
    private static int[][] COMP_MOVES  = new int[2][SIZE*SIZE/2+1];

    private static final char DOT_EMPTY = '•';      // empty field  •    ⃞  □   '🞎' 🞐  🞏 □ ▢ ▣ ▤ ▥ ▦ ▧ ▨ ▩
    private static final char DOT_X = '⛌';          // chross   ⛌   𐍇
    private static final char DOT_O = '੦';          // zero     ੦  ◯


    private static final boolean SILLY_MODE = true;

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {

        initMap();
        initHMCM();
        printMap();

        while(true) {
            humanTurn();
            if(isEndGame(DOT_X)) {
                break;
            }
//            computerTurn();
//            if(isEndGame(DOT_O)) {
//                break;
//            }
        }

        System.out.println("Game over.");
    }

    // Prepare game field
    private static void initMap() {
        map = new char[SIZE][SIZE];
        for(int i = 0 ; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    private static void initHMCM() {
        map = new char[SIZE][SIZE];
        for(int i = 0 ; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                HUMAN_MOVES[i][j] = -1;
                COMP_MOVES[i][j] = -1;
            }
        }
    }

    private static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            if (i == 0 ) System.out.print("░ ");
            else System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
//            System.out.print((char)(i+65) + " ");
            System.out.print((i+1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void humanTurn() {
//        int x,y;

        do {
            System.out.print("Enter the coordinates \nseparated by a space -> ");
            Y = scanner.nextInt() - 1;
            X = scanner.nextInt() - 1;
        } while(!isCellValid(X,Y));

        writeInMoves(HUMAN_MOVES, X, Y);

        map[Y][X] = DOT_X;
    }

    private static void computerTurn() {
//        int x = -1, y = -1;

        if (SILLY_MODE) {
            sillyMode();
        } else {
            if (HUMAN_MOVES[0][0] == -1 && COMP_MOVES[0][0] == -1) {
                sillyMode();
            } else if (HUMAN_MOVES[0][1] == -1) {
                sillyMode();
            } else {

                int a = 0, b = 0;

                for (int i = 0; i < HUMAN_MOVES.length; i++) {
                    if (HUMAN_MOVES[0][i] == -1) {
                        for (int j = i-2; j > -1 ; j--) {

                            a = HUMAN_MOVES[0][i-1] - HUMAN_MOVES[0][j];
                            b = HUMAN_MOVES[1][i-1] - HUMAN_MOVES[1][j];

                        }
                    }
                }




            }
        }
// ************************************************************************************************


        System.out.println("Computer move -> " + (Y + 1) + " " + (X + 1) + "  [" + map[Y][X] + "]");
        writeInMoves(COMP_MOVES, X, Y);
        map[Y][X] = DOT_O;
    }

    private static void sillyMode() {
        X = -1;
        Y = -1;
        do {
            X = random.nextInt(SIZE);
            Y = random.nextInt(SIZE);
        } while (!isCellValid(X, Y));
    }

    /*
    Поменял не много логику. Если попадает в поле и ячейка свободна то true.
     */
    private static boolean isCellValid(int x, int y) {
        boolean result = false;
        if (x > 0 || x < SIZE || y > 0 || y < SIZE) {
            if (map[y][x] == DOT_EMPTY) {
                result = true;
            }
            System.out.println("isCellValid  [" + y + "][" + x + "]-> " + map[y][x]);
        }

        return result;
    }

    private static boolean isEndGame(char plauerSumbol) {

        boolean resalt = false;

        printMap();



        if (checkWin(map,plauerSumbol,X,Y)) {
            System.out.println("Win " + plauerSumbol);
            resalt = true;
        }

        if (isMapFull()) {
            System.out.println("Win frendship");
            resalt = true;
        }

        return resalt;
    }

    /** Метод проверяет победу на поле любой размерности с любой длинной победной комбинации
     * За основу берутся координаты хода и проверяется пространство вокруг этих координат
     * @param arr - поле
     * @param dotUser - символ игрока
     * @param a - координата x
     * @param b - координата y
     * @return true - победа, false - нет победы
     */
    private static boolean checkWin(char[][] arr, char dotUser, int a, int b) {

        boolean result = false;

        do {
// Check left diagonal
            int winCount = 0;
            for (int i = 1; i < WINNER_SEQUENCE; i++) {
                if (a - i > -1 && b - i > -1) {
                    if (arr[a - i][b - i] == dotUser) {
                        winCount++;
                    } else break;
                } else break;
            }

            if (winCount == WINNER_SEQUENCE - 1) {
                result = true;
                break;
            }

            for (int i = 1; i < WINNER_SEQUENCE; i++) {
                if (a + i < SIZE && b + i < SIZE) {
                    if (arr[a + i][b + i] == dotUser) { // ERROR
                        winCount++;
                    } else break;
                } else break;
            }

            if (winCount == WINNER_SEQUENCE - 1) {
                result = true;
                break;
            }

//Check right diagonal (switch a & b)
            winCount = 0;
            for (int i = 1; i < WINNER_SEQUENCE; i++) {
                if (b - i > -1 && a + i < SIZE) {
                    if (arr[b - i][a + i] == dotUser) {
                        winCount++;
                    } else break;
                } else break;
            }

            if (winCount == WINNER_SEQUENCE - 1) {
                result = true;
                break;
            }

            for (int i = 1; i < WINNER_SEQUENCE; i++) {
                if (b + i < SIZE && a - i > -1) {
                    if (arr[b + i][a - i] == dotUser) {
                        winCount++;
                    } else break;
                } else break;
            }

            if (winCount == WINNER_SEQUENCE - 1) {
                result = true;
                break;
            }

// Check horizontal line
            winCount = 0;
            for (int i = 1; i < WINNER_SEQUENCE; i++) {
                if (b - i > -1) {
                    if (arr[a][b - i] == dotUser) {
                        winCount++;
                    } else break;
                } else break;
            }

            if (winCount == WINNER_SEQUENCE - 1) {
                result = true;
                break;
            }

            for (int i = 1; i < WINNER_SEQUENCE; i++) {
                if (b + i < SIZE) {
                    if (arr[a][b + i] == dotUser) {
                        winCount++;
                    } else break;
                } else break;
            }

            if (winCount == WINNER_SEQUENCE - 1) {
                result = true;
                break;
            }

// Check vertical line
            winCount = 0;
            for (int i = 1; i < WINNER_SEQUENCE; i++) {
                if (a - i > -1) {
                    if (arr[a - i][b] == dotUser) {
                        winCount++;
                    } else break;
                } else break;
            }

            if (winCount == WINNER_SEQUENCE - 1) {
                result = true;
                break;
            }

            for (int i = 1; i < WINNER_SEQUENCE; i++) {
                if (a + i < SIZE) {
                    if (arr[a + i][b] == dotUser) {
                        winCount++;
                    } else break;
                } else break;
            }

            if (winCount == WINNER_SEQUENCE - 1) {
                result = true;
                break;
            }

        } while (false);

        return result;
    }


    private static boolean isMapFull() {
        boolean result = true;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY)
                    result = false;
            }
        }
        return result;
    }


    private static boolean checkRisk(char[][] arr, char dotRival, int a, int b) {

        boolean result = false;



        do {
// Check left diagonal
            int winCount = 0;
            for (int i = 1; i < WINNER_SEQUENCE; i++) {
                if (a - i > -1 && b - i > -1) {
                    if (arr[a - i][b - i] == dotRival) {
                        winCount++;
                    } else break;
                } else break;
            }

            if (winCount == WINNER_SEQUENCE - 1) {
                result = true;
                break;
            }

            for (int i = 1; i < WINNER_SEQUENCE; i++) {
                if (a + i < SIZE && b + i < SIZE) {
                    if (arr[a + i][b + i] == dotRival) { // ERROR
                        winCount++;
                    } else break;
                } else break;
            }

            if (winCount == WINNER_SEQUENCE - 1) {
                result = true;
                break;
            }

//Check right diagonal (switch a & b)
            winCount = 0;
            for (int i = 1; i < WINNER_SEQUENCE; i++) {
                if (b - i > -1 && a + i < SIZE) {
                    if (arr[b - i][a + i] == dotRival) {
                        winCount++;
                    } else break;
                } else break;
            }

            if (winCount == WINNER_SEQUENCE - 1) {
                result = true;
                break;
            }

            for (int i = 1; i < WINNER_SEQUENCE; i++) {
                if (b + i < SIZE && a - i > -1) {
                    if (arr[b + i][a - i] == dotRival) {
                        winCount++;
                    } else break;
                } else break;
            }

            if (winCount == WINNER_SEQUENCE - 1) {
                result = true;
                break;
            }

// Check horizontal line
            winCount = 0;
            for (int i = 1; i < WINNER_SEQUENCE; i++) {
                if (b - i > -1) {
                    if (arr[a][b - i] == dotRival) {
                        winCount++;
                    } else break;
                } else break;
            }

            if (winCount == WINNER_SEQUENCE - 1) {
                result = true;
                break;
            }

            for (int i = 1; i < WINNER_SEQUENCE; i++) {
                if (b + i < SIZE) {
                    if (arr[a][b + i] == dotRival) {
                        winCount++;
                    } else break;
                } else break;
            }

            if (winCount == WINNER_SEQUENCE - 1) {
                result = true;
                break;
            }

// Check vertical line
            winCount = 0;
            for (int i = 1; i < WINNER_SEQUENCE; i++) {
                if (a - i > -1) {
                    if (arr[a - i][b] == dotRival) {
                        winCount++;
                    } else break;
                } else break;
            }

            if (winCount == WINNER_SEQUENCE - 1) {
                result = true;
                break;
            }

            for (int i = 1; i < WINNER_SEQUENCE; i++) {
                if (a + i < SIZE) {
                    if (arr[a + i][b] == dotRival) {
                        winCount++;
                    } else break;
                } else break;
            }

            if (winCount == WINNER_SEQUENCE - 1) {
                result = true;
                break;
            }

        } while (false);

        return result;
    }

    private static void writeInMoves(int[][] movesArr, int x, int y) {
        for (int i = 0; i < movesArr.length ; i++) {
            if (movesArr[0][i] == -1) {
                movesArr[0][i] = x;
                movesArr[1][i] = y;
            }
        }
    }

}
