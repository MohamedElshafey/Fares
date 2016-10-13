package com.qyadat.fares;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LiveWallpaperService extends WallpaperService {
	int r = 15;
	int random = 0 ;
	private int speed = 1;
	public void onCreate() {
		super.onCreate();

	}

	public void onDestroy() {
		super.onDestroy();
	}

	public Engine onCreateEngine() {
		return new MyWallpaperEngine();
	}

	class MyWallpaperEngine extends Engine implements
			OnSharedPreferenceChangeListener {

		private int speed = 1;
		private int bubble = 8;
		private String bubbleDirection = "random";

		private SharedPreferences mPreferences;
		Timer timer;
		TimerTask task;
		private int wallaperTiming;
		private boolean isChangeWallpaper = false;
		private final Handler handler = new Handler();

		private final Runnable drawRunner = new Runnable() {
			@Override
			public void run() {
				draw(bubbleDirection);
			}
		};

		private boolean visible = true;
		public Bitmap backgroundImage;

		ArrayList<Bubble> bubbles = new ArrayList<Bubble>();
		public void onCreate(SurfaceHolder surfaceHolder) {

			super.onCreate(surfaceHolder);

			new MyWallpaperEngine();
			mPreferences = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			mPreferences.registerOnSharedPreferenceChangeListener(this);
			setBackGroundImage(mPreferences);

			wallaperTiming = Integer.parseInt(mPreferences.getString(
					getString(R.string.key_lp_timing), "3")) * 1000;

			speed = Integer.parseInt(mPreferences.getString(
					getString(R.string.key_lp_speed), "10"));
			bubble = Integer.parseInt(mPreferences.getString(
					getString(R.string.key_lp_bubble), "10"));
			bubbleDirection = mPreferences.getString(
					getString(R.string.key_lp_direction), "random");
			boolean isSlide = mPreferences.getBoolean(
					getString(R.string.key_cb_slidewallpaper), true);
			if (isSlide) {
				isChangeWallpaper = true;
			} else {
				isChangeWallpaper = false;
			}
			runTimerTask();
			setTouchEventsEnabled(true);
		}

		MyWallpaperEngine() {
			Bubble.setHeightWidth(getApplicationContext());
			bubbles.clear();
			bubbles.add(new Bubble(110, 110,R.drawable.bu));
			bubbles.add(new Bubble(50, 50,R.drawable.bu1));
			bubbles.add(new Bubble(40, 40,R.drawable.bu1));
			bubbles.add(new Bubble(100, 100,R.drawable.bu));
			bubbles.add(new Bubble(110, 110,R.drawable.bu1));
			bubbles.add(new Bubble(150, 150,R.drawable.bu));
			bubbles.add(new Bubble(60, 60,R.drawable.bu));
			bubbles.add(new Bubble(65, 65,R.drawable.bu1));
			bubbles.add(new Bubble(30, 30,R.drawable.bu));
			bubbles.add(new Bubble(50, 50,R.drawable.bu));
			bubbles.add(new Bubble(110, 110,R.drawable.bu));
			bubbles.add(new Bubble(50, 50,R.drawable.bu1));
			bubbles.add(new Bubble(40, 40,R.drawable.bu1));
			bubbles.add(new Bubble(100, 100,R.drawable.bu));
			bubbles.add(new Bubble(110, 110,R.drawable.bu1));
			bubbles.add(new Bubble(150, 150,R.drawable.bu1));
			bubbles.add(new Bubble(60, 60,R.drawable.bu));
			bubbles.add(new Bubble(65, 65,R.drawable.bu1));
			bubbles.add(new Bubble(30, 30,R.drawable.bu));
			backgroundImage = BitmapFactory.decodeResource(getResources(),R.drawable.fares1);
		}

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			speed = Integer.parseInt(mPreferences.getString(
					getString(R.string.key_lp_speed), "1"));
			bubble = Integer.parseInt(mPreferences.getString(
					getString(R.string.key_lp_bubble), "6"));
			bubbleDirection = sharedPreferences.getString(
					getString(R.string.key_lp_direction), "left");

			boolean isSlide = sharedPreferences.getBoolean(
					getString(R.string.key_cb_slidewallpaper), true);

			if (isSlide) {
				isChangeWallpaper = true;
			} else {
				isChangeWallpaper = false;
			}

			int oldtime = wallaperTiming;
			wallaperTiming = Integer.parseInt(sharedPreferences.getString(
					getString(R.string.key_lp_timing), "2")) * 1000;
			if (wallaperTiming != oldtime) {
				timer.cancel();
				runTimerTask();
			}

			setBackGroundImage(sharedPreferences);
		}
		public void setBackGroundImage(SharedPreferences prefs) {
			int value = Integer.parseInt(prefs.getString(
					getString(R.string.key_lp_wall), "0"));
			setBitmap(value);
		}

		@Override
		public void onDestroy() {
			timer.cancel();
			super.onDestroy();
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			this.visible = visible;
			// if screen wallpaper is visible then draw the image otherwise do
			// not draw
			if (visible) {
				handler.post(drawRunner);
			} else {
				handler.removeCallbacks(drawRunner);
			}
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			this.visible = false;

			handler.removeCallbacks(drawRunner);
		}

		public void onOffsetsChanged(float xOffset, float yOffset, float xStep,
				float yStep, int xPixels, int yPixels) {
			draw(bubbleDirection);
		}

		void drawLeft() {
			final SurfaceHolder holder = getSurfaceHolder();


			handler.removeCallbacks(drawRunner);
			Canvas c = null;
			r += 15;
			try {
				// clear the canvas

				c = holder.lockCanvas();

				// draw the background image

				if (c != null) {

					// draw the background image
					c.drawBitmap(
							Bitmap.createScaledBitmap(backgroundImage,
									c.getWidth(), c.getHeight(), false), 0, 0,
							null);

					for (int i = 0; i < bubble; i++) {
						drawCircleCanvasToLeft(c,bubbles.get(i));
					}
				}
			} catch (Exception e) {
			} finally {
				if (c != null)
					holder.unlockCanvasAndPost(c);
			}


		}
		private void drawCircleCanvasToUp(Canvas c, Bubble image1) {
			// int width = c.getWidth();

			if (image1.getY() >= 0) {
				int tmpx = image1.getY();
				tmpx -= speed;
				image1.setY(tmpx);

			} else {
				image1.setX(image1.getRandomYany());
				image1.setY(image1.getRandomYend());

				// y.set(14, width / 2);
			}
			c.drawBitmap(image1.getImage(), image1.getX(), image1.getY(), null);
		}

		private void drawCircleCanvasToLeft(Canvas c, Bubble image1) {

			if (image1.getX() >= 0) {
				int tmpx = image1.getX();
				tmpx -= speed;
				image1.setX(tmpx);

			} else {
				image1.setX(image1.getRandomXend());
				image1.setY(image1.getRandomXany());
				// y.set(14, width / 2);
			}
			c.drawBitmap(image1.getImage(), image1.getX(), image1.getY(), null);

		}

//		@Override
//		public Bundle onCommand(String action, int x, int y, int z, Bundle extras, boolean resultRequested) {
//			if (action.equals(WallpaperManager.COMMAND_TAP)) {
//				// do whatever you would have done on ACTION_UP
////				bubbles.clear();
////				new MyWallpaperEngine();
//////				bubbleDirection = "left";
//////				runTimerTask();
////				drawLeft();
//
//			}
//			return null;
//		}

		void drawRandom() {
			final SurfaceHolder holder = getSurfaceHolder();

			Canvas c = null;
			r += 15;
			try {
				c = holder.lockCanvas();
				// clear the canvas
				c.drawColor(Color.BLACK);
				if (c != null) {

					// draw the background image
					c.drawBitmap(
							Bitmap.createScaledBitmap(backgroundImage,
									c.getWidth(), c.getHeight(), false), 0, 0,
							null);
					if(random == 0 ) {
						for (int i = 0; i < bubble; i++) {
							drawCircleCanvasToRightBottom(c,bubbles.get(i));
						}
						random = 0;
					}
				}
			} catch (Exception e) {
			} finally {
				if (c != null)
					holder.unlockCanvasAndPost(c);
			}

			handler.removeCallbacks(drawRunner);
			if (visible) {
				handler.postDelayed(drawRunner, 10); // delay 10 mileseconds
			}
		}

		public void runTimerTask() {
			task = new TimerTask() {
				int i = 0;

				public void run() {
					if (isChangeWallpaper) {
						setBitmap(i);
						i++;
					}
					if (i == 5) {
						i = 0;
					}
					draw(bubbleDirection);
				}
			};
			timer = new Timer();
			timer.schedule(task, 0, wallaperTiming);
		}

		private void draw(String bubbleDirection) {
			if(bubbleDirection.equals("left")){
				drawLeft();
			}else {
				drawRandom();
			}
		}
		private void setBitmap(int i) {
			switch (i) {
			case 0:
				backgroundImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.fares1);
				break;
			case 1:
				backgroundImage = BitmapFactory.decodeResource(getResources(),
						R.drawable.fares2);
				break;
			case 2:
					backgroundImage = BitmapFactory.decodeResource(getResources(),
							R.drawable.fares3);
				break;
			case 3:
					backgroundImage = BitmapFactory.decodeResource(getResources(),
							R.drawable.fares4);
				break;
			case 4:
					backgroundImage = BitmapFactory.decodeResource(getResources(),
							R.drawable.fares5);
				break;
			}
		}
	}
	private void drawCircleCanvasToRightBottom(Canvas c, Bubble image1) {

		if (image1.getX() <= Bubble.width || image1.getY() <= Bubble.height) {
			int tmpx = image1.getX();
			tmpx += speed;
			image1.setX(tmpx);

			int tmpy = image1.getY();
			tmpy += speed;
			image1.setY(tmpy);
		} else {
			image1.setX(image1.getRandomX());
			image1.setY(image1.getRandomY());
		}
		c.drawBitmap(image1.getImage(), image1.getX(), image1.getY(), null);
	}
}
