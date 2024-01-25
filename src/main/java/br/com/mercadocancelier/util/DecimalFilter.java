package br.com.mercadocancelier.util;

import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class DecimalFilter extends DocumentFilter {
	private final DecimalFormat format = new DecimalFormat("#.###");

	@Override
	public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr)
			throws BadLocationException {
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder(doc.getText(0, doc.getLength()));
		sb.insert(offset, string);

		if (isDecimal(sb.toString())) {
			super.insertString(fb, offset, string, attr);
		}
	}

	@Override
	public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder(doc.getText(0, doc.getLength()));

		if (text != null) {
			sb.replace(offset, offset + length, text);
		}

		if (isDecimal(sb.toString())) {
			super.replace(fb, offset, length, text, attrs);
		}
	}

	private boolean isDecimal(String str) {
		try {
			Number number = format.parse(str);
			double value = number.doubleValue();
			return value >= 0 && value < 1000;
		} catch (ParseException e) {
			return false;
		}
	}
}

