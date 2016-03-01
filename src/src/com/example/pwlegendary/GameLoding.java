/*
 * 功能：进度条
 * */
package com.example.pwlegendary;

import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import android.content.Context;
import android.database.Cursor;

public class GameLoding extends CCLayer {
	
	//声明进度条精灵对象
	CCSprite gmLoding;
	//声明Logo精灵对象
	CCSprite gmLogo;
	Context app;
	dbHelper db = null;
	
	public GameLoding(Context context)
	{
		app = context;
		
		//实例进度条精灵对象
		gmLoding = CCSprite.sprite("loding/loding_01.png");
		gmLogo = CCSprite.sprite("loding/logo.png");
		//获取屏幕宽度、高度
		CGSize size = CCDirector.sharedDirector().winSize();
		//进度条位置
		CGPoint lodingPoint = CGPoint.ccp(size.width/2, size.height/3);
		CGPoint logoPoint = CGPoint.ccp(size.width/2,size.height/2);
		//设定位置
		gmLoding.setPosition(lodingPoint);
		gmLogo.setPosition(logoPoint);
		//将进度条精灵添加至图层
		this.addChild(gmLoding);
		this.addChild(gmLogo);
		
		//实例CCAnimation对象
		CCAnimation animation = CCAnimation.animation("gmLoding");
		//循环图片
		for(int i = 1;i<11;i++)
		{
			animation.addFrame(String.format("loding/loding_%02d.png",i));
		}
		//执行速度
		CCAnimate action = CCAnimate.action(1,animation,false);
		
		//监听回调函数
		CCCallFuncN callFuncN = CCCallFuncN.action(this, "onActionFinished");
		
		//精灵动作序列callFuncN监听执行结果
		CCSequence sequence = CCSequence.actions(action, callFuncN);
		
		//进度条精灵执行动作
		gmLoding.runAction(sequence);
	}
	
	//进度条执行完毕后进入角色登录场景
	public void onActionFinished(Object sender)
	{
		db = new dbHelper(app);
		Cursor cursor = db.selectId("1");
		//判断是否有数据,无数据跳转到创建角色场景，有数据跳转选择角色场景
		if(cursor.getCount() == 0)
		{
			//实例CCScene场景节点
			CCScene scene = CCScene.node();
			//场景加载创建角色图层
			scene.addChild(new LoginRole(app));
			//导演更换场景
			CCDirector.sharedDirector().replaceScene(scene);
		}else
		{
			//实例CCScene场景节点
			CCScene scene = CCScene.node();
			//场景加载角色选择图层
			scene.addChild(new RoleSelect(app));
			//导演更换场景
			CCDirector.sharedDirector().replaceScene(scene);
		}
		cursor.close();
		//关闭数据库
		db.close();
	}
	
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		
		if(db != null)
		{
			db.close();
		}
		
		super.onExit();
	}
}
