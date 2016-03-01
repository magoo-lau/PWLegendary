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
		//此图层开启触屏事件
		this.setIsTouchEnabled(true);
		//背景图
		resultBg = CCSprite.sprite("result/resultbg.png");
		CGSize size = CCDirector.sharedDirector().winSize();
		CGPoint mapPoint = CGPoint.ccp(size.width/2, size.height/2);
		resultBg.setPosition(mapPoint);
		
		ccColor3B color = new ccColor3B(255, 255, 255);
		//文字
		String rt = "出错";
		String rtj ="努力，加油";
		String rtImg = "result/shibai.png";
		if(Lev01.result == 1)
		{
			rt = "胜利";
			int s = MathNum();
			
			switch(s)
			{
				case 0:
					rtj = "金币："+ MathNum2();
					rtImg = "result/jinbi.png";
					break;
				case 1:
					rtj = " 一级宝石";
					rtImg = "result/qianghua01.png";
					break;
				case 2:
					rtj = " 二级宝石";
					rtImg = "result/qianghua02.png";
					break;
				case 3:
					rtj = " 三级宝石";
					rtImg = "result/qianghua03.png";
					break;
				case 4:
					rtj = " 四级宝石";
					rtImg = "result/qianghua04.png";
					break;
				case 5:
					rtj = " 五级宝石";
					rtImg = "result/qianghua05.png";
					break;
				default:
					rtj = "努力，加油";
					rtImg = "";
					break;
			}
		}else
		{
			rt = "失败";
		}
		
		//奖励图
		rImg = CCSprite.sprite(rtImg);
		CGPoint imgPoint = CGPoint.ccp(size.width/2, size.height/2+50);
		rImg.setPosition(imgPoint);
		
		//奖励说明
		rText = CCLabel.makeLabel(rtj,"rText", 40);
		rText.setPosition(size.width/2-100,size.height/2-150);
		rText.setColor(color);
		rText.setAnchorPoint(0, 0);
		
		resultText = CCLabel.makeLabel(rt, "roleLev", 45);
		resultText.setPosition(size.width/2-50,size.height/2+275);
		resultText.setColor(color);
		resultText.setAnchorPoint(0, 0);

		//返回按钮
		fanhuibt = CCSprite.sprite("result/fanhui.png");
		CGPoint fanhuiPoint = CGPoint.ccp(size.width/2+100, size.height/2-240);
		fanhuibt.setPosition(fanhuiPoint);
		
		//回放按钮
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
	
	//触屏
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		//获取点击坐标点
		CGPoint touch = CGPoint.ccp(event.getX(), event.getY());
		//转换坐标系
		touch = CCDirector.sharedDirector().convertToGL(touch);		
		//获取返回按钮
		CGRect fanhui = fanhuibt.getBoundingBox();
		CGRect huifang = huifangbt.getBoundingBox();
		if(CGRect.containsPoint(fanhui, touch))
		{
			PublicFun.btSound(app);
			//跳转至主城界面
			CCScene scene = CCScene.node();
			//场景加载角色选择图层
			scene.addChild(new StartGame(app));
			//导演更换场景
			CCDirector.sharedDirector().replaceScene(scene);
		}
		if(CGRect.containsPoint(huifang, touch))
		{
			PublicFun.btSound(app);
			//跳转至战斗界面
			CCScene scene = CCScene.node();
			//场景加载角色选择图层
			scene.addChild(new Lev01(app));
			//导演更换场景
			CCDirector.sharedDirector().replaceScene(scene);
		}
		return CCTouchDispatcher.kEventHandled;
	}	
	
	//随机数-奖品
	public int MathNum()
	{
		return new Random().nextInt(4);
	}	
	
	//随机数-金币
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
