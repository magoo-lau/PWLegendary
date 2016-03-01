/*
 * ������
 * */
package com.example.pwlegendary;

import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import android.content.Context;

public class PublicFun {

	public PublicFun(){}
	
	//�ƶ��ٶ�(��˵�еı��ٳ���)
	private static float moveSpeed = 200;
	//���������ʱ��
	public static float moveTime;
	//��������
	public static float pointDis;
	
	//���ŵ����ť����
	public static void btSound(Context app)
	{
		SoundEngine.sharedEngine().playEffect(app, R.raw.btsound);
	}
	
	/*
	 * ��ȡ���������������˶�
	 * curPoint����ǰ����
	 * tarPoint��Ŀ������
	 * �����˶�ʱ��
	 * ��ѧ��ʽ��ƽ����
	 * */
	public static float moveTime(CGPoint curPoint,CGPoint tarPoint)
	{
		//ˮƽ
		float h = tarPoint.x-curPoint.x;
		//��ֱ
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
	 * ��ȡ�ƶ�����
	 * curPoint����ǰ����
	 * tarPoint��Ŀ������
	 * ������
	 * 		4����
	 * 		3������
	 * 		2����
	 * 		1������
	 * 		0����
	 * 		7������
	 * 		6����
	 * 		5������
	 * ��ѧ��ʽ�����к���
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
