/*
 * 角色选择类
 * */
package com.example.pwlegendary;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor4B;

import android.content.Context;
import android.database.Cursor;
import android.view.MotionEvent;

public class RoleSelect extends CCColorLayer{

	Context app;
	//按钮角色
	CCSprite roleSelectBj;
	CCSprite createBt;
	CCSprite startBt;
	CCSprite deleteBt;
	//角色头像
	CCSprite roleImg;
	//角色名称标签
	CCLabel roleName;
	//级别标签
	CCLabel roleLev;
	//类型标签
	CCLabel roleType;
	//存储用户名、ID、性别、角色类型
	static String rID,rName,rSex,rType,rLev;
	//删除弹出框
	CCSprite deleteMes;
	CCSprite mesClose;
	CCSprite mesConfirm;
	CCSprite mesConcel;
	dbHelper db = null;
	
	public RoleSelect(Context context)
	{
		//黑色背景
		super(new ccColor4B(0, 0, 0, 0));
		app = context;
		//此图层开启触屏事件
		this.setIsTouchEnabled(true);
		
		//实例选择角色背景图精灵对象
		roleSelectBj = CCSprite.sprite("role/selectrole.png");
		CGSize size = CCDirector.sharedDirector().winSize();
		CGPoint spritePoint = CGPoint.ccp(size.width/2, size.height/2);
		roleSelectBj.setPosition(spritePoint);
		
		db = new dbHelper(app);
		Cursor cursor = db.selectId("1");
		try
		{
			//判断是否有数据
			if(cursor.getCount() == 0)
			{
				System.out.println("出错了");
			}else
			{
				
				while(cursor.moveToNext())
				{
					rID = cursor.getString(0).toString();
					rName = cursor.getString(1).toString();
					rSex = cursor.getString(2).toString();
					rType = cursor.getString(3).toString();
					rLev = cursor.getString(5).toString();
				}
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}finally
		{
			cursor.close();
			db.close();
		}
		//游戏名称
		roleName = CCLabel.makeLabel(rName, "gameName", 25);
		CGPoint bg =roleSelectBj.getPosition();
		roleName.setPosition(bg.x+10,bg.y+185);
		roleName.setAnchorPoint(0, 0);//左对齐
		//等级
		roleLev = CCLabel.makeLabel(rLev, "gameLev", 25);
		roleLev.setPosition(bg.x+10,bg.y+145);
		roleLev.setAnchorPoint(0, 0);//左对齐
		
		//判断角色职业与性别显示对应的图片与文字
		switch(Integer.parseInt(rType))
		{
			case 1:
				rType = "战士";
				if(rSex.equals("1"))
				{
					//头像
					roleImg = CCSprite.sprite("role/sznan.png");
					roleImg.setPosition(bg.x-225,bg.y+110);
					roleImg.setAnchorPoint(0, 0);//左对齐
				}else
				{
					roleImg = CCSprite.sprite("role/sznv.png");
					roleImg.setPosition(bg.x-225,bg.y+110);
					roleImg.setAnchorPoint(0, 0);//左对齐
				}
				break;
			case 2:
				rType = "法师";
				if(rSex.equals("1"))
				{
					//头像
					roleImg = CCSprite.sprite("role/sfnan.png");
					roleImg.setPosition(bg.x-225,bg.y+110);
					roleImg.setAnchorPoint(0, 0);//左对齐
				}else
				{
					roleImg = CCSprite.sprite("role/sfnv.png");
					roleImg.setPosition(bg.x-225,bg.y+110);
					roleImg.setAnchorPoint(0, 0);//左对齐
				}
				break;
			case 3:
				rType = "道士";
				if(rSex.equals("1"))
				{
					//头像
					roleImg = CCSprite.sprite("role/sdnan.png");
					roleImg.setPosition(bg.x-225,bg.y+110);
					roleImg.setAnchorPoint(0, 0);//左对齐
				}else
				{
					roleImg = CCSprite.sprite("role/sdnv.png");
					roleImg.setPosition(bg.x-225,bg.y+110);
					roleImg.setAnchorPoint(0, 0);//左对齐
				}
				break;
			default:
				System.out.println("未知");
				break;
		}
		//职业
		roleType = CCLabel.makeLabel(rType, "gameType", 25);
		roleType.setPosition(bg.x+10,bg.y+105);
		roleType.setAnchorPoint(0, 0);//左对齐
		
		//实例创建角色按钮
		createBt = CCSprite.sprite("role/createrole.png");
		//坐标位置
		CGPoint cPoint = CGPoint.ccp(size.width/2-180, size.height/2-270);
		//定义位置
		createBt.setPosition(cPoint);
		
		//实例开始游戏按钮
		startBt = CCSprite.sprite("role/startgame.png");
		//坐标位置
		CGPoint sPoint = CGPoint.ccp(size.width/2, size.height/2-280);
		//定义位置
		startBt.setPosition(sPoint);
		
		//实例开始游戏按钮
		deleteBt = CCSprite.sprite("role/deleterole.png");
		//坐标位置
		CGPoint dPoint = CGPoint.ccp(size.width/2+180, size.height/2-270);
		//定义位置
		deleteBt.setPosition(dPoint);
		
		//删除弹出框
		deleteMes = CCSprite.sprite("role/delmes.png");
		CGPoint mPoint = CGPoint.ccp(size.width/2, size.height/2);
		deleteMes.setPosition(mPoint);
		deleteMes.setVisible(false);
		//弹出框关闭
		mesClose = CCSprite.sprite("role/mesclose.png");
		CGPoint xPoint = CGPoint.ccp(size.width/2+200, size.height/2+95);
		mesClose.setPosition(xPoint);
		mesClose.setVisible(false);
		//确定按钮
		mesConfirm = CCSprite.sprite("role/mesconfirm.png");
		CGPoint ccPoint = CGPoint.ccp(size.width/2-95, size.height/2-80);
		mesConfirm.setPosition(ccPoint);
		mesConfirm.setVisible(false);
		//取消按钮
		mesConcel = CCSprite.sprite("role/mesconcel.png");
		CGPoint czPoint = CGPoint.ccp(size.width/2+88, size.height/2-80);
		mesConcel.setPosition(czPoint);
		mesConcel.setVisible(false);
		
		//将精灵添加至图层
		this.addChild(roleSelectBj);
		this.addChild(createBt);
		this.addChild(startBt);
		this.addChild(deleteBt);
		this.addChild(roleName);
		this.addChild(roleLev);
		this.addChild(roleImg);
		this.addChild(roleType);
		this.addChild(deleteMes);
		this.addChild(mesClose);
		this.addChild(mesConfirm);
		this.addChild(mesConcel);
	}
	
	//触屏
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		//获取点击坐标点
		CGPoint touch = CGPoint.ccp(event.getX(), event.getY());
		//转换坐标系
		touch = CCDirector.sharedDirector().convertToGL(touch);
		//获取创建角色按钮
		CGRect cbt = createBt.getBoundingBox();
		//获取开始游戏按钮
		CGRect sbt = startBt.getBoundingBox();
		//获取删除角色按钮
		CGRect dbt = deleteBt.getBoundingBox();
		//获取弹出框关闭按钮
		CGRect mesclose = mesClose.getBoundingBox();
		//获取取消按钮
		CGRect mesconcel = mesConcel.getBoundingBox();
		//获取确定按钮
		CGRect mesconfirm = mesConfirm.getBoundingBox();
		//关闭事件
		if(CGRect.containsPoint(mesclose, touch))
		{
			mesHide();
		}
		if(CGRect.containsPoint(mesconcel, touch))
		{
			mesHide();
		}
		//确定删除角色
		if(CGRect.containsPoint(mesconfirm, touch))
		{
			//只有角色显示时才可以触发事件
			if(deleteMes.getVisible())
			{
				//删除角色
				db = new dbHelper(app);
				db.clearFeedTable();
				db.close();
				//实例CCScene场景节点
				CCScene scene = CCScene.node();
				//场景加载角色选择图层
				scene.addChild(new LoginRole(app));
				//导演更换场景
				CCDirector.sharedDirector().replaceScene(scene);
			}
			//隐藏弹出框
			mesHide();
		}
		//点击创建按钮
		if(CGRect.containsPoint(cbt, touch))
		{
			PublicFun.btSound(app);
			db = new dbHelper(app);
			Cursor cursor = db.selectId("1");
			try
			{
				//判断是否有数据
				if(cursor.getCount() == 0)
				{
					//实例CCScene场景节点
					CCScene scene = CCScene.node();
					//场景加载角色选择图层
					scene.addChild(new LoginRole(app));
					//导演更换场景
					CCDirector.sharedDirector().replaceScene(scene);
				}
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
			}finally
			{
				cursor.close();
				db.close();
			}
		}
		//点击开始游戏按钮
		if(CGRect.containsPoint(sbt, touch))
		{
			try
			{
				//实例CCScene场景节点
				CCScene scene = CCScene.node();
				//场景加载角色选择图层
				scene.addChild(new StartGame(app));
				//导演更换场景
				CCDirector.sharedDirector().replaceScene(scene);
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			
		}
		
		//点击删除角色按钮
		if(CGRect.containsPoint(dbt, touch))
		{
			PublicFun.btSound(app);
			if(deleteMes.getVisible())
			{
				mesHide();
			}else
			{
				deleteMes.setVisible(true);
				mesClose.setVisible(true);
				mesConfirm.setVisible(true);
				mesConcel.setVisible(true);
			}
		}
		return CCTouchDispatcher.kEventHandled;
	}
	
	//隐藏弹出框
	public void mesHide()
	{
		deleteMes.setVisible(false);
		mesClose.setVisible(false);
		mesConfirm.setVisible(false);
		mesConcel.setVisible(false);
	}
	
	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		super.onEnter();
		SoundEngine.sharedEngine().playSound(app, R.raw.loginselect, true);
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
