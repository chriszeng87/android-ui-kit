package com.example.chris;



import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/*游戏界面的类*/
public class MainView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
	private Canvas canvas;			// 画布资源
	private Paint paint; 			// 画笔
	private SurfaceHolder sfh;
	private Thread thread;			// 绘图线程
	private MyPlane myPlane;
	private int scoreSum;			//总积分
	private int enemyCount;
	private int smallCloudCount;
	private int middleCloudCount;
	private int speedTime;
	private float screen_width;		 // 屏幕的宽度
	private float screen_height;	 // 屏幕的高度
	private boolean threadFlag;
	private boolean isPlay;
	private boolean isTouch;
	private List<GameObject> planes;
	private List<GameObject> clouds;
	private MainActivity mainActivity;
	private Handler myHandler;

	public MainView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mainActivity = (MainActivity)context;
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		planes = new ArrayList<GameObject>();
		myPlane = new MyPlane(this,getResources());
		clouds = new ArrayList<GameObject>();
		for(int i = 0;i < 8;i++){
			EnemyPlane enemyPlane = new EnemyPlane(getResources());
			planes.add(enemyPlane);
		}
		for(int i = 0;i < 2; i++) {
			SmallCloud smallCloud = new SmallCloud(getResources());
			clouds.add(smallCloud);
		}
		for(int i = 0;i < 1; i++) {
			MiddleCloud middleCloud = new MiddleCloud(getResources());
			clouds.add(middleCloud);
		}
		enemyCount = 0;
		smallCloudCount = 0;
		middleCloudCount = 0;
		speedTime = 1;
		thread = new Thread(this);
		isPlay = true;
		myHandler = new Handler(){ 
			@Override
	        public void handleMessage(Message msg){
	            if(msg.what == 1){
//	            	mainActivity.toEndView(scoreSum);
	            }
	        }
	    };
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		screen_width = this.getWidth();
		screen_height = this.getHeight();
		for(GameObject obj:planes){
			obj.setScreenWH(screen_width, screen_height);
		}
		for(GameObject obj:clouds) {
			obj.setScreenWH(screen_width, screen_height);
		}
		myPlane.setScreenWH(screen_width, screen_height);
		myPlane.setAlive(true);
		threadFlag = true;
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		threadFlag = false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP){
			isTouch = false;
		}
		else if(event.getAction() == MotionEvent.ACTION_DOWN){
			float x = event.getX();
			float y = event.getY();
//			if(x > 10 && x < 10 + play_bt_w && y > 10 && y < 10 + play_bt_h){
//				if(isPlay){
//					isPlay = false;
//				}		
//				else{
//					isPlay = true;	
//					synchronized(thread){
//						thread.notify();
//					}
//				}
//				return true;
//			}
			//判断玩家飞机是否被按下
//			else 
				if(x > myPlane.getObject_x() && x < myPlane.getObject_x() + myPlane.getObject_width() 
					&& y > myPlane.getObject_y() && y < myPlane.getObject_y() + myPlane.getObject_height()){
				if(isPlay){
					isTouch = true;
				}
				return true;
			}
		}
		//玩家飞机是否移动
		else if(event.getAction() == MotionEvent.ACTION_MOVE && event.getPointerCount() == 1){
			if(isTouch){
				float x = event.getX();
				float y = event.getY();
				if(x > myPlane.getMiddle_x() + 20){
					if(myPlane.getMiddle_x() + myPlane.getSpeed() <= screen_width){
						myPlane.setMiddle_x(myPlane.getMiddle_x() + myPlane.getSpeed());
					}					
				}
				else if(x < myPlane.getMiddle_x() - 20){
					if(myPlane.getMiddle_x() - myPlane.getSpeed() >= 0){
						myPlane.setMiddle_x(myPlane.getMiddle_x() - myPlane.getSpeed());
					}				
				}
				if(y > myPlane.getMiddle_y() + 20){
					if(myPlane.getMiddle_y() + myPlane.getSpeed() <= screen_height){
						myPlane.setMiddle_y(myPlane.getMiddle_y() + myPlane.getSpeed());
					}		
				}
				else if(y < myPlane.getMiddle_y() - 20){
					if(myPlane.getMiddle_y() - myPlane.getSpeed() >= 0){
						myPlane.setMiddle_y(myPlane.getMiddle_y() - myPlane.getSpeed());
					}
				}
			}	
		}
		return false;
	}

	//初始化对象
	public void initObject(){
		//初始化敌机对象
		for(GameObject obj:planes){		
			//初始化敌机
			if(obj instanceof EnemyPlane){
				if(!obj.isAlive()){
					obj.initial(enemyCount,0,0,speedTime);
					enemyCount++;
					if(enemyCount >= 8){
						enemyCount = 0;
					}
					break;
				}
			}
		}
		
		for(GameObject obj: clouds) {
			if(obj instanceof SmallCloud) {
				if(!obj.isAlive()){
					obj.initial(smallCloudCount,0,0,speedTime);
					smallCloudCount++;
					if(smallCloudCount >= 2){
						smallCloudCount = 0;
					}
					break;
				}				
			} else if(obj instanceof MiddleCloud) {
				if(!obj.isAlive()){
					obj.initial(middleCloudCount,0,0,speedTime);
					middleCloudCount++;
					if(middleCloudCount >= 1){
						middleCloudCount = 0;
					}
					break;
				}				
			}
		}
		myPlane.initBullets();						
	}
	// 绘图函数
	public void drawSelf() {
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.GRAY); // 绘制背景色
			canvas.save();

			//绘制敌机
			for(GameObject obj:planes){		
				if(obj.isAlive()){
					obj.drawSelf(canvas);					
					if(!obj.isExplosion() && myPlane.isAlive()){		
						if(obj.isCollide(myPlane)){			//检测敌机是否与玩家的飞机碰撞					
							myPlane.setAlive(false);	
						}
					}
				}	
			}
			
			for(GameObject obj: clouds) {
				if(obj.isAlive()) {
					obj.drawSelf(canvas);
				}
			}
			if(!myPlane.isAlive()){
				threadFlag = false;
			}
			//绘制玩家的飞机
			myPlane.drawSelf(canvas);	
			myPlane.shoot(canvas, planes);//发射子弹	
			//绘制按钮
			canvas.save();
			canvas.restore();
			//绘制积分文字
			paint.setTextSize(30);
			paint.setColor(Color.rgb(235, 161, 1));
			canvas.drawText("积分:"+String.valueOf(scoreSum), screen_width - 150,
					40, paint);//绘制文字
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}
	public void release(){
		for(GameObject obj:planes){		
			obj.release();
		}
		for(GameObject obj:clouds) {
			obj.release();
		}
		myPlane.release();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (threadFlag) {	
			long startTime = System.currentTimeMillis();
			initObject();
			drawSelf();
			long endTime = System.currentTimeMillis();	
			if(!isPlay){
				synchronized (thread) {  
				    try {  
				    	thread.wait();  
				    } catch (InterruptedException e) {  
				        e.printStackTrace();  
				    }  
				}  		
			}	
			try {
				if (endTime - startTime < 100)
					Thread.sleep(100 - (endTime - startTime));
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myHandler.sendEmptyMessage(1);
	}
	
	public void addScoreSum(int score) {
		scoreSum += score;
	}

}
