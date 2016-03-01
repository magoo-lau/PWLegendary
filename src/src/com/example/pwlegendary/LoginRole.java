/*
 * 功能：登录创建角色类
 * */
package com.example.pwlegendary;

import org.cocos2d.actions.interval.CCBlink;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor4B;
import android.content.Context;
import android.database.Cursor;
import android.view.MotionEvent;

public class LoginRole extends CCColorLayer{
	
	Context app;
	//登录背景
	CCSprite loginBg;
	CCSprite zNan;
	CCSprite zNv;
	CCSprite zBt;
	CCSprite fBt;
	CCSprite dBt;
	CCSprite shaizi;
	CCLabel roleName;
	CCSprite startGm;
	CCSprite returnBt;
	private static String strName = null;
	//男角色姓名库
	String NanName[] = {"蒙睿明","卓宪磊","魏儒致","宫晓东","丰睿儒","东方志","舒辉肃"};
	//女角色姓名库
	String NvName[] = {"令狐语语","姬彤雅","南宫念彤","蒙念薇","钱思烟","令狐依蕊","甄薇妙"};
	//角色选择标示
	private static int Tag = 1;
	//性别
	private static int Sex = 1;
	
	dbHelper db = null;
	
	public LoginRole(Context context)
	{
		//黑色背景
		super(new ccColor4B(0, 0, 0, 0));
		
		//此图层开启触屏事件
		this.setIsTouchEnabled(true);
		
		app = context;
		//实例登录精灵对象
		loginBg = CCSprite.sprite("role/roleselect.png");
		//坐标位置
		CGSize size = CCDirector.sharedDirector().winSize();
		CGPoint spritePoint = CGPoint.ccp(size.width/2, size.height/2);
		//定义位置
		loginBg.setPosition(spritePoint);
		
		//战士男初始化图片
		zNan = CCSprite.sprite("role/znan.png");
		CGPoint bg =loginBg.getPosition();
		CGPoint zNanPoint = CGPoint.ccp(bg.x-239,bg.y+100);
		zNan.setPosition(zNanPoint);
		
		//战士女
		zNv = CCSprite.sprite("role/znva.png");
		CGPoint zNvPoint = CGPoint.ccp(bg.x+239,bg.y+100);
		zNv.setPosition(zNvPoint);
		
		//战士按钮
		zBt = CCSprite.sprite("role/zbt.png");
		CGPoint zBtPoint = CGPoint.ccp(bg.x,bg.y+105);
		zBt.setPosition(zBtPoint);
		
		//法师按钮
		fBt = CCSprite.sprite("role/fbt.png");
		CGPoint fBtPoint = CGPoint.ccp(bg.x,bg.y+25);
		fBt.setPosition(fBtPoint);
		fBt.setVisible(false);
		
		//道士按钮
		dBt = CCSprite.sprite("role/dbt.png");
		CGPoint dBtPoint = CGPoint.ccp(bg.x,bg.y-55);
		dBt.setPosition(dBtPoint);
		dBt.setVisible(false);
		
		//骰子
		shaizi = CCSprite.sprite("role/shaizi.png");
		CGPoint shaiziPoint = CGPoint.ccp(bg.x+200, bg.y-180);
		shaizi.setPosition(shaiziPoint);
		
		//名字
		roleName = CCLabel.makeLabel(RandomName(1), "Name", 32);
		CGPoint roleNamePoint = CGPoint.ccp(bg.x-140, bg.y-195);
		roleName.setPosition(roleNamePoint);
		roleName.setAnchorPoint(0, 0);//左对齐
		
		//开始游戏
		startGm = CCSprite.sprite("role/startbt.png");
		CGPoint startPoint = CGPoint.ccp(bg.x, bg.y-260);
		startGm.setPosition(startPoint);
		startGm.setVisible(true);
		
		//返回按钮
		returnBt = CCSprite.sprite("role/returnbt.png");
		CGPoint sPoint = CGPoint.ccp(bg.x+300, bg.y-180);
		returnBt.setPosition(sPoint);
		returnBt.setVisible(false);
		
		//将精灵添加至图层
		this.addChild(loginBg);
		this.addChild(zNan);
		this.addChild(zNv);
		this.addChild(zBt);
		this.addChild(fBt);
		this.addChild(dBt);
		this.addChild(shaizi);
		this.addChild(roleName);
		this.addChild(startGm);
		this.addChild(returnBt);
	}
	
	//触屏
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		//获取点击坐标点
		CGPoint touch = CGPoint.ccp(event.getX(), event.getY());
		//转换坐标系
		touch = CCDirector.sharedDirector().convertToGL(touch);
		
		//获取 战士系 按钮
		CGRect rectZbt = zBt.getBoundingBox();
		//获取 法师系 按钮
		CGRect rectFbt = fBt.getBoundingBox();
		//获取 道士系 按钮
		CGRect rectDbt = dBt.getBoundingBox();
		//获取战士男图片坐标位置
		CGRect rectNan = zNan.getBoundingBox();
		//获取战士女图片坐标位置
		CGRect rectNv = zNv.getBoundingBox();
		//获取骰子坐标
		CGRect rectShaizi = shaizi.getBoundingBox();
		//获取开始游戏按钮
		CGRect RectStart = startGm.getBoundingBox();
		//获取返回按钮
		CGRect RectReturn = returnBt.getBoundingBox();
		//战士按钮
		if(CGRect.containsPoint(rectZbt, touch))
		{
			PublicFun.btSound(app);
			Tag = 1;
			zBt.setVisible(true);
			fBt.setVisible(false);
			dBt.setVisible(false);
			CCTexture2D textNan = CCTextureCache.sharedTextureCache().addImage("role/znan.png");
			zNan.setTexture(textNan);
			CCTexture2D textNv = CCTextureCache.sharedTextureCache().addImage("role/znva.png");
			zNv.setTexture(textNv);
			roleName.setString(RandomName(1));
		}
		//法师按钮
		if(CGRect.containsPoint(rectFbt, touch))
		{
			PublicFun.btSound(app);
			Tag = 2;
			fBt.setVisible(true);
			zBt.setVisible(false);
			dBt.setVisible(false);
			
			CCTexture2D textNan = CCTextureCache.sharedTextureCache().addImage("role/fnan.png");
			zNan.setTexture(textNan);
			CCTexture2D textNv = CCTextureCache.sharedTextureCache().addImage("role/fnva.png");
			zNv.setTexture(textNv);
			roleName.setString(RandomName(1));
		}
		//道士按钮
		if(CGRect.containsPoint(rectDbt, touch))
		{
			PublicFun.btSound(app);
			Tag = 3;
			dBt.setVisible(true);
			zBt.setVisible(false);
			fBt.setVisible(false);
			
			CCTexture2D textNan = CCTextureCache.sharedTextureCache().addImage("role/dnan.png");
			zNan.setTexture(textNan);
			CCTexture2D textNv = CCTextureCache.sharedTextureCache().addImage("role/dnva.png");
			zNv.setTexture(textNv);
			roleName.setString(RandomName(1));
		}
		
		switch(Tag)
		{
			//战士系列
			case 1:
				//判断是否点击(战士男)
				if(CGRect.containsPoint(rectNan, touch))
				{
					CCTexture2D textNan = CCTextureCache.sharedTextureCache().addImage("role/znan.png");
					zNan.setTexture(textNan);
					CCTexture2D textNv = CCTextureCache.sharedTextureCache().addImage("role/znva.png");
					zNv.setTexture(textNv);
					Sex = 1;
					roleName.setString(RandomName(Sex));
				}
				//战士女
				if(CGRect.containsPoint(rectNv, touch))
				{
					CCTexture2D textNan = CCTextureCache.sharedTextureCache().addImage("role/znana.png");
					zNan.setTexture(textNan);
					CCTexture2D textNv = CCTextureCache.sharedTextureCache().addImage("role/znv.png");
					zNv.setTexture(textNv);
					Sex = 0;
					roleName.setString(RandomName(Sex));
				}
				break;
			//法师系列
			case 2:
				//判断是否点击(法师男)
				if(CGRect.containsPoint(rectNan, touch))
				{
					CCTexture2D textNan = CCTextureCache.sharedTextureCache().addImage("role/fnan.png");
					zNan.setTexture(textNan);
					CCTexture2D textNv = CCTextureCache.sharedTextureCache().addImage("role/fnva.png");
					zNv.setTexture(textNv);
					Sex = 1;
					roleName.setString(RandomName(Sex));
				}
				//法师女
				if(CGRect.containsPoint(rectNv, touch))
				{
					CCTexture2D textNan = CCTextureCache.sharedTextureCache().addImage("role/fnana.png");
					zNan.setTexture(textNan);
					CCTexture2D textNv = CCTextureCache.sharedTextureCache().addImage("role/fnv.png");
					zNv.setTexture(textNv);
					Sex = 0;
					roleName.setString(RandomName(Sex));
				}
				break;
			//道士系列
			case 3:
				//判断是否点击(道士男)
				if(CGRect.containsPoint(rectNan, touch))
				{
					CCTexture2D textNan = CCTextureCache.sharedTextureCache().addImage("role/dnan.png");
					zNan.setTexture(textNan);
					CCTexture2D textNv = CCTextureCache.sharedTextureCache().addImage("role/dnva.png");
					zNv.setTexture(textNv);
					Sex = 1;
					roleName.setString(RandomName(Sex));
				}
				//道士女
				if(CGRect.containsPoint(rectNv, touch))
				{
					CCTexture2D textNan = CCTextureCache.sharedTextureCache().addImage("role/dnana.png");
					zNan.setTexture(textNan);
					CCTexture2D textNv = CCTextureCache.sharedTextureCache().addImage("role/dnv.png");
					zNv.setTexture(textNv);
					Sex = 0;
					roleName.setString(RandomName(Sex));
				}
				break;
			default:
				System.out.println("Error!!!!!!!!");
				break;
		}
		
		//判断骰子
		if(CGRect.containsPoint(rectShaizi, touch))
		{
			PublicFun.btSound(app);
			//骰子闪烁
			CCBlink blink = CCBlink.action(1, 2);
			shaizi.runAction(blink);
			roleName.setString(RandomName(Sex));
		}
		
		//判断开始按钮
		if(CGRect.containsPoint(RectStart, touch))
		{
			PublicFun.btSound(app);
			startGm.setVisible(false);
			//进入游戏
			//单机版单用户查询是否选过角色，如果选择则不可以创建
			db = new dbHelper(app);
			Cursor cursor = db.selectId("1");
			try{
				//判断是否有数据
				if(cursor.getCount() == 0)
				{
					//插入初始化数据
					db.insert(strName, Sex, Tag, 0, 1);
					//进入游戏
					//实例CCScene场景节点
					CCScene scene = CCScene.node();
					//场景加载角色选择图层
					scene.addChild(new StartGame(app));
					//导演更换场景
					CCDirector.sharedDirector().replaceScene(scene);
				}else
				{
					//进入角色选择界面
					//实例CCScene场景节点
					CCScene scene = CCScene.node();
					//场景加载角色选择图层
					scene.addChild(new RoleSelect(app));
					//导演更换场景
					CCDirector.sharedDirector().replaceScene(scene);
				}
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
			}finally
			{
				cursor.close();
				//关闭数据库
				db.close();
			}
		}
		
		//判断是否点击返回按钮
		if(CGRect.containsPoint(RectReturn, touch))
		{
			PublicFun.btSound(app);
			db = new dbHelper(app);
			Cursor cursor = db.selectId("1");
			try
			{
				//判断是否有数据
				if(cursor.getCount() == 0)
				{
					
				}else
				{
					//实例CCScene场景节点
					CCScene scene = CCScene.node();
					//场景加载角色选择图层
					scene.addChild(new RoleSelect(app));
					//导演更换场景
					CCDirector.sharedDirector().replaceScene(scene);
				}
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
			}finally
			{
				cursor.close();
				//关闭数据库
				db.close();
			}
		}
		
		return CCTouchDispatcher.kEventHandled;
	}
	
	//随机抽取姓名
	public String RandomName(int sex)
	{
		//男
		if(sex == 1)
		{
			int index=(int)(Math.random()*NanName.length);
			strName = NanName[index];
		}else
		{
			int index=(int)(Math.random()*NvName.length);
			strName = NvName[index];
		}
		return strName;
	}
	
	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		super.onEnter();
		SoundEngine.sharedEngine().playSound(app, R.raw.loginmp3, true);
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		if(db != null)
		{
			db.close();
		}
		SoundEngine.sharedEngine().pauseSound();
		super.onExit();
	}
}
