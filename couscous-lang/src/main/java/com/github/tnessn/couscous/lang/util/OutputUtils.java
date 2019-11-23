package com.github.tnessn.couscous.lang.util;

// TODO: Auto-generated Javadoc
/**
 * The Class OutputUtils.
 *
 * @author huangjinfeng
 */
public class OutputUtils {

    /**  行分割符. */
    private static final String LINE_SEPARATOR;

    static {
        String ls = System.getProperty("line.separator");
        if (ls == null) {
            ls = "\n";
        }
        LINE_SEPARATOR = ls;
    }

   
    /**
     * 换行.
     *
     * @param sb a StringBuilder to append to
     */
    public static void newLine(StringBuilder sb) {
        sb.append(LINE_SEPARATOR);
    }
}
