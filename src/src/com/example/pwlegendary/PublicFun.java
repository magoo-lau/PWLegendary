/*
 * 公共类
 * */
package com.example.pwlegendary;

import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import android.content.Context;

public class PublicFun {

	public PublicFun(){}
	
	//移动速度(传说中的变速齿轮)
	private static float moveSpeed = 200;
	//两点间运行时间
	public static float moveTime;
	//两点间距离
	public static float pointDis;
	
	//播放点击按钮音乐
	public static void btSound(Context app)
	{
		SoundEngine.sharedEngine().playEffect(app, R.raw.btsound);
	}
	
	/*
	 * 获取两点间距离做匀速运动
	 * curPoint：当前坐标
	 * tarPoint：目标坐标
	 * 返回运动时间
	 * 数学公式：平方根
	 * */
	public static float moveTime(CGPoint curPoint,CGPoint tarPoint)
	{
		//水平
		float h = tarPoint.x-curPoint.x;
		//垂直
		float v = tarPoint.y-curPoint.y;
		if(h == 0) {
		    return v>0?v:-v;
		}
		if(v == 0) {
		    return h>0?h:-h;
		}
		moveTime = (float) Math.sqrt(h*h + v*v) / moveSpeed;
		pointDis = (float) Math.sqrt(h*h + v*v);
		return moveTime;
	}
	
	/*
	 * 获取移动方向
	 * curPoint：当前坐标
	 * tarPoint：目标坐标
	 * 参数：
	 * 		4：北
	 * 		3：东北
	 * 		2：东
	 * 		1：东南
	 * 		0：南
	 * 		7：西南
	 * 		6：西
	 * 		5：西北
	 * 数学公式：正切函数
	 * */
	public static int moveDirection(CGPoint curPoint,CGPoint tarPoint)
	{
		double targetY,currentY,targetX,currentX;
		targetY = tarPoint.y;
		targetX = tarPoint.x;
		currentY = curPoint.y;
		currentX = curPoint.x;
		double t = (currentY - targetY) / (currentX - targetX);
		
		if (Math.abs(t) >= Math.tan(Math.PI * 3 / 8) && targetY <= currentY) {
            return 0;
        } else if (Math.abs(t) > Math.tan(Math.PI / 8) && Math.abs(t) < Math.tan(Math.PI * 3 / 8) && targetX > currentX && targetY < currentY) {
            return 1;
        } else if (Math.abs(t) <= Math.tan(Math.PI / 8) && targetX >= currentX) {
            return 2;
        } else if (Math.abs(t) > Math.tan(Math.PI / 8) && Math.abs(t) < Math.tan(Math.PI * 3 / 8) && targetX > currentX && targetY > currentY) {
            return 3;
        } else if (Math.abs(t) >= Math.tan(Math.PI * 3 / 8) && targetY >= currentY) {
            return 4;
        } else if (Math.abs(t) > Math.tan(Math.PI / 8) && Math.abs(t) < Math.tan(Math.PI * 3 / 8) && targetX < currentX && targetY > currentY) {
            return 5;
        } else if (Math.abs(t) <= Math.tan(Math.PI / 8) && targetX <= currentX) {
            return 6;
        } else if (Math.abs(t) > Math.tan(Math.PI / 8) && Math.abs(t) < Math.tan(Math.PI * 3 / 8) && targetX < currentX && targetY < currentY) {
            return 7;
        } else {
            return 0;
        }
	}
}
