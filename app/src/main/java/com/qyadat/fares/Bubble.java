package com.qyadat.fares;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import java.util.Random;

public class Bubble {

	public static int width, height;
	int x;
	int y;
	Bitmap image;
	Random r;
	static Context c;

	public static void setHeightWidth(Context c) {
		Bubble.c = c;
		DisplayMetrics displayMetrics = c.getApplicationContext()
				.getResources().getDisplayMetrics();
		width = displayMetrics.widthPixels;
		height = displayMetrics.heightPixels;
	}

	Bubble(int dstWidth, int dstHeight , int resource) {
		r = new Random();
		x = r.nextInt(width);
		y = r.nextInt(height);
		image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(c.getResources(),resource),dstHeight,dstWidth,false);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public int getRandomX() {
		return r.nextInt(10);
	}

	public int getRandomY() {
		return r.nextInt(10);
	}

	public int getRandomXend() {
		return (r.nextInt(width - (width - 10)) + (width - 10));
	}

	public int getRandomYend() {
		return r.nextInt(height - (height - 10)) + (height - 10);
	}

	public int getRandomXmid() {
		return (r.nextInt(width / 2 - (width / 2 - 10)) + (width / 2 - 10));
	}

	public int getRandomYmid() {
		return r.nextInt(height / 2 - (height / 2 - 100)) + (height / 2 - 100);
	}

	public int getRandomXany() {
		return r.nextInt(width);
	}

	public int getRandomYany() {
		return r.nextInt(height);
	}
}
