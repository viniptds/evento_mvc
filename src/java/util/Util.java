/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.List;

/**
 *
 * @author rique
 */
public class Util 
{
    public static boolean isEmpty(String in) {
        return in == null || in.isEmpty();
    }

    public static boolean isNotEmpty(String in) {
        return !Util.isEmpty(in);
    }

    public static boolean isEmpty(List in) {
        return in == null || in.isEmpty();
    }

    public static boolean isNotEmpty(List in) {
        return !Util.isEmpty(in);
    }
    
}
