/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author eduardo.sawabe
 */
public final class Constants {

    public Constants() {
    }

    public static int MAX_MINUTES = 1;
    public static int MAX_MINUTES_BREAK = 15;
    public static int MAX_ROUNDS = 28;
    public static double BUY_IN_VALUE = 30.00;
    public static double REBUY_VALUE = 30.00;
    public static int MAX_REBUY = 99999999;
    public static int SECONDS_IN_MINUTE = 60;
    public static int TOTAL_ROUND_TIME =  MAX_MINUTES * SECONDS_IN_MINUTE;
    public static int TOTAL_BREAK_TIME =  MAX_MINUTES_BREAK * SECONDS_IN_MINUTE;
}
