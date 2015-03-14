package com.example.chris;



import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
/*玩家飞机的类*/
public class MyPlane extends GameObject {
	private Bitmap myplane;
	private float middle_x;
	private float  middle_y;
	private List<GameObject> bullets;	//子弹类
	private MainView mainView;
	MyPlane(MainView mainView,Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		initBitmap();
		this.speed = 8;
		this.mainView = mainView;
		bullets = new ArrayList<GameObject>();
		for(int i = 0;i < 4;i++){
			Bullet bullet = new Bullet(resources);
			bullets.add(bullet);
		}
	}

	// 初始化数据
	@Override
	public void setScreenWH(float screen_width, float screen_height) {
		super.setScreenWH(screen_width, screen_height);
		object_x = screen_width/2 - object_width/2;
		object_y = screen_height - object_height;
		middle_x = object_x + object_width/5;
		middle_y = object_y + object_height/2;
	}
	
	// 初始化图片
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		myplane = BitmapFactory.decodeResource(resources, R.drawable.plane);
		object_width = myplane.getWidth(); // 获得每一帧位图的宽
		object_height = myplane.getHeight(); // 获得每一帧位图的高
	}

	// 绘图函数
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){
			int x = (int) //(currentFrame * 
					object_width;//); // 获得当前帧相对于位图的Y坐标
			canvas.save();
//			canvas.clipRect(object_x - object_width, object_y, object_x + object_width, object_y + object_height);
			canvas.drawBitmap(myplane, object_x  , object_y, paint);
			canvas.restore();
			currentFrame++;
			if (currentFrame >= 2) {
				currentFrame = 0;
			}
		}
	}

	//初始化子弹对象
	public void initBullets(){
		for(GameObject obj:bullets){	
			if(!obj.isAlive()){
				obj.initial(0,middle_x, middle_y,0);
				break;
			}
		}
	}
	//发射子弹
	public void shoot(Canvas canvas,List<GameObject> planes){
		//遍历子弹的对象
		for(GameObject obj:bullets){
			if(obj.isAlive()){
				for(GameObject pobj:planes){
					if(pobj.isAlive() && !pobj.isExplosion()){
						if(obj.isCollide(pobj)){			//检查碰撞
							pobj.attacked(obj.harm);
							if(pobj.isExplosion()){	
								//增加积分
								mainView.addScoreSum(pobj.getScore());		
							}
							break;
						}
					}
				}
				obj.drawSelf(canvas);//绘制子弹
			}
		}
	}
	// 释放资源
	@Override
	public void release() {
		// TODO Auto-generated method stub
		for(GameObject obj:bullets){	
			obj.release();
		}
		if(!myplane.isRecycled()){
			myplane.recycle();
		}
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public float getMiddle_x() {
		return middle_x;
	}

	public void setMiddle_x(float middle_x) {
		this.middle_x = middle_x;
		this.object_x = middle_x - object_width/2;
	}

	public float getMiddle_y() {
		return middle_y;
	}

	public void setMiddle_y(float middle_y) {
		this.middle_y = middle_y;
		this.object_y = middle_y - object_height/2;
	}
	
	public void setAlive(boolean isAlive){
		this.isAlive = isAlive;
	}
}
