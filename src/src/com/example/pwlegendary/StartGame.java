package com.example.pwlegendary;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.content.Context;
import android.database.Cursor;
import android.view.MotionEvent;

public class StartGame extends CCLayer {
	
	Context app;
	CCSprite map01;
	CCSprite sprite01;
	CCSprite npc01;
	CCSprite npc02;
	CCSprite npc03;
	static final int kTagSprite = 1;
	static final int kTagMap01 = 2;
	static final int kTagnpc01 = 3;
	static final int kTagnpc02 = 4;
	static final int kTagnpc03 = 5;
	CCSprite npc01bg;
	CCSprite npc01bgClose;
	CCSprite lev01;
	static final int kTagLev01 = 6;
	CCSprite roleBt;
	CCSprite roleBtClose;
	CCSprite packageBt;
	CCSprite roleInfo;
	CCSprite roleSexImg;
	
	//角色状态信息
	CCLabel roleName;
	CCLabel roleSex;
	CCLabel roleType;
	CCLabel roleAtk;
	CCLabel roleLev;
	CCLabel roleHp;
	CCLabel roleMp;
	CCLabel roleDefA;
	CCLabel roleDefB;
	CCLabel roleExp;
	static RoleInfoModel model;
	
	static EquModel equWuqi;
	static EquModel equYifu;
	static EquModel equToukui;
	static EquModel equXianglian;
	static EquModel equShouzhuoZuo;
	static EquModel equShouzhuoYou;
	static EquModel equJiezhiZuo;
	static EquModel equJiezhiYou;
	
	CCSprite EQshouzhuoL;
	CCSprite EQshouzhuoR;
	CCSprite EQjiezhiL;
	CCSprite EQjiezhiR;
	CCSprite EQxianglian;
	CCSprite EQtoukui;
	CCSprite EQyifu;
	CCSprite EQwuqi;
	CCSprite EQwuqida;
	CCSprite EQyifuda;
	dbHelper db = null;
	/*
	 * 状态
	 * */
	CCSprite spriteState;
	
	public StartGame(Context context)
	{
		app = context;
		//此图层开启触屏事件
		this.setIsTouchEnabled(true);
		//时间调度
		this.schedule("funCollision",1/5);
		
		//安全区地图
		map01 = CCSprite.sprite("map/map01.png");
		CGSize size = CCDirector.sharedDirector().winSize();
		CGPoint spritePoint = CGPoint.ccp(size.width/2, size.height/2);
		map01.setPosition(spritePoint);
		
		//地图传送管理员
		npc01 = CCSprite.sprite("npc/wanshitong-01.png");
		CGPoint npc01Point = CGPoint.ccp(size.width/2+100, size.height/2+250);
		npc01.setPosition(npc01Point);
		//实例CCAnimation对象
		CCAnimation animation = CCAnimation.animation("gmNpc01");
		//循环图片
		for(int i = 1;i<3;i++)
		{
			animation.addFrame(String.format("npc/wanshitong-%02d.png",i));
		}
		//执行速度
		CCAnimate action = CCAnimate.action(1,animation,false);
		CCAnimate action_back = action.reverse();
		CCSequence seq = CCSequence.actions(action, action_back);
		//无限重复执行seq队列
		CCRepeatForever repeatForever = CCRepeatForever.action(seq);
		//进度条精灵执行动作
		npc01.runAction(repeatForever);
		
		//仓库管理员
		npc02 = CCSprite.sprite("npc/cangku-01.png");
		CGPoint npc02Point = CGPoint.ccp(size.width/2+250, size.height/2+160);
		npc02.setPosition(npc02Point);
		//实例CCAnimation对象
		CCAnimation animation2 = CCAnimation.animation("gmNpc02");
		//循环图片
		for(int i = 1;i<3;i++)
		{
			animation2.addFrame(String.format("npc/cangku-%02d.png",i));
		}
		//执行速度
		CCAnimate action2 = CCAnimate.action(1,animation2,false);
		CCAnimate action_back2 = action2.reverse();
		CCSequence seq2 = CCSequence.actions(action2, action_back2);
		//无限重复执行seq队列
		CCRepeatForever repeatForever2 = CCRepeatForever.action(seq2);
		//进度条精灵执行动作
		npc02.runAction(repeatForever2);
		
		//药店管理员
		npc03 = CCSprite.sprite("npc/yaodian-01.png");
		CGPoint npc03Point = CGPoint.ccp(size.width/2+400, size.height/2+80);
		npc03.setPosition(npc03Point);
		//实例CCAnimation对象
		CCAnimation animation3 = CCAnimation.animation("gmNpc03");
		//循环图片
		for(int i = 1;i<3;i++)
		{
			animation3.addFrame(String.format("npc/yaodian-%02d.png",i));
		}
		//执行速度
		CCAnimate action3 = CCAnimate.action(1,animation3,false);
		CCAnimate action_back3 = action3.reverse();
		CCSequence seq3 = CCSequence.actions(action3, action_back3);
		//无限重复执行seq队列
		CCRepeatForever repeatForever3 = CCRepeatForever.action(seq3);
		//进度条精灵执行动作
		npc03.runAction(repeatForever3);		
		
		//角色精灵
		sprite01 = CCSprite.sprite("roledef/def-01.png");
		CGPoint spPoint = CGPoint.ccp(size.width/2-160, size.height/2+20);
		sprite01.setPosition(spPoint);
		
		//精灵状态
		spriteState = CCSprite.sprite("role/roleState.png");
		CGPoint ssPoint = CGPoint.ccp(size.width/2-320, size.height/2-270);
		spriteState.setPosition(ssPoint);
		
		//传送员关卡背景
		npc01bg = CCSprite.sprite("lev/levbg.png");
		CGPoint n01bgPoint = CGPoint.ccp(size.width/2, size.height/2);
		npc01bg.setPosition(n01bgPoint);
		npc01bg.setVisible(false);
		
		//传送员背景关闭按钮
		npc01bgClose = CCSprite.sprite("lev/levclose.png");
		CGPoint n01bgcPoint = CGPoint.ccp(size.width/2+476, size.height/2+300);
		npc01bgClose.setPosition(n01bgcPoint);
		npc01bgClose.setVisible(false);
		
		//关卡01
		lev01  = CCSprite.sprite("map/map02s.png");
		CGPoint lev01Point = CGPoint.ccp(size.width/2-350, size.height/2+180);
		lev01.setPosition(lev01Point);
		lev01.setVisible(false);
		
		//角色按钮
		roleBt = CCSprite.sprite("role/rolebt.png");
		CGPoint roleBtPoint = CGPoint.ccp(size.width/2-330, size.height/2-300);
		roleBt.setPosition(roleBtPoint);
		
		//背包按钮
		packageBt = CCSprite.sprite("role/packagebt.png");
		CGPoint packageBtPoint = CGPoint.ccp(size.width/2-230, size.height/2-300);
		packageBt.setPosition(packageBtPoint);
		
		db = new dbHelper(app);
		Cursor cursor = db.selectId("1");
		try
		{
			model = new RoleInfoModel();
			//判断是否有数据
			if(cursor.getCount() == 0)
			{
				System.out.println("出错了");
			}else
			{
				
				while(cursor.moveToNext())
				{
					model.setSysID(cursor.getString(0).toString());
					model.setSysName(cursor.getString(1).toString());
					model.setSysSex(cursor.getString(2).toString());
					model.setSysType(cursor.getString(3).toString());
					model.setSysExp(cursor.getString(4).toString());
					model.setSysLev(cursor.getString(5).toString());
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
		
		//装备信息
		EQshouzhuoL = CCSprite.sprite("eqa/shouzhuo.png");
		CGPoint shouzhuoLPoint = CGPoint.ccp(size.width/2-370, size.height/2-130);
		EQshouzhuoL.setPosition(shouzhuoLPoint);
		EQshouzhuoL.setVisible(false);
		
		EQshouzhuoR = CCSprite.sprite("eqa/shouzhuo.png");
		CGPoint shouzhuoRPoint = CGPoint.ccp(size.width/2+30, size.height/2-130);
		EQshouzhuoR.setPosition(shouzhuoRPoint);
		EQshouzhuoR.setVisible(false);
		
		EQjiezhiL = CCSprite.sprite("eqa/jiezhi.png");
		CGPoint jiezhiLPoint = CGPoint.ccp(size.width/2-370, size.height/2-210);
		EQjiezhiL.setPosition(jiezhiLPoint);
		EQjiezhiL.setVisible(false);
		
		EQjiezhiR = CCSprite.sprite("eqa/jiezhi.png");
		CGPoint jiezhiRPoint = CGPoint.ccp(size.width/2+30, size.height/2-210);
		EQjiezhiR.setPosition(jiezhiRPoint);
		EQjiezhiR.setVisible(false);
		
		EQxianglian = CCSprite.sprite("eqa/xianglian.png");
		CGPoint xianglianPoint = CGPoint.ccp(size.width/2+30, size.height/2-50);
		EQxianglian.setPosition(xianglianPoint);
		EQxianglian.setVisible(false);
		
		EQtoukui = CCSprite.sprite("eqa/toukui.png");
		CGPoint toukuiPoint = CGPoint.ccp(size.width/2+30, size.height/2+30);
		EQtoukui.setPosition(toukuiPoint);
		EQtoukui.setVisible(false);
		
		EQyifu = CCSprite.sprite("eqa/yifu.png");
		CGPoint yifuPoint = CGPoint.ccp(size.width/2+30, size.height/2+105);
		EQyifu.setPosition(yifuPoint);
		EQyifu.setVisible(false);
		
		EQwuqi = CCSprite.sprite("eqa/wuqi.png");
		CGPoint wuqiPoint = CGPoint.ccp(size.width/2-370, size.height/2-50);
		EQwuqi.setPosition(wuqiPoint);
		EQwuqi.setVisible(false);
		
		EQwuqida = CCSprite.sprite("eqa/wuqida.png");
		CGPoint wuqidaPoint = CGPoint.ccp(size.width/2-235, size.height/2-50);
		EQwuqida.setPosition(wuqidaPoint);
		EQwuqida.setVisible(false);
		
		//实例装备
		wuqi();
		yifu();
		toukui();
		xianglian();
		shouzhuozuo();
		shouzhuoyou();
		jiezhizuo();
		jiezhiyou();
		
		//角色信息
		roleInfo = CCSprite.sprite("role/roleinfo.png");
		CGPoint roleInfoPoint = CGPoint.ccp(size.width/2, size.height/2);
		roleInfo.setPosition(roleInfoPoint);
		roleInfo.setVisible(false);
		
		//角色信息关闭按钮
		roleBtClose = CCSprite.sprite("role/mesclose.png");
		CGPoint roleClosePoint = CGPoint.ccp(size.width/2+410, size.height/2+320);
		roleBtClose.setPosition(roleClosePoint);
		roleBtClose.setVisible(false);
		
		//角色名称
		roleName = CCLabel.makeLabel(model.getSysName(), "roleName", 20);
		roleName.setPosition(size.width/2-200,size.height/2+110);
		roleName.setAnchorPoint(0, 0);//左对齐
		roleName.setVisible(false);
		
		//攻击力
		roleAtk = CCLabel.makeLabel(gongjili(), "roleAtk", 20);
		roleAtk.setPosition(size.width/2+260,size.height/2+110);
		roleAtk.setAnchorPoint(0, 0);//左对齐
		roleAtk.setVisible(false);
		
		//等级
		roleLev = CCLabel.makeLabel(model.getSysLev(), "roleLev", 20);
		roleLev.setPosition(size.width/2-265,size.height/2+215);
		roleLev.setAnchorPoint(0, 0);//左对齐
		roleLev.setVisible(false);
		
		//物理防御
		roleDefA = CCLabel.makeLabel(fangyu(), "roleDefA", 20);
		roleDefA.setPosition(size.width/2+260,size.height/2+30);
		roleDefA.setAnchorPoint(0, 0);//左对齐
		roleDefA.setVisible(false);
		
		//魔法防御
		roleDefB = CCLabel.makeLabel(fangyu(), "roleDefB", 20);
		roleDefB.setPosition(size.width/2+260,size.height/2);
		roleDefB.setAnchorPoint(0, 0);//左对齐
		roleDefB.setVisible(false);
		
		//血
		roleHp = CCLabel.makeLabel(""+(shengming()+Integer.parseInt(model.getSysLev())*200), "roleHp", 20);
		roleHp.setPosition(size.width/2+260,size.height/2-30);
		roleHp.setAnchorPoint(0, 0);//左对齐
		roleHp.setVisible(false);
		
		//蓝
		roleMp = CCLabel.makeLabel("10", "roleMp", 20);
		roleMp.setPosition(size.width/2+260,size.height/2-60);
		roleMp.setAnchorPoint(0, 0);//左对齐	
		roleMp.setVisible(false);
		
		//显示男女
		CGPoint roleSexPoint = CGPoint.ccp(size.width/2-180, size.height/2-100);
		if(model.getSysSex().equals("1"))
		{
			roleSexImg = CCSprite.sprite("eqa/yifudanan.png");
			roleSexImg.setPosition(roleSexPoint);
			roleSexImg.setVisible(false);
		}else
		{
			roleSexImg = CCSprite.sprite("eqa/yifudanv.png");
			roleSexImg.setPosition(roleSexPoint);
			roleSexImg.setVisible(false);
		}
		
		this.addChild(map01,-1,kTagMap01);
		this.addChild(npc01,1,kTagnpc01);
		this.addChild(npc02,1,kTagnpc02);
		this.addChild(npc03,1,kTagnpc03);
		this.addChild(sprite01,1,kTagSprite);
		this.addChild(spriteState,2);
		this.addChild(npc01bg,2);
		this.addChild(npc01bgClose,2);
		this.addChild(lev01,2,kTagLev01);
		this.addChild(roleBt,3);
		this.addChild(packageBt,3);
		this.addChild(roleInfo,3);
		this.addChild(roleBtClose,3);
		//装备
		this.addChild(EQshouzhuoL,3);
		this.addChild(EQshouzhuoR,3);	
		this.addChild(EQjiezhiL,3);
		this.addChild(EQjiezhiR,3);
		this.addChild(EQxianglian,3);
		this.addChild(EQtoukui,3);
		this.addChild(EQyifu,3);
		this.addChild(EQwuqi,3);
		this.addChild(EQwuqida,4);
		
		
		this.addChild(roleName,3);
		this.addChild(roleAtk,3);
		this.addChild(roleLev,3);
		this.addChild(roleDefA,3);
		this.addChild(roleDefB,3);
		this.addChild(roleHp,3);
		this.addChild(roleMp,3);
		this.addChild(roleSexImg,3);
		
		//动作
		selectImg(8);
	}
	
	//判断是否发生碰撞
	public void funCollision(float delta)
	{
		//碰撞判断
		CGRect spriteNarrow = sprite01.getBoundingBox();
		spriteNarrow.size.height = 100;
		spriteNarrow.size.width = 100;
		//判断精灵与NPC碰撞
		if(CGRect.intersects(spriteNarrow,npc01.getBoundingBox()))
        {
        	//System.out.println("碰撞");
        	CCNode s = getChildByTag(kTagSprite);
        	//获取当前位置
            CGPoint p1 = s.getPosition();
            //设置偏离
    		s.setPosition(p1.x-5,p1.y-5);
        }
		if(CGRect.intersects(spriteNarrow,npc02.getBoundingBox()))
        {
        	//System.out.println("碰撞");
        	CCNode s = getChildByTag(kTagSprite);
        	//获取当前位置
            CGPoint p1 = s.getPosition();
            //设置偏离
    		s.setPosition(p1.x-5,p1.y-5);
        }
		if(CGRect.intersects(spriteNarrow,npc03.getBoundingBox()))
        {
        	//System.out.println("碰撞");
        	CCNode s = getChildByTag(kTagSprite);
        	//获取当前位置
            CGPoint p1 = s.getPosition();
            //设置偏离
    		s.setPosition(p1.x-5,p1.y-5);
        }
	}	
	
	//触屏
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		//获取点击坐标 -100 定位到精灵脚下
		CGPoint convertedLocation = CCDirector.sharedDirector()
            	.convertToGL(CGPoint.make(event.getX(), event.getY()-100));
		CGPoint ppp = CCDirector.sharedDirector()
            	.convertToGL(CGPoint.make(event.getX(), event.getY()));
		
		//获取精灵tag
		CCNode s = getChildByTag(kTagSprite);
        //停止精灵动作
		s.stopAllActions();
		//执行新动作
		CCMoveTo moveTo= CCMoveTo.action(PublicFun.moveTime(s.getPosition(),convertedLocation), convertedLocation);
		//监听回调函数
      	CCCallFuncN callFuncN = CCCallFuncN.action(this, "onActionFinished");
      	CCSequence seq = CCSequence.actions(moveTo, callFuncN);
        s.runAction(seq);
        
        //判断移动方向
        switch(PublicFun.moveDirection(s.getPosition(), convertedLocation))
        {
        	case 0:
        		selectImg(0);
        		break;
        	case 1:
        		selectImg(1);
        		break;
        	case 2:
        		selectImg(2);
        		break;
        	case 3:
        		selectImg(3);
        		break;
        	case 4:
        		selectImg(4);
        		break;
        	case 5:
        		selectImg(5);
        		break;
        	case 6:
        		selectImg(6);
        		break;
        	case 7:
        		selectImg(7);
        		break;
        	default:
        		System.out.println("未知方向!!");
        		break;
        }
        
        //判断点击传送员
      	CGRect btnpc01 = npc01.getBoundingBox();
      	if(CGRect.containsPoint(btnpc01, ppp))
      	{
      		npc01bg.setVisible(true);
      		npc01bgClose.setVisible(true);
      		lev01.setVisible(true);
      	}
      	//判断关闭按钮
      	CGRect btnpc01c = npc01bgClose.getBoundingBox();
      	if(CGRect.containsPoint(btnpc01c, ppp))
      	{
      		npc01bg.setVisible(false);
      		npc01bgClose.setVisible(false);
      		lev01.setVisible(false);
      	}
      	//判断关卡01按钮
      	CGRect lev01Bt = lev01.getBoundingBox();
      	if(CGRect.containsPoint(lev01Bt, ppp))
      	{
      		if(npc01bg.getVisible())
      		{
      			//传送关卡01
				CCScene scene = CCScene.node();
				//场景加载角色选择图层
				scene.addChild(new Lev01(app));
				//导演更换场景
				CCDirector.sharedDirector().replaceScene(scene);
      		}          
      	}
      	
      	//判断角色按钮
      	CGRect roleb = roleBt.getBoundingBox();
      	if(CGRect.containsPoint(roleb, ppp))
      	{
      		roleBtClose.setVisible(true);
      		roleInfo.setVisible(true);
      		roleSexImg.setVisible(true);
      		
      		roleName.setVisible(true);
      		roleAtk.setVisible(true);
      		roleLev.setVisible(true);
      		roleHp.setVisible(true);
      		roleMp.setVisible(true);
      		roleDefA.setVisible(true);
      		roleDefB.setVisible(true);
      		
      		EQshouzhuoL.setVisible(true);
      		EQshouzhuoR.setVisible(true);
      		EQjiezhiL.setVisible(true);
      		EQjiezhiR.setVisible(true);
      		EQxianglian.setVisible(true);
      		EQtoukui.setVisible(true);
      		EQyifu.setVisible(true);
      		EQwuqida.setVisible(true);
      		EQwuqi.setVisible(true);
      	}
      	
      	//关闭按钮
      	CGRect roleClose =roleBtClose.getBoundingBox();
      	if(CGRect.containsPoint(roleClose, ppp))
      	{
      		roleBtClose.setVisible(false);
      		roleInfo.setVisible(false);
      		roleSexImg.setVisible(false);
      		
      		roleName.setVisible(false);
      		roleAtk.setVisible(false);
      		roleLev.setVisible(false);
      		roleHp.setVisible(false);
      		roleMp.setVisible(false);
      		roleDefA.setVisible(false);
      		roleDefB.setVisible(false);
      		
      		EQshouzhuoL.setVisible(false);
      		EQshouzhuoR.setVisible(false);
      		EQjiezhiL.setVisible(false);
      		EQjiezhiR.setVisible(false);
      		EQxianglian.setVisible(false);
      		EQtoukui.setVisible(false);
      		EQyifu.setVisible(false);
      		EQwuqida.setVisible(false);
      		EQwuqi.setVisible(false);
      	}
        
        return CCTouchDispatcher.kEventHandled;
	}
	
	/*
	 * 精灵移动到目的地后停止滚动图片
	 * */
	public void onActionFinished(Object sender)
	{
		CCNode s = getChildByTag(kTagSprite);
        //停止精灵动作
		s.stopAllActions();
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
				spriteAs(animation,action,action_back,seq,repeatForever,"left");
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
				//进度条精灵执行动作
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
	
	
	//武器状态
	public void wuqi()
	{
		equWuqi = new EquModel();
		equWuqi.setSysName("三尖叉");
		equWuqi.setSysSAttack("20");
		equWuqi.setSysDefA("5");
		equWuqi.setSysHP("100");
		equWuqi.setSysLucky("2");
	}
	//衣服
	public void yifu()
	{
		equYifu = new EquModel();
		equYifu.setSysName("新手衣");
		equYifu.setSysSAttack("10");
		equYifu.setSysDefA("3");
		equYifu.setSysHP("80");
		equYifu.setSysLucky("0");
	}
	//头盔
	public void toukui()
	{
		equToukui = new EquModel();
		equToukui.setSysName("新手头盔");
		equToukui.setSysSAttack("5");
		equToukui.setSysDefA("2");
		equToukui.setSysHP("50");
		equToukui.setSysLucky("0");
	}
	//项链
	public void xianglian()
	{
		equXianglian = new EquModel();
		equXianglian.setSysName("新手项链");
		equXianglian.setSysSAttack("5");
		equXianglian.setSysDefA("2");
		equXianglian.setSysHP("50");
		equXianglian.setSysLucky("0");
	}
	//手镯左
	public void shouzhuozuo()
	{
		equShouzhuoZuo = new EquModel();
		equShouzhuoZuo.setSysName("新手手镯");
		equShouzhuoZuo.setSysSAttack("5");
		equShouzhuoZuo.setSysDefA("2");
		equShouzhuoZuo.setSysHP("50");
		equShouzhuoZuo.setSysLucky("0");
	}
	//手镯右
	public void shouzhuoyou()
	{
		equShouzhuoYou = new EquModel();
		equShouzhuoYou.setSysName("新手手镯");
		equShouzhuoYou.setSysSAttack("5");
		equShouzhuoYou.setSysDefA("2");
		equShouzhuoYou.setSysHP("50");
		equShouzhuoYou.setSysLucky("0");
	}	
	//戒指左
	public void jiezhizuo()
	{
		equJiezhiZuo = new EquModel();
		equJiezhiZuo.setSysName("新手戒指");
		equJiezhiZuo.setSysSAttack("5");
		equJiezhiZuo.setSysDefA("2");
		equJiezhiZuo.setSysHP("50");
		equJiezhiZuo.setSysLucky("0");
	}
	//戒指右
	public void jiezhiyou()
	{
		equJiezhiYou = new EquModel();
		equJiezhiYou.setSysName("新手戒指");
		equJiezhiYou.setSysSAttack("5");
		equJiezhiYou.setSysDefA("2");
		equJiezhiYou.setSysHP("50");
		equJiezhiYou.setSysLucky("0");
	}
	//总攻击力
	public static String gongjili()
	{
		int s= Integer.parseInt(equWuqi.getSysSAttack())+Integer.parseInt(equYifu.getSysSAttack())+Integer.parseInt(equToukui.getSysSAttack())
				+Integer.parseInt(equXianglian.getSysSAttack())+Integer.parseInt(equShouzhuoZuo.getSysSAttack())
				+Integer.parseInt(equShouzhuoYou.getSysSAttack())+Integer.parseInt(equJiezhiZuo.getSysSAttack())+Integer.parseInt(equJiezhiYou.getSysSAttack());
		return String.valueOf(s);
	}
	//总防御力
	public static String fangyu()
	{
		int s= Integer.parseInt(equWuqi.getSysDefA())+Integer.parseInt(equYifu.getSysDefA())+Integer.parseInt(equToukui.getSysDefA())
				+Integer.parseInt(equXianglian.getSysDefA())+Integer.parseInt(equShouzhuoZuo.getSysDefA())
				+Integer.parseInt(equShouzhuoYou.getSysDefA())+Integer.parseInt(equJiezhiZuo.getSysDefA())+Integer.parseInt(equJiezhiYou.getSysDefA());
		return String.valueOf(s);
	}
	//总生命
	public static int shengming()
	{
		int s= Integer.parseInt(equWuqi.getSysHP())+Integer.parseInt(equYifu.getSysHP())+Integer.parseInt(equToukui.getSysHP())
				+Integer.parseInt(equXianglian.getSysHP())+Integer.parseInt(equShouzhuoZuo.getSysHP())
				+Integer.parseInt(equShouzhuoYou.getSysHP())+Integer.parseInt(equJiezhiZuo.getSysHP())+Integer.parseInt(equJiezhiYou.getSysHP());
		return s;
	}
	//总幸运
	public static String xingyun()
	{
		int s= Integer.parseInt(equWuqi.getSysLucky())+Integer.parseInt(equYifu.getSysLucky())+Integer.parseInt(equToukui.getSysLucky())
				+Integer.parseInt(equXianglian.getSysLucky())+Integer.parseInt(equShouzhuoZuo.getSysLucky())
				+Integer.parseInt(equShouzhuoYou.getSysLucky())+Integer.parseInt(equJiezhiZuo.getSysLucky())+Integer.parseInt(equJiezhiYou.getSysLucky());
		return String.valueOf(s);
	}
	
	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		super.onEnter();
		SoundEngine.sharedEngine().playSound(app, R.raw.anquanqu, true);
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
