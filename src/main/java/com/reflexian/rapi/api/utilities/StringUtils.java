package com.reflexian.rapi.api.utilities;

import lombok.experimental.UtilityClass;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class StringUtils {


    /**
     * @return boolean
     */
    public static boolean getBoolean(String fromString) {
        try {
            return fromString.equalsIgnoreCase("true") || fromString.equalsIgnoreCase("yes");
        } catch (Exception er) {
            return false;
        }
    }

    /**
     *  Convert String to Math and Calculate exempt
     * @return double
     */
    public static double calculate(String val) {
        if(val.contains("(")&&val.contains(")")) {
            val=splitter(val);
        }
        if(val.contains("*")||val.contains("/")) {
            Matcher s = extra.matcher(val);
            while(s.find()) {
                double a = getDouble(s.group(1));
                String b = s.group(3);
                double d = getDouble(s.group(4));
                val=val.replace(s.group(), (a==0||d==0?0:((b.equals("*")?a*d:a/d)))+"");
                s = extra.matcher(val);
            }
        }
        if(val.contains("+")||val.contains("-")) {
            Matcher s = normal.matcher(val);
            while(s.find()) {
                double a = getDouble(s.group(1));
                String b = s.group(3);
                double d = getDouble(s.group(4));
                val=val.replace(s.group(), (b.equals("+")?a+d:a-d)+"");
                s = normal.matcher(val);
            }
        }
        return getDouble(val.replaceAll("[^0-9+.-]", ""));
    }

    private static String splitter(String s) {
        String i = "", fix = "";

        int count = 0, waiting = 0;
        for(char c : s.toCharArray()) {
            i+=""+c;
            if(c=='(') {
                fix+=""+c;
                waiting=1;
                ++count;
            }else
            if(c==')') {
                fix+=""+c;
                if(--count==0) {
                    waiting=0;
                    i=i.replace(fix, ""+simpleCalculate(fix.substring(1, fix.length()-1)));
                    fix="";
                }
            }else
            if(waiting==1)
                fix+=""+c;
        }
        return i;
    }

    private static Pattern extra = Pattern.compile("((^[-])?[ ]*[0-9.]+)[ ]*([*/])[ ]*(-?[ ]*[0-9.]+)"),
            normal = Pattern.compile("((^[-])?[ ]*[0-9.]+)[ ]*([+-])[ ]*(-?[ ]*[0-9.]+)");


    private static double simpleCalculate(String val) {
        if(val.contains("(")&&val.contains(")")) {
            val=splitter(val);
        }
        if(val.contains("*")||val.contains("/")) {
            Matcher s = extra.matcher(val);
            while(s.find()) {
                double a = getDouble(s.group(1));
                String b = s.group(3);
                double d = getDouble(s.group(4));
                val=val.replace(s.group(), (a==0||d==0?0:((b.equals("*")?a*d:a/d)))+"");
                s = extra.matcher(val);
            }
        }
        if(val.contains("+")||val.contains("-")) {
            Matcher s = normal.matcher(val);
            while(s.find()) {
                double a = getDouble(s.group(1));
                String b = s.group(3);
                double d = getDouble(s.group(4));
                val=val.replace(s.group(), (b.equals("+")?a+d:a-d)+"");
                s = normal.matcher(val);
            }
        }
        return getDouble(val.replaceAll("[^0-9+.-]", ""));
    }

    static Pattern mat = Pattern.compile("\\.([0-9])([0-9])?");

    public static String fixedFormatDouble(double val) {
        String text = String.format(Locale.ENGLISH,"%.2f", val);
        Matcher m = mat.matcher(text);
        if (m.find()) {
            if (m.groupCount() != 2) {
                if (m.group(1).equals("0")) {
                    return m.replaceFirst("");
                }
                return m.replaceFirst(".$1");
            }
            if (m.group(1).equals("0")) {
                if (m.group(2).equals("0")) {
                    return m.replaceFirst("");
                }
                return m.replaceFirst(".$1$2");
            }
            if (m.group(2).equals("0")) {
                return m.replaceFirst(".$1");
            }
            return m.replaceFirst(".$1$2");
        }
        return text;
    }

    /**
     * Get double from string
     * @return double
     */
    public static double getDouble(String fromString) {
        if (fromString == null)
            return 0.0D;
        String a = fromString.replaceAll("[^+0-9E.,-]+", "").replace(",", ".");
        if (isDouble(a)) {
            return Double.parseDouble(a);
        } else {
            return 0.0;
        }
    }

    /**
     *  Is string, double ?
     * @return boolean
     */
    public static boolean isDouble(String fromString) {
        try {
            Double.parseDouble(fromString);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    /**
     * Get long from string
     * @return long
     */
    public static long getLong(String fromString) {
        return (long) getFloat(fromString);
    }

    /**
     * Is string, long ?
     * @return
     */
    public static boolean isLong(String fromString) {
        try {
            Long.parseLong(fromString);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Get int from string
     * @return int
     */
    public static int getInt(String fromString) {
        return (int) (getDouble(fromString) == 0 ? getLong(fromString) : getDouble(fromString));
    }

    /**
     * Is string, int ?
     * @return boolean
     */
    public static boolean isInt(String fromString) {
        try {
            Integer.parseInt(fromString);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    /**
     * Is string, float ?
     * @return boolean
     */
    public static boolean isFloat(String fromString) {
        try {
            Float.parseFloat(fromString);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Get float from string
     * @return float
     */
    public static float getFloat(String fromString) {
        if (fromString == null)
            return 0F;
        String a = fromString.replaceAll("[^+0-9E.,-]+", "").replace(",", ".");
        if (isFloat(a)) {
            return Float.parseFloat(a);
        } else {
            return 0;
        }
    }

    /**
     * Is string, float ?
     * @return boolean
     */
    public static boolean isByte(String fromString) {
        try {
            Byte.parseByte(fromString);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Get float from string
     * @return float
     */
    public static byte getByte(String fromString) {
        if (fromString == null)
            return (byte) 0;
        String a = fromString.replaceAll("[^+0-9E-]+", "");
        if (isByte(a)) {
            return Byte.parseByte(a);
        } else {
            return (byte) 0;
        }
    }

    /**
     * Is string, float ?
     * @return boolean
     */
    public static boolean isShort(String fromString) {
        try {
            Short.parseShort(fromString);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Get float from string
     * @return float
     */
    public static short getShort(String fromString) {
        if (fromString == null)
            return (short) 0;
        String a = fromString.replaceAll("[^+0-9E-]+", "");
        if (isShort(a)) {
            return Short.parseShort(a);
        } else {
            return (short) 0;
        }
    }

    /**
     * Is string, number ?
     * @return boolean
     */
    public static boolean isNumber(String fromString) {
        return isInt(fromString) || isDouble(fromString) || isLong(fromString) || isByte(fromString)
                || isShort(fromString) || isFloat(fromString);
    }

    /**
     * Is string, boolean ?
     * @return boolean
     */
    public static boolean isBoolean(String fromString) {
        if (fromString == null)
            return false;
        return fromString.equalsIgnoreCase("true") || fromString.equalsIgnoreCase("false")
                || fromString.equalsIgnoreCase("yes") || fromString.equalsIgnoreCase("no");
    }

    private static Pattern special = Pattern.compile("[^A-Z-a-z0-9_]+");

    public static boolean containsSpecial(String value) {
        return special.matcher(value).find();
    }

    public static Number getNumber(String o) {
        if (isDouble(o))
            return getDouble(o);
        if (isInt(o))
            return getInt(o);
        if (isLong(o))
            return getLong(o);
        if (isByte(o))
            return getByte(o);
        if (isShort(o))
            return getShort(o);
        if (isFloat(o))
            return getFloat(o);
        return null;
    }


}
