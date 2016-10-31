package com.chinatel.robot.Util;

import android.text.Selection;
import android.text.Spannable;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharUtil {
	public static void cursorPosition(CharSequence paramCharSequence) {
		if ((paramCharSequence instanceof Spannable))
			Selection.setSelection((Spannable) paramCharSequence,
					paramCharSequence.length());
	}

	private static boolean isChinese(char paramChar) {
		Character.UnicodeBlock localUnicodeBlock = Character.UnicodeBlock
				.of(paramChar);
		return (localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)
				|| (localUnicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS)
				|| (localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A)
				|| (localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B)
				|| (localUnicodeBlock == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION)
				|| (localUnicodeBlock == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
				|| (localUnicodeBlock == Character.UnicodeBlock.GENERAL_PUNCTUATION);
	}

	public static boolean isChinese(String paramString) {
		char[] arrayOfChar = paramString.toCharArray();
		for (int i = 0;; i++) {
			if (i >= arrayOfChar.length)
				return false;
			if (isChinese(arrayOfChar[i]))
				return true;
		}
	}

	public static boolean isChineseByName(String paramString) {
		if (paramString == null)
			return false;
		return Pattern.compile("\\p{InCJK Unified Ideographs}&&\\P{Cn}")
				.matcher(paramString.trim()).find();
	}

	public static boolean isChineseByREG(String paramString) {
		if (paramString == null)
			return false;
		return Pattern.compile("[\\u4E00-\\u9FBF]+")
				.matcher(paramString.trim()).find();
	}

	public static void main(String[] paramArrayOfString) {
		String[] arrayOfString = { "www.micmiu.com",
				"!@#$%^&*()_+{}[]|\"'?/:;<>,.", "！￥……（）——：；“”‘'《》，。？、", "不要啊",
				"やめて", "韩佳人", "???" };
		int i = arrayOfString.length;
		char[] arrayOfChar;
		int k;
		for (int j = 0;; j++) {
			if (j >= i)
				return;
			String str1 = arrayOfString[j];
			System.out.println("===========> 测试字符串：" + str1);
			System.out.println("正则判断结果：" + isChineseByREG(str1) + " -- "
					+ isChineseByName(str1));
			System.out.println("Unicode判断结果 ：" + isChinese(str1));
			System.out.println("详细判断列表：");
			arrayOfChar = str1.toCharArray();
			k = 0;
			if (k < arrayOfChar.length)
				break;
		}
		char c = arrayOfChar[k];
		PrintStream localPrintStream = System.out;
		StringBuilder localStringBuilder = new StringBuilder(String.valueOf(c))
				.append(" --> ");
		if (isChinese(c))
			;
		for (String str2 = "是";; str2 = "否") {
			localPrintStream.println(str2);
			k++;
			break;
		}
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Util.CharUtil JD-Core Version: 0.6.2
 */