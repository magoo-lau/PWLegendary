package com.example.pwlegendary;

import java.util.Random;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.content.Context;
import android.view.MotionEvent;

public class Result extends CCLayer{
	
	Context app;
	CCSprite resultBg;
	CCLabel resultText;
	CCSprite rImg;
	CCLabel rText;
	CCSprite fanhuibt;
	CCSprite huifangbt;
	
	public Result(Context context)
	{
		app = context;
		//��ͼ�㿪�������¼�
		this.setIsTouchEnabled(true);
		//����ͼ
		resultBg = CCSprite.sprite("result/resultbg.png");
		CGSize size = CCDirector.sharedDirector().winSize();
		CGPoint mapPoint = CGPoint.ccp(size.width/2, size.height/2);
		resultBg.setPosition(mapPoint);
		
		ccColor3B color = new ccColor3B(255, 255, 255);
		//����
		String rt = "����";
		String rtj ="Ŭ��������";
		String rtImg = "result/shibai.png";
		if(Lev01.result == 1)
		{
			rt = "ʤ��";
			int s = MathNum();
			
			switch(s)
			{
				case 0:
					rtj = "��ң�"+ MathNum2();
					rtImg = "result/jinbi.png";
					break;
				case 1:
					rtj = " һ����ʯ";
					rtImg = "result/qianghua01.png";
					break;
				case 2:
					rtj = " ������ʯ";
					rtImg = "result/qianghua02.png";
					break;
				case 3:
					rtj = " ������ʯ";
					rtImg = "result/qianghua03.png";
					break;
				case 4:
					rtj = " �ļ���ʯ";
					rtImg = "result/qianghua04.png";
					break;
				case 5:
					rtj = " �弶��ʯ";
					rtImg = "result/qianghua05.png";
					break;
				default:
					rtj = "Ŭ��������";
					rtImg = "";
					break;
			}
		}else
		{
			rt = "ʧ��";
		}
		
		//����ͼ
		rImg = CCSprite.sprite(rtImg);
		CGPoint imgPoint = CGPoint.ccp(size.width/2, size.height/2+50);
		rImg.setPosition(imgPoint);
		
		//����˵��
		rText = CCLabel.makeLabel(rtj,"rText", 40);
		rText.setPosition(size.width/2-100,size.height/2-150);
		rText.setColor(color);
		rText.setAnchorPoint(0, 0);
		
		resultText = CCLabel.makeLabel(rt, "roleLev", 45);
		resultText.setPosition(size.width/2-50,size.height/2+275);
		resultText.setColor(color);
		resultText.setAnchorPoint(0, 0);

		//���ذ�ť
		fanhuibt = CCSprite.sprite("result/fanhui.png");
		CGPoint fanhuiPoint = CGPoint.ccp(size.width/2+100, size.height/2-240);
		fanhuibt.setPosition(fanhuiPoint);
		
		//�طŰ�ť
		huifangbt = CCSprite.sprite("result/huifang.png");
		CGPoint huifangPoint = CGPoint.ccp(size.width/2-100, size.height/2-240);
		huifangbt.setPosition(huifangPoint);
		
		this.addChild(resultBg,1);
		this.addChild(resultText,2);
		this.addChild(rImg,2);
		this.addChild(rText,2);
		this.addChild(fanhuibt,2);
		this.addChild(huifangbt,2);
	}
	
	//����
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		//��ȡ��������
		CGPoint touch = CGPoint.ccp(event.getX(), event.getY());
		//ת������ϵ
		touch = CCDirector.sharedDirector().convertToGL(touch);		
		//��ȡ���ذ�ť
		CGRect fanhui = fanhuibt.getBoundingBox();
		CGRect huifang = huifangbt.getBoundingBox();
		if(CGRect.containsPoint(fanhui, touch))
		{
			PublicFun.btSound(app);
			//��ת�����ǽ���
			CCScene scene = CCScene.node();
			//�������ؽ�ɫѡ��ͼ��
			scene.addChild(new StartGame(app));
			//���ݸ�������
			CCDirector.sharedDirector().replaceScene(scene);
		}
		if(CGRect.containsPoint(huifang, touch))
		{
			PublicFun.btSound(app);
			//��ת��ս������
			CCScene scene = CCScene.node();
			//�������ؽ�ɫѡ��ͼ��
			scene.addChild(new Lev01(app));
			//���ݸ�������
			CCDirector.sharedDirector().replaceScene(scene);
		}
		return CCTouchDispatcher.kEventHandled;
	}	
	
	//�����-��Ʒ
	public int MathNum()
	{
		return new Random().nextInt(4);
	}	
	
	//�����-���
	public int MathNum2()
	{
		return new Random().nextInt(9999);
	}	
	
	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		super.onEnter();
		SoundEngine.sharedEngine().playSound(app, R.raw.jieguomp3, true);
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
//		if(db != null)
//		{
//			db.close();
//		}
		SoundEngine.sharedEngine().pauseSound();
		super.onExit();
	}
}
