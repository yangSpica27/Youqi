package com.spica.app.tools;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

/**
 * @ClassName GodUtils
 * @Description 颜色处理工具类
 * @Author Spica2 7
 * @Date 2021/11/18 9:25
 */
@SuppressWarnings("unused")
public class ColorUtils {
  /**
   * 判断是否为ARGB格式的十六进制颜色，例如：FF990587
   *
   * @param string String
   * @return boolean
   */
  public static boolean judgeColorString(String string) {
    if (string.length() == 8) {
      for (int i = 0; i < string.length(); i++) {
        char cc = string.charAt(i);
        return !(cc != '0' && cc != '1' && cc != '2' && cc != '3' && cc != '4' && cc != '5' && cc != '6' && cc != '7' && cc != '8' && cc != '9' && cc != 'A' && cc != 'B' && cc != 'C' &&
            cc != 'D' && cc != 'E' && cc != 'F' && cc != 'a' && cc != 'b' && cc != 'c' && cc != 'd' && cc != 'e' && cc != 'f');
      }
    }
    return false;
  }

  /**
   * 颜色加深
   *
   * @param argbColor ARGB颜色值
   * @param darkValue 0-255 加深范围
   * @return
   */
  public static int TranslateDark(String argbColor, int darkValue) {
    int startAlpha = Integer.parseInt(argbColor.substring(0, 2), 16);
    int startRed = Integer.parseInt(argbColor.substring(2, 4), 16);
    int startGreen = Integer.parseInt(argbColor.substring(4, 6), 16);
    int startBlue = Integer.parseInt(argbColor.substring(6), 16);
    startRed -= darkValue;
    startGreen -= darkValue;
    startBlue -= darkValue;
    if (startRed < 0) startRed = 0;
    if (startGreen < 0) startGreen = 0;
    if (startBlue < 0) startBlue = 0;
    return Color.argb(startAlpha, startRed, startGreen, startBlue);
  }

  /**
   * 颜色加深
   *
   * @param colorInt ARGB颜色值
   * @param darkValue 0~255 加深范围
   * @return 加深后的颜色
   */
  public static int TranslateDark(int colorInt, int darkValue) {
    String argbColor = intToString(colorInt);
    int startAlpha = Integer.parseInt(argbColor.substring(0, 2), 16);
    int startRed = Integer.parseInt(argbColor.substring(2, 4), 16);
    int startGreen = Integer.parseInt(argbColor.substring(4, 6), 16);
    int startBlue = Integer.parseInt(argbColor.substring(6), 16);
    startRed -= darkValue;
    startGreen -= darkValue;
    startBlue -= darkValue;
    if (startRed < 0) startRed = 0;
    if (startGreen < 0) startGreen = 0;
    if (startBlue < 0) startBlue = 0;
    return Color.argb(startAlpha, startRed, startGreen, startBlue);
  }

  //颜色变浅，可调度数：0~255
  public static int TranslateLight(String color, int lightValue) {
    int startAlpha = Integer.parseInt(color.substring(0, 2), 16);
    int startRed = Integer.parseInt(color.substring(2, 4), 16);
    int startGreen = Integer.parseInt(color.substring(4, 6), 16);
    int startBlue = Integer.parseInt(color.substring(6), 16);

    startRed += lightValue;
    startGreen += lightValue;
    startBlue += lightValue;

    if (startRed > 255) startRed = 255;
    if (startGreen > 255) startGreen = 255;
    if (startBlue > 255) startBlue = 255;

    return Color.argb(startAlpha, startRed, startGreen, startBlue);
  }

  //颜色变浅，可调度数：0~255
  public static int TranslateLight(int colorInt, int lightValue) {
    String color = intToString(colorInt);
    int startAlpha = Integer.parseInt(color.substring(0, 2), 16);
    int startRed = Integer.parseInt(color.substring(2, 4), 16);
    int startGreen = Integer.parseInt(color.substring(4, 6), 16);
    int startBlue = Integer.parseInt(color.substring(6), 16);

    startRed += lightValue;
    startGreen += lightValue;
    startBlue += lightValue;

    if (startRed > 255) startRed = 255;
    if (startGreen > 255) startGreen = 255;
    if (startBlue > 255) startBlue = 255;

    return Color.argb(startAlpha, startRed, startGreen, startBlue);
  }

  //不透明度加强，可调度数：0~255
  public static int DarkAlpha(int colorInt, int addValue) {
    String color = intToString(colorInt);
    int startAlpha = Integer.parseInt(color.substring(0, 2), 16);
    int startRed = Integer.parseInt(color.substring(2, 4), 16);
    int startGreen = Integer.parseInt(color.substring(4, 6), 16);
    int startBlue = Integer.parseInt(color.substring(6), 16);

    startAlpha += addValue;
    if (startAlpha > 255) startAlpha = 255;
    return Color.argb(startAlpha, startRed, startGreen, startBlue);
  }

  //不透明度加强，可调度数：0~255
  public static int DarkAlpha(String color, int addValue) {

    int startAlpha = Integer.parseInt(color.substring(0, 2), 16);
    int startRed = Integer.parseInt(color.substring(2, 4), 16);
    int startGreen = Integer.parseInt(color.substring(4, 6), 16);
    int startBlue = Integer.parseInt(color.substring(6), 16);

    startAlpha += addValue;
    if (startAlpha > 255) startAlpha = 255;
    return Color.argb(startAlpha, startRed, startGreen, startBlue);
  }

  //透明度加强，可调度数：0~255
  public static int LightAlpha(int colorInt, int darkValue) {
    String argbColor = intToString(colorInt);
    int startAlpha = Integer.parseInt(argbColor.substring(0, 2), 16);
    int startRed = Integer.parseInt(argbColor.substring(2, 4), 16);
    int startGreen = Integer.parseInt(argbColor.substring(4, 6), 16);
    int startBlue = Integer.parseInt(argbColor.substring(6), 16);
    startAlpha -= darkValue;

    if (startAlpha < 0) startAlpha = 0;
    return Color.argb(startAlpha, startRed, startGreen, startBlue);
  }

  //透明度加强，可调度数：0~255
  public static int LightAlpha(String argbColor, int darkValue) {

    int startAlpha = Integer.parseInt(argbColor.substring(0, 2), 16);
    int startRed = Integer.parseInt(argbColor.substring(2, 4), 16);
    int startGreen = Integer.parseInt(argbColor.substring(4, 6), 16);
    int startBlue = Integer.parseInt(argbColor.substring(6), 16);
    startAlpha -= darkValue;

    if (startAlpha < 0) startAlpha = 0;
    return Color.argb(startAlpha, startRed, startGreen, startBlue);
  }

  //将16进制颜色（String）转化为10进制（Int）
  public static int StringTransInt(String color) {
    int startAlpha = Integer.parseInt(color.substring(0, 2), 16);
    int startRed = Integer.parseInt(color.substring(2, 4), 16);
    int startGreen = Integer.parseInt(color.substring(4, 6), 16);
    int startBlue = Integer.parseInt(color.substring(6), 16);
    return Color.argb(startAlpha, startRed, startGreen, startBlue);
  }

  //将10进制颜色（int）值转换成16进制(String)
  public static String intToString(int value) {
    String hexString = Integer.toHexString(value);
    if (hexString.length() == 1) {
      hexString = "0" + hexString;
    }
    return hexString;
  }

  //将10进制颜色（Int）转换为Drawable对象
  public static Drawable intToDrawable(int color) {
    return new ColorDrawable(color);
  }

  //将16进制颜色（String）转化为Drawable对象
  public static Drawable stringToDrawable(String color) {
    return new ColorDrawable(StringTransInt(color));
  }

  //截屏
  public static Bitmap takeScreenShot(Activity activity) {
    View view = activity.getWindow().getDecorView();
    view.setDrawingCacheEnabled(true);
    view.buildDrawingCache();
    Bitmap b1 = view.getDrawingCache();

    // 获取屏幕长和高
    int width = activity.getResources().getDisplayMetrics().widthPixels;
    int height = activity.getResources().getDisplayMetrics().heightPixels;
    Bitmap bmp = Bitmap.createBitmap(b1, 0, 0, width, height);
    view.destroyDrawingCache();
    return bmp;
  }

  //显示自定义时间长度的Toast
  public static void showToast(final Activity activity, final String word, final long time) {
    activity.runOnUiThread(new Runnable() {
      public void run() {
        final Toast toast = Toast.makeText(activity, word, Toast.LENGTH_LONG);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
          public void run() {
            toast.cancel();
          }
        }, time);
      }
    });
  }
}
