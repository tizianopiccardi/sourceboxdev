package cc.sourcebox.beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class UrlHelper
 */
@Stateless
@LocalBean
public class UrlHelper implements UrlHelperLocal {

	private static final String baseDigits = "TpAlDxWiOCIk59mSeX2oMzPjsbuaZhrEqL07KgwcYFJ34GBNf8UV6nyvQHd1tR";

	private String fromDecimalToOtherBase(int base, int n) {
		if (n == 0)
			return "0";

		String s = "";
		while (n > 0) {
			int d = n % base;
			s = baseDigits.charAt(d) + s;
			n = n / base;
		}
		return s;
	}

	public void inputError(String s) {
		throw new RuntimeException("Input error with" + s);
	}

	private int fromOtherBaseToDecimal(int b, String s) {
		int result = 0;
		int digit = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= '0' && c <= '9')
				digit = c - '0';
			else if (c >= 'A' && c <= 'Z')
				digit = 10 + c - 'A';
			else
				inputError(s);

			if (digit < b)
				result = b * result + digit;
			else
				inputError(s);
		}
		return result;
	}

	@Override
	public String idToAlias(int id) {
		return fromDecimalToOtherBase(62, id);
	}

	@Override
	public int aliasToId(String alias) {
		return fromOtherBaseToDecimal(62, alias);
	}

}
