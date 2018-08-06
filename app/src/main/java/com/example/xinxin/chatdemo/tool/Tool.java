package com.example.xinxin.chatdemo.tool;

import android.content.Context;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tool {

	/**
	 * 处理接收字节
	 * 
	 * @param count
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] getByte(long count, InputStream inputStream)
			throws IOException {
		byte[] buffer = new byte[1024];
		long position = 0;
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		if (count < 1024) {
			buffer = new byte[(int) count];
		}
		while (position < count) {
			if ((count - position) < buffer.length)
				buffer = new byte[(int) (count - position)];
			int len = inputStream.read(buffer);
			position += len;
//			showLog("Tool",position+ " len "+len);
			if (position == -1) {
				return null;
			}
			arrayOutputStream.write(buffer, 0, len);
		}
		arrayOutputStream.flush();
		arrayOutputStream.close();
		buffer = arrayOutputStream.toByteArray();
		String strTemp = new String(buffer, "utf-8");
//		showLog("Tool",count+ " getByte "+strTemp);
		return buffer;
	}
	/**
	 * 
	 * @param context
	 * @param text
	 * @param delay
	 *            0--LENGTH_SHORT
	 */
	public static void showToast(Context context, Object text, int delay) {
		if (delay == 0) {
			Toast.makeText(context, text + "", Toast.LENGTH_SHORT).show();
			return;
		}
		Toast.makeText(context, text + "", Toast.LENGTH_LONG).show();
		return;
	}

	public static void showLog(String filter, Object text) {
		android.util.Log.e(filter, "--- " + text + " ---");
	}
	
	public static boolean isIp(String text) {
		String regex = "^[0-9]{1,3}(.[0-9]{1,3}){3}$";
		return text.matches(regex);
	}

	public static boolean isGroup(String text) {
		String regex = "^[0-9]{2}(-[0-9A-F]{2}){5}$";
		return text.matches(regex);
	}

	public static boolean isReg(String text) {
		String regex = "^([0-9A-F]{2}-){5}[0-9A-F]{2}$";
		return text.matches(regex);
	}

	public static boolean isUser(String text) {
		if ("FF-FF-FF-FF-FF-FF".equals(text))
			return false;
		String regex = "^F[0F](-[0-9A-F]{2}){5}$";
		return text.matches(regex);
	}

	public static boolean isNewsShare(String text) {
		if (!"FF-FF-FF-FF-FF-FF".equals(text))
			return false;
		return true;
	}

	public static boolean isHtml(String text) {
		// String
		// regex="^((https?|ftp|news):\\/\\/)?([a-z]([a-z0-9\\-]*[\\.。])+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)|(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))(\\/[a-z0-9_\\-\\.~]+)*(\\/([a-z0-9_\\-\\.]*)(\\?[a-z0-9+_\\-\\.%=&]*)?)?(#[a-z][a-z0-9_]*)?$";
		String regex = "(https?|ftp|www).*";
		return text.matches(regex);
	}

	public static String getHtml(String text) {
		if (text == null)
			return null;
		String[] ts = text.split(" ");
		for (String string : ts) {
			if (isHtml(string))
				return string.trim();
		}
		return null;
	}

	public static boolean isMobilePhone(String phone){
		Pattern pattern = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[6-8])|(18[0-9]))\\d{8}$");
		Matcher matcher = pattern.matcher(phone);
		return matcher.matches();

	}

}
