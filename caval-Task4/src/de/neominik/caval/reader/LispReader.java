package de.neominik.caval.reader;

import de.neominik.caval.lang.IFn;
import de.neominik.caval.lang.Keyword;
import de.neominik.caval.lang.Symbol;
import loader.PluginLoader;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LispReader {

    private static IFn[] macros = new IFn[256];
    private static Pattern intPat = Pattern.compile("([-+]?)(?:(0)|([1-9][0-9]*)|0[xX]([0-9A-Fa-f]+)|0([0-7]+)|([1-9][0-9]?)[rR]([0-9A-Za-z]+)|0[0-9]+)(N)?");
    private static Pattern floatPat = Pattern.compile("([-+]?[0-9]+(\\.[0-9]*)?([eE][-+]?[0-9]+)?)(M)?");
    private static Pattern symbolPat = Pattern.compile("[:]?(/|[\\D&&[^/]][^/]*)");

	static {
		PluginLoader.load(SpecialReader.class).stream()
				.flatMap(reader -> reader.getReadersForChars().entrySet().stream()).forEach(entry -> {
					macros[entry.getKey()] = entry.getValue();
				});
	}

    static int read1(Reader r) {
        try {
            return r.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isWhitespace(int ch) {
        return Character.isWhitespace(ch) || ch == ',';
    }

    private static Object readNumber(PushbackReader r, char initch) {
        StringBuilder sb = new StringBuilder();
        sb.append(initch);

        for (; ; ) {
            int ch = read1(r);
            if (ch == -1 || isWhitespace(ch) || isMacro(ch)) {
                unread(r, ch);
                break;
            }
            sb.append((char) ch);
        }

        String s = sb.toString();
        Object n = matchNumber(s);
        if (n == null)
            throw new NumberFormatException("Invalid number: " + s);
        return n;
    }

    private static boolean isMacro(int ch) {
        return (ch < macros.length && macros[ch] != null);
    }

    private static boolean isTerminatingMacro(int ch) {
        return isMacro(ch); // with additional features, we may have to add exceptions to this list
    }

    private static void unread(PushbackReader r, int ch) {
        try {
            r.unread(ch);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object matchNumber(String s) {
        Matcher m = intPat.matcher(s);
        if (m.matches()) {
            if (m.group(2) != null) {
                return 0;
            }
            boolean negate = (m.group(1).equals("-"));
            String n;
            int radix = 10;
            if ((n = m.group(3)) != null)
                radix = 10;
            else if ((n = m.group(4)) != null)
                radix = 16;
            else if ((n = m.group(5)) != null)
                radix = 8;
            else if ((n = m.group(7)) != null)
                radix = Integer.parseInt(m.group(6));
            if (n == null)
                return null;
            BigInteger bn = new BigInteger(n, radix);
            if (negate)
                bn = bn.negate();
            return bn.intValue();
        }
        m = floatPat.matcher(s);
        if (m.matches()) {
            if (m.group(4) != null)
                return new BigDecimal(m.group(1));
            return Double.parseDouble(s);
        }
        return null;
    }

    private static IFn getMacro(int ch) {
        if (ch < macros.length)
            return macros[ch];
        return null;
    }

    private static String readToken(PushbackReader r, char initch) {
        StringBuilder sb = new StringBuilder();
        sb.append(initch);

        for (; ; ) {
            int ch = read1(r);
            if (ch == -1 || ch == 65535 || isWhitespace(ch) || isTerminatingMacro(ch)) {
                unread(r, ch);
                return sb.toString();
            }
            sb.append((char) ch);
        }
    }

    private static Object interpretToken(String s) {
        switch (s) {
            case "nil":
                return null;
            case "true":
                return true;
            case "false":
                return false;
            default:
                Object ret = matchSymbol(s);
                if (ret != null) return ret;
                throw new RuntimeException("Invalid token: " + s);
        }
    }

    private static Object matchSymbol(String s) {
        Matcher m = symbolPat.matcher(s);
        if (m.matches()) {
            boolean isKeyword = s.charAt(0) == ':';
            Symbol sym = Symbol.create(s.substring(isKeyword ? 1 : 0));
            if (isKeyword) return Keyword.create(sym);
            return sym;
        }
        return null;
    }

    static int readUnicodeChar(PushbackReader r, int initch, int base, int length, boolean exact) {
        int uc = Character.digit(initch, base);
        if (uc == -1)
            throw new IllegalArgumentException("Invalid digit: " + (char) initch);
        int i = 1;
        for (; i < length; ++i) {
            int ch = read1(r);
            if (ch == -1 || isWhitespace(ch) || isMacro(ch)) {
                unread(r, ch);
                break;
            }
            int d = Character.digit(ch, base);
            if (d == -1)
                throw new IllegalArgumentException("Invalid digit: " + (char) ch);
            uc = uc * base + d;
        }
        if (i != length && exact)
            throw new IllegalArgumentException("Invalid character length: " + i + ", should be: " + length);
        return uc;
    }

    static Object read(PushbackReader r, boolean eofIsError, Character returnOn, Object returnOnValue) {
        try {
            for (; ; ) {
                int ch = read1(r);

                while (isWhitespace(ch)) {
                    ch = read1(r);
                }
                if (ch == -1) {
                    if (eofIsError)
                        throw new RuntimeException("EOF while reading");
                    return returnOnValue;
                }
                if (returnOn != null && ch == returnOn) {
                    return returnOnValue;
                }
                if (Character.isDigit(ch)) {
                    return readNumber(r, (char) ch);
                }
                IFn macroFn = getMacro(ch);
                if (macroFn != null) {
                    Object ret = macroFn.invoke(r, (char) ch);
                    if (ret == r) continue;
                    return ret;
                }
                if (ch == '+' || ch == '-') {
                    int ch2 = read1(r);
                    if (Character.isDigit(ch2)) {
                        unread(r, ch2);
                        return readNumber(r, (char) ch);
                    }
                    unread(r, ch2);
                }
                String token = readToken(r, (char) ch);
                return interpretToken(token);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object read(String line) {
        PushbackReader r = new PushbackReader(new java.io.StringReader(line));
        return read(r, null);
    }

    public Object read(PushbackReader r, Object returnOnEOFValue) {
        return read(r, false, null, returnOnEOFValue);
    }
}
