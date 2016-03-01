/*
 * 关卡01
 * */
package com.example.pwlegendary;

import java.util.Random;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.content.Context;

public class Lev01 extends CCLayer {
	
	Context app;
	CCSprite lev01Bg;
	CCSprite guaiwu01;
	CCSprite sprite01;
	CCLabel roleName;
	CCLabel roleLev;
	CCLabel roleHP;
	CCLabel guaiwuName;
	CCLabel guaiwuLev;
	CCLabel guaiwuHP;
	CCLabel outInfo;
	CCLabel expLab;
	dbHelper db = null;
	
	//冰极魔猿初始值
	String gName = "冰极魔猿";
	//等级
	String gLev = "10级";
	//血量
	int gHP = 700;
	//防御
	int gDef = 15;
	//攻击力
	int gSAttack = 10;
	//怪物经验
	private static int guaiExp = 60;
	//说明：血量计算：装备血+等级*200
	int shengming = StartGame.shengming()+Integer.parseInt(StartGame.model.getSysLev())*200;
	
	static final int kTagSprite = 1;
	static final int kTagSprite2 = 2;
	//结果 0 失败 1胜利
	public static int result = 0;
	
	public Lev01(Context context)
	{
		app = context;
		//时间调度
		this.schedule("scheduleFun",1);
		//背景地图
		lev01Bg = CCSprite.sprite("map/map02.png");
		CGSize size = CCDirector.sharedDirector().winSize();
		CGPoint mapPoint = CGPoint.ccp(size.width/2, size.height/2);
		lev01Bg.setPosition(mapPoint);
		//怪物01
		guaiwu01 = CCSprite.sprite("lev/lev01-01.png");
		CGPoint guaiwu01Point = CGPoint.ccp(size.width/2+450, size.height/2);
		guaiwu01.setPosition(guaiwu01Point);
		//实例CCAnimation对象
		CCAnimation animation = CCAnimation.animation("gmguaiwu01");
		//循环图片
		for(int i = 1;i<5;i++)
		{
			animation.addFrame(String.format("lev/lev02-%02d.png",i));
		}
		//执行速度
		CCAnimate action = CCAnimate.action(2,animation,false);
		CCAnimate action_back = action.reverse();
		CCSequence seq = CCSequence.actions(action, action_back);
		//无限重复执行seq队列
		CCRepeatForever repeatForever = CCRepeatForever.action(seq);
		//进度条精灵执行动作
		guaiwu01.runAction(repeatForever);
		
		//角色精灵
		sprite01 = CCSprite.sprite("roledef/def-01.png");
		CGPoint spPoint = CGPoint.ccp(size.width/2-450, size.height/2-50);
		sprite01.setPosition(spPoint);

		//人物信息
		roleName = CCLabel.makeLabel("名称：" + StartGame.model.getSysName(), "roleName", 35);
		roleName.setPosition(size.width/2-560,size.height/2+280);
		ccColor3B color = new ccColor3B(255, 0, 0);
		roleName.setColor(color);
		roleName.setAnchorPoint(0, 0);//左对齐
		
		roleLev = CCLabel.makeLabel("等级：" + StartGame.model.getSysLev()+"级", "roleLev", 35);
		roleLev.setPosition(size.width/2-560,size.height/2+235);
		roleLev.setColor(color);
		roleLev.setAnchorPoint(0, 0);
		
		roleHP = CCLabel.makeLabel("血量：" + shengming, "roleHP", 35);
		roleHP.setPosition(size.width/2-560,size.height/2+190);
		roleHP.setColor(color);
		roleHP.setAnchorPoint(0, 0);
		
		guaiwuName = CCLabel.makeLabel("名称：" + gName, "gName", 35);
		guaiwuName.setPosition(size.width/2+300,size.height/2+280);
		guaiwuName.setColor(color);
		guaiwuName.setAnchorPoint(0, 0);//左对齐
		
		guaiwuLev = CCLabel.makeLabel("等级：" + gLev, "roleLev", 35);
		guaiwuLev.setPosition(size.width/2+300,size.height/2+235);
		guaiwuLev.setColor(color);
		guaiwuLev.setAnchorPoint(0, 0);
		
		guaiwuHP = CCLabel.makeLabel("血量：" + gHP, "roleHP", 35);
		guaiwuHP.setPosition(size.width/2+300,size.height/2+190);
		guaiwuHP.setColor(color);
		guaiwuHP.setAnchorPoint(0, 0);
		
		outInfo = CCLabel.makeLabel("0", "outInfo", 100);
		outInfo.setPosition(size.width/2,size.height/2);
		outInfo.setColor(color);
		outInfo.setAnchorPoint(0, 0);
		
		expLab = CCLabel.makeLabel("经验："+StartGame.model.getSysExp()+" / " + + returnLev(Integer.parseInt(StartGame.model.getSysLev())), "expLab", 35);
		expLab.setPosition(size.width/2-560,size.height/2+145);
		expLab.setColor(color);
		expLab.setAnchorPoint(0, 0);
		
		this.addChild(lev01Bg,-1);
		this.addChild(guaiwu01,1,kTagSprite2);
		this.addChild(sprite01,1,kTagSprite);
		this.addChild(roleName,2);
		this.addChild(roleLev,2);
		this.addChild(roleHP,2);
		this.addChild(guaiwuName,2);
		this.addChild(guaiwuLev,2);
		this.addChild(guaiwuHP,2);
		this.addChild(outInfo,2);
		this.addChild(expLab,2);
		
		//动作
		selectImg(8);
	}
	
	/*
	 * 心跳方法
	 * */
	int i = 0;
	public void scheduleFun(float delta){
		i++;
		//伤害值
        int shanghai;
        
        if(i <= 4)
        {
        	if(i==3)
        	{
        		outInfo.setString("KO");
        	}else
        	{
        		if(i == 4)
        		{
        			outInfo.setVisible(false);
        		}else
        		{
        			outInfo.setString(String.valueOf(i));
        		}
        	}
        }else
        {
        	//界面战斗
        	//获取精灵tag
    		CCNode s = getChildByTag(kTagSprite);
            //停止精灵动作
    		s.stopAllActions();
    		//执行新动作
    		CGPoint convertedLocation = CGPoint.ccp(530, 400);
    		CCMoveTo moveTo= CCMoveTo.action(PublicFun.moveTime(s.getPosition(),convertedLocation), convertedLocation);
    		//监听回调函数
          	CCCallFuncN callFuncN = CCCallFuncN.action(this, "onActionFinished");
          	CCSequence seq = CCSequence.actions(moveTo, callFuncN);
            s.runAction(seq);
            selectImg(2);
            
            CCNode s2 = getChildByTag(kTagSprite2);
            //停止动作
    		s2.stopAllActions();
    		CCMoveTo moveTo2= CCMoveTo.action(PublicFun.moveTime(s2.getPosition(),convertedLocation), convertedLocation);
    		CCCallFuncN callFuncN2 = CCCallFuncN.action(this, "onActionFinished");
          	CCSequence seq2 = CCSequence.actions(moveTo2, callFuncN2);
            s2.runAction(seq2);
            selectImg(6);
            
        	//数据运算
        	//单：人物攻击怪兽，双：怪兽攻击人物
        	if(i%2==0)
            {
        		SoundEngine.sharedEngine().playEffect(app, R.raw.pk);
        		//伤害=人物攻击力*随机数-怪物攻击力*随机数-怪物防御
            	shanghai = Integer.parseInt(StartGame.gongjili())*MathNum() - gSAttack * MathNum()-gDef;
            	if (shanghai <= 0)
                {
                    shanghai = 0;
                }
            	gHP = gHP - shanghai;
            	 if (gHP <= 0)
                 {
            		 gHP = 0;
            		 guaiwuHP.setString("血量："+String.valueOf(gHP));
                     //停止调度
            		 this.unschedule("scheduleFun");
                     System.out.println("魔猿死了，游戏结束");
                     //战斗结果
                     result = 1;
                     //怪物死了人物增加经验：每次增加60点经验
                     int Exp = Integer.parseInt(StartGame.model.getSysExp());
                     Exp = Exp + guaiExp;
                     int Lev=Integer.parseInt(StartGame.model.getSysLev());
                     //如果经验值大于等于所需升级经验，精灵升级
                     if (Exp >= returnLev(Lev))
                     {
                    	 //如果大于则升级，升级后剩余经验留下一级用
                         if (Exp > returnLev(Lev))
                         {
                             Exp = Exp - returnLev(Lev);
                             Lev = Lev + 1;
                             //等级
                             roleLev.setString("等级："+Lev+"级");
                             //生级经验
                             expLab.setString("经验："+Exp+ " / "+returnLev(Lev));
                         }
                         else
                         {
                             Lev = Lev + 1;
                             Exp = 0;
                             //等级
                             roleLev.setString("等级："+Lev+"级");
                             //生级经验
                             expLab.setString("经验："+Exp+ " / "+returnLev(Lev));
                         }
                     }else
                     {
                         //生级经验
                    	 expLab.setString("经验："+Exp+ " / "+returnLev(Lev));
                     }
                     //经验值、等级插入数据库
                     db = new dbHelper(app);
                     try{
                    	 db.updateRole(Exp, Lev);
                     }catch(Exception e)
         			 {
         				System.out.println(e.getMessage());
         			 }finally
         			 {
         				//关闭数据库
         				db.close();
         				//跳转至战斗结果界面
        				CCScene scene = CCScene.node();
        				//场景加载角色选择图层
        				scene.addChild(new Result(app));
        				//导演更换场景
        				CCDirector.sharedDirector().replaceScene(scene);
         			 }
                 }
                 else
                 {
                	 guaiwuHP.setString("血量："+String.valueOf(gHP));
                     System.out.println("哼哼向魔猿发动攻击，魔猿损失"+shanghai+"点血");
                 }
            }else
            {
            	SoundEngine.sharedEngine().playEffect(app, R.raw.pk2);
            	shanghai = gSAttack * MathNum() - Integer.parseInt(StartGame.gongjili()) * MathNum()-Integer.parseInt(StartGame.fangyu());
            	 if (shanghai <= 0)
                 {
                     shanghai = 0;
                 }
            	 shengming = shengming- shanghai;
            	 if (shengming <= 0)
                 {
            		 shengming = 0;
                     roleHP.setString("血量："+String.valueOf(shengming));
                     //停止调度
            		 this.unschedule("scheduleFun");
                     System.out.println("哼哼死了，游戏结束");
                     result = 0;
                     //跳转至战斗结果界面
     				CCScene scene = CCScene.node();
     				//场景加载角色选择图层
     				scene.addChild(new Result(app));
     				//导演更换场景
     				CCDirector.sharedDirector().replaceScene(scene);
                 }
                 else
                 {
                     roleHP.setString("血量："+String.valueOf(shengming));
                     System.out.println("魔猿向哼哼发动攻击，哼哼损失"+shanghai+"点血");
                 }
            }
            
        	
        }
	}
	
	//升级公式方法：
	/// <summary>
	/// 等级公式
	/// </summary>
	/// <param name="lev">当前等级</param>
	/// <returns>升级经验</returns>
	public int returnLev(int lev)
	{
	       return 30 * (lev * lev * lev + 5 * lev) - 80;
	}
	
	//随机数
	public int MathNum()
	{
		return new Random().nextInt(6);
	}
	
	/*
	 * 精灵动作
	 * */
	private void selectImg(int tag)
	{
		CCAnimation animation = null;
		CCAnimate action = null;
		CCAnimate action_back = null;
		CCSequence seq = null;
		CCRepeatForever repeatForever = null;
		
		switch(tag)
		{
			case 0:
				spriteAs(animation,action,action_back,seq,repeatForever,"down");
				break;
			case 1:
				spriteAs(animation,action,action_back,seq,repeatForever,"rightdown");
				break;
			case 2:
				spriteAs(animation,action,action_back,seq,repeatForever,"right");
				break;
			case 3:
				spriteAs(animation,action,action_back,seq,repeatForever,"topright");
				break;
			case 4:
				spriteAs(animation,action,action_back,seq,repeatForever,"top");
				break;
			case 5:
				spriteAs(animation,action,action_back,seq,repeatForever,"topleft");
				break;
			case 6:
				//spriteAs(animation,action,action_back,seq,repeatForever,"left");
				spriteAs2(animation,action,action_back,seq,repeatForever,"left");
				break;
			case 7:
				spriteAs(animation,action,action_back,seq,repeatForever,"leftdown");
				break;
			//默认状态
			case 8:
				//实例CCAnimation对象
				animation = CCAnimation.animation("gmSprite");
				//循环图片
				for(int i = 1;i<4;i++)
				{
					animation.addFrame(String.format("roledef/def-%02d.png",i));
				}
				//执行速度
				action = CCAnimate.action(1,animation,false);
				action_back = action.reverse();
				seq = CCSequence.actions(action, action_back);
				//无限重复执行seq队列
				repeatForever = CCRepeatForever.action(seq);
				//执行动作
				sprite01.runAction(repeatForever);
				break;
			default:
				System.out.println("error");
				break;
		}
	}

	/*
	 * 更换精灵动作
	 * */
	public void spriteAs(CCAnimation animation,CCAnimate action,CCAnimate action_back,CCSequence seq,CCRepeatForever repeatForever,String name)
	{
		//实例CCAnimation对象
		animation = CCAnimation.animation("gmSprite");
		//循环图片
		for(int i = 1;i<4;i++)
		{
			animation.addFrame(String.format("roledef/"+name+"-%02d.png",i));
		}
		//执行速度
		action = CCAnimate.action(1f/5,animation,false);
		action_back = action.reverse();
		seq = CCSequence.actions(action, action_back);
		
		repeatForever = CCRepeatForever.action(seq);
		sprite01.runAction(repeatForever);
	}
	public void spriteAs2(CCAnimation animation,CCAnimate action,CCAnimate action_back,CCSequence seq,CCRepeatForever repeatForever,String name)
	{
		//实例CCAnimation对象
		animation = CCAnimation.animation("gmSprite2");
		//循环图片
		for(int i = 1;i<5;i++)
		{
			animation.addFrame(String.format("lev/lev02-%02d.png",i));
		}
		//执行速度
		action = CCAnimate.action(1f/5,animation,false);
		action_back = action.reverse();
		seq = CCSequence.actions(action, action_back);
		
		repeatForever = CCRepeatForever.action(seq);
		guaiwu01.runAction(repeatForever);
	}

	/*
	 * 精灵移动到目的地后停止滚动图片
	 * */
	public void onActionFinished(Object sender)
	{
		CCNode s = getChildByTag(kTagSprite);
        //停止精灵动作
		s.stopAllActions();
		CCNode s2 = getChildByTag(kTagSprite2);
        //停止精灵动作
		s2.stopAllActions();
		
		//更换动作
		CCAnimation animation = CCAnimation.animation("sprite01");
		//循环图片
		for(int i = 1;i<5;i++)
		{
			animation.addFrame(String.format("roledef/z-%02d.png",i));
		}
		//执行速度
		CCAnimate action = CCAnimate.action(2,animation,false);
		CCAnimate action_back = action.reverse();
		CCSequence seq = CCSequence.actions(action, action_back);
		//无限重复执行seq队列
		CCRepeatForever repeatForever = CCRepeatForever.action(seq);
		//进度条精灵执行动作
		sprite01.runAction(repeatForever);
		
		CCAnimation animation2 = CCAnimation.animation("gmguaiwu01");
		//循环图片
		for(int i = 1;i<9;i++)
		{
			animation2.addFrame(String.format("lev/lev01-%02d.png",i));
		}
		//执行速度
		CCAnimate action2 = CCAnimate.action(2,animation2,false);
		CCAnimate action_back2 = action.reverse();
		CCSequence seq2 = CCSequence.actions(action2, action_back2);
		//无限重复执行seq队列
		CCRepeatForever repeatForever2 = CCRepeatForever.action(seq2);
		//进度条精灵执行动作
		guaiwu01.runAction(repeatForever2);
	}
	
	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		super.onEnter();
		SoundEngine.sharedEngine().playSound(app, R.raw.lev01bg, true);
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
