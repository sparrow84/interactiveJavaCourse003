package com.company;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    private static char[][] map;        // game matrix
    private static final int SIZE = 3;        // fild size
    private static final int WINNER_SEQUENCE = SIZE;        // количество Х/О подряд для победы
    private static int x, y;
//    private static boolean MAP_IS_EMPTY = true;
//    private static int MOVE_NUMBER = 0;
    private static int[][] humanMoves;
    private static int[][] compMoves;

    private static final char DOT_EMPTY = '•';      // empty field  •    ⃞  □   '🞎' 🞐  🞏 □ ▢ ▣ ▤ ▥ ▦ ▧ ▨ ▩
    private static final char DOT_X = '⛌';          // chross   ⛌   𐍇
    private static final char DOT_O = '੦';          // zero     ੦  ◯


    private static final boolean SILLY_MODE = false;

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
            computerTurn();
            if(isEndGame(DOT_O)) {
                break;
            }
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
        humanMoves = new int[2][SIZE*SIZE/2+1];
        compMoves = new int[2][SIZE*SIZE/2+1];

        for(int i = 0 ; i < humanMoves.length; i++) {
            for(int j = 0; j < humanMoves[0].length; j++) {
                humanMoves[i][j] = -1;
                compMoves[i][j] = -1;
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
            y = scanner.nextInt() - 1;
            x = scanner.nextInt() - 1;
        } while(!isCellValid(x, y));

        writeInMoves(humanMoves, x, y);

        map[y][x] = DOT_X;
    }

    private static void computerTurn() {
//        int x = -1, y = -1;

        if (SILLY_MODE) {
            sillyMode();
        } else {
            if (humanMoves[0][0] == -1 && compMoves[0][0] == -1) {
                sillyMode();
            } else if (humanMoves[0][1] == -1) {
                sillyMode();
            } else {

                do {
// Check left diagonal
                    int posibleMoveX = -1;
                    int posibleMoveY = -1;
                    int riskCount = 0;
                    int xLocal;
                    int yLocal;

                    for (int i = 1; i < WINNER_SEQUENCE; i++) {
                        xLocal = x - i;
                        yLocal = y - i;

                        if (xLocal > -1 && yLocal > -1) {
                            if (map[xLocal][yLocal] == DOT_X) {
                                riskCount++;
                            } else if (map[xLocal][yLocal] == DOT_EMPTY) {
                                posibleMoveX = xLocal;
                                posibleMoveY = yLocal;
                            } else break;
                        } else break;
                    }

                    if (riskCount == WINNER_SEQUENCE - 2 && posibleMoveX > -1) {
                        x = posibleMoveX;
                        y = posibleMoveY;
                        break;
                    }

                    for (int i = 1; i < WINNER_SEQUENCE; i++) {
                        xLocal = x + i;
                        yLocal = y + i;

                        if (xLocal < SIZE && yLocal < SIZE) {
                            if (map[xLocal][yLocal] == DOT_X) {
                                riskCount++;
                            } else if (map[xLocal][yLocal] == DOT_EMPTY) {
                                posibleMoveX = xLocal;
                                posibleMoveY = yLocal;
                            } else break;
                        } else break;
                    }

                    if (riskCount == WINNER_SEQUENCE - 2 && posibleMoveX > -1) {
                        x = posibleMoveX;
                        y = posibleMoveY;
                        break;
                    }

//Check RIGHT diagonal

                    riskCount = 0;

                    for (int i = 1; i < WINNER_SEQUENCE; i++) {
                        xLocal = x - i;
                        yLocal = y + i;

                        if (xLocal > -1 && yLocal < SIZE) {
                            if (map[xLocal][yLocal] == DOT_X) {
                                riskCount++;
                            } else if (map[xLocal][yLocal] == DOT_EMPTY) {
                                posibleMoveX = xLocal;
                                posibleMoveY = yLocal;
                            } else break;
                        } else break;
                    }

                    if (riskCount == WINNER_SEQUENCE - 2 && posibleMoveX > -1) {
                        x = posibleMoveX;
                        y = posibleMoveY;
                        break;
                    }

                    for (int i = 1; i < WINNER_SEQUENCE; i++) {
                        xLocal = x + i;
                        yLocal = y - i;

                        if (xLocal < SIZE && yLocal > -1) {
                            if (map[xLocal][yLocal] == DOT_X) {
                                riskCount++;
                            } else if (map[xLocal][yLocal] == DOT_EMPTY) {
                                posibleMoveX = xLocal;
                                posibleMoveY = yLocal;
                            } else break;
                        } else break;
                    }

                    if (riskCount == WINNER_SEQUENCE - 2 && posibleMoveX > -1) {
                        x = posibleMoveX;
                        y = posibleMoveY;
                        break;
                    }

// Check horizontal line
                    riskCount = 0;

                    for (int i = 1; i < WINNER_SEQUENCE; i++) {
                        xLocal = x;
                        yLocal = y - i;

                        if (yLocal > -1) {
                            if (map[xLocal][yLocal] == DOT_X) {
                                riskCount++;
                            } else if (map[xLocal][yLocal] == DOT_EMPTY) {
                                posibleMoveX = xLocal;
                                posibleMoveY = yLocal;
                            } else break;
                        } else break;
                    }

                    if (riskCount == WINNER_SEQUENCE - 2 && posibleMoveX > -1) {
                        x = posibleMoveX;
                        y = posibleMoveY;
                        break;
                    }

                    for (int i = 1; i < WINNER_SEQUENCE; i++) {
                        xLocal = x;
                        yLocal = y + i;

                        if (yLocal < SIZE) {
                            if (map[xLocal][yLocal] == DOT_X) {
                                riskCount++;
                            } else if (map[xLocal][yLocal] == DOT_EMPTY) {
                                posibleMoveX = xLocal;
                                posibleMoveY = yLocal;
                            } else break;
                        } else break;
                    }

                    if (riskCount == WINNER_SEQUENCE - 2 && posibleMoveX > -1) {
                        x = posibleMoveX;
                        y = posibleMoveY;
                        break;
                    }

// Check vertical line

                    riskCount = 0;

                    for (int i = 1; i < WINNER_SEQUENCE; i++) {
                        xLocal = x - i;
                        yLocal = y;

                        if (xLocal > -1) {
                            if (map[xLocal][yLocal] == DOT_X) {
                                riskCount++;
                            } else if (map[xLocal][yLocal] == DOT_EMPTY) {
                                posibleMoveX = xLocal;
                                posibleMoveY = yLocal;
                            } else break;
                        } else break;
                    }

                    if (riskCount == WINNER_SEQUENCE - 2 && posibleMoveX > -1) {
                        x = posibleMoveX;
                        y = posibleMoveY;
                        break;
                    }

                    for (int i = 1; i < WINNER_SEQUENCE; i++) {
                        xLocal = x + i;
                        yLocal = y;

                        if (xLocal < SIZE) {
                            if (map[xLocal][yLocal] == DOT_X) {
                                riskCount++;
                            } else if (map[xLocal][yLocal] == DOT_EMPTY) {
                                posibleMoveX = xLocal;
                                posibleMoveY = yLocal;
                            } else break;
                        } else break;
                    }

                    if (riskCount == WINNER_SEQUENCE - 2 && posibleMoveX > -1) {
                        x = posibleMoveX;
                        y = posibleMoveY;
                        break;
                    } else sillyMode();

                } while (false);
            }
        }


        // Единицы добавляются для согласования с выведенным полем на экране
        System.out.println("Computer move -> " + (y + 1) + " " + (x + 1) + "  [" + map[y][x] + "]");
        isCellValid(x,y);
        writeInMoves(compMoves, x, y);
        map[y][x] = DOT_O;
    }

    private static void sillyMode() {

        System.out.println("Silly mod activated (0.0)");

        x = -1;
        y = -1;
        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!isCellValid(x, y));
    }

    /*
    Поменял не много логику. Если попадает в поле и ячейка свободна то true.
     */
    private static boolean isCellValid(int x, int y) {
        boolean result = false;
        if (x > -1 && x < SIZE && y > -1 && y < SIZE) { // Если координаты в приделах поля
            if (map[y][x] == DOT_EMPTY) { // Если по этим координатам не занятая клетка
                result = true;
            }
            System.out.println("isCellValid  [" + y + "][" + x + "]-> " + map[y][x]);
        }

        return result;
    }

    private static boolean isEndGame(char plauerSumbol) {

        boolean resalt = false;

        printMap();



        if (checkWin(map,plauerSumbol, x, y)) {
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
