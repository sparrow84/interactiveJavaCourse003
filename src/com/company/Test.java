package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class Test {

    private static int SIZE = 3;
    private static char DOT_EMPTY = '*';
    private static char DOT_USER = 'X';
    private static int WINNER_SEQUENCE = 3;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        /** /
        char[][] arr = {{'X', '*', '*'},
                        {'*', 'X', '*'},
                        {'*', '*', 'X'}};
        /**/

/** /
 char[][] arr = {{'*','*','X'},
 {'*','X','*'},
 {'X','*','*'}};
 /**/

/** /
 char[][] arr = {{'X','*','*'},
 {'X','*','*'},
 {'X','*','*'}};
 /**/

/** /
 char[][] arr = {{'X','X','X'},
 {'*','*','*'},
 {'*','*','*'}};
 /**/

/**/
        char[][] arr = {{'*','*','X'},
                        {'*','X','*'},
                        {'*','*','*'}};
/**/


        printCharArray2D(arr);

        int a = 0;
        int b = 2;
/*
        int a = scanner.nextInt();
        int b = scanner.nextInt();
*/
        System.out.println("Entered numbers: " + a + " " + b);

        System.out.println(checkWin(arr, DOT_USER, a, b));


    }

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

//Check right diagonal
            winCount = 0;
            for (int i = 1; i < WINNER_SEQUENCE; i++) {
                if (a - i > -1 && b + i < SIZE) {
                    if (arr[a - i][b + i] == dotUser) {
                        winCount++;
                    } else break;
                } else break;
            }
            if (winCount == WINNER_SEQUENCE - 1) {
                result = true;
                break;
            }

            for (int i = 1; i < WINNER_SEQUENCE; i++) {
                if (a + i < SIZE && b - i > -1) {
                    if (arr[a + i][b - i] == dotUser) {
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


    private static void printCharArray2D(char[][] arr) {
        for (char[] arr1 : arr) {
            System.out.println(Arrays.toString(arr1));
        }
    }
}