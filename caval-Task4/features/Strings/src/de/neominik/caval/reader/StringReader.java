package de.neominik.caval.reader;

import de.neominik.caval.lang.AFn;
import de.neominik.caval.lang.IFn;

import java.io.PushbackReader;
import java.util.Map;

import static de.neominik.caval.reader.LispReader.read1;
import static de.neominik.caval.reader.LispReader.readUnicodeChar;

public class StringReader extends AFn implements SpecialReader {
    @Override
    public Object invoke(Object reader, Object doublequote) {
        StringBuilder sb = new StringBuilder();
        PushbackReader r = (PushbackReader) reader;

        for (int ch = read1(r); ch != '"'; ch = read1(r)) {
            if (ch == -1) throw new RuntimeException("EOF while reading string");
            if (ch == '\\') { // escape
                ch = read1(r);
                if (ch == -1) throw new RuntimeException("EOF while reading string");
                switch (ch) {
                    case 't':
                        ch = '\t';
                        break;
                    case 'r':
                        ch = '\r';
                        break;
                    case 'n':
                        ch = '\n';
                        break;
                    case '\\':
                        break;
                    case '"':
                        break;
                    case 'b':
                        ch = '\b';
                        break;
                    case 'f':
                        ch = '\f';
                        break;
                    case 'u':
                        ch = read1(r);
                        if (Character.digit(ch, 16) == -1)
                            throw new RuntimeException("Invalid unicode escape: \\u" + (char) ch);
                        ch = readUnicodeChar(r, ch, 16, 4, true);
                        break;
                    default:
                        if (Character.isDigit(ch)) {
                            ch = readUnicodeChar(r, ch, 8, 3, false);
                            if (ch > 0377)
                                throw new RuntimeException("Octal escape sequence must be in range [0, 377].");
                        } else
                            throw new RuntimeException("Unsupported escape character: \\" + (char) ch);
                }
            }
            sb.append((char) ch);
        }
        return sb.toString();
    }

	@Override
	public Map<Character, IFn> getReadersForChars() {
		return Map.of('"', new StringReader());
	}
}
