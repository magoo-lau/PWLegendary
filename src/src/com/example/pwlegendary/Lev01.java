/*
 * �ؿ�01
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
	
	//����ħԳ��ʼֵ
	String gName = "����ħԳ";
	//�ȼ�
	String gLev = "10��";
	//Ѫ��
	int gHP = 700;
	//����
	int gDef = 15;
	//������
	int gSAttack = 10;
	//���ﾭ��
	private static int guaiExp = 60;
	//˵����Ѫ�����㣺װ��Ѫ+�ȼ�*200
	int shengming = StartGame.shengming()+Integer.parseInt(StartGame.model.getSysLev())*200;
	
	static final int kTagSprite = 1;
	static final int kTagSprite2 = 2;
	//��� 0 ʧ�� 1ʤ��
	public static int result = 0;
	
	public Lev01(Context context)
	{
		app = context;
		//ʱ�����
		this.schedule("scheduleFun",1);
		//������ͼ
		lev01Bg = CCSprite.sprite("map/map02.png");
		CGSize size = CCDirector.sharedDirector().winSize();
		CGPoint mapPoint = CGPoint.ccp(size.width/2, size.height/2);
		lev01Bg.setPosition(mapPoint);
		//����01
		guaiwu01 = CCSprite.sprite("lev/lev01-01.png");
		CGPoint guaiwu01Point = CGPoint.ccp(size.width/2+450, size.height/2);
		guaiwu01.setPosition(guaiwu01Point);
		//ʵ��CCAnimation����
		CCAnimation animation = CCAnimation.animation("gmguaiwu01");
		//ѭ��ͼƬ
		for(int i = 1;i<5;i++)
		{
			animation.addFrame(String.format("lev/lev02-%02d.png",i));
		}
		//ִ���ٶ�
		CCAnimate action = CCAnimate.action(2,animation,false);
		CCAnimate action_back = action.reverse();
		CCSequence seq = CCSequence.actions(action, action_back);
		//�����ظ�ִ��seq����
		CCRepeatForever repeatForever = CCRepeatForever.action(seq);
		//����������ִ�ж���
		guaiwu01.runAction(repeatForever);
		
		//��ɫ����
		sprite01 = CCSprite.sprite("roledef/def-01.png");
		CGPoint spPoint = CGPoint.ccp(size.width/2-450, size.height/2-50);
		sprite01.setPosition(spPoint);

		//������Ϣ
		roleName = CCLabel.makeLabel("���ƣ�" + StartGame.model.getSysName(), "roleName", 35);
		roleName.setPosition(size.width/2-560,size.height/2+280);
		ccColor3B color = new ccColor3B(255, 0, 0);
		roleName.setColor(color);
		roleName.setAnchorPoint(0, 0);//�����
		
		roleLev = CCLabel.makeLabel("�ȼ���" + StartGame.model.getSysLev()+"��", "roleLev", 35);
		roleLev.setPosition(size.width/2-560,size.height/2+235);
		roleLev.setColor(color);
		roleLev.setAnchorPoint(0, 0);
		
		roleHP = CCLabel.makeLabel("Ѫ����" + shengming, "roleHP", 35);
		roleHP.setPosition(size.width/2-560,size.height/2+190);
		roleHP.setColor(color);
		roleHP.setAnchorPoint(0, 0);
		
		guaiwuName = CCLabel.makeLabel("���ƣ�" + gName, "gName", 35);
		guaiwuName.setPosition(size.width/2+300,size.height/2+280);
		guaiwuName.setColor(color);
		guaiwuName.setAnchorPoint(0, 0);//�����
		
		guaiwuLev = CCLabel.makeLabel("�ȼ���" + gLev, "roleLev", 35);
		guaiwuLev.setPosition(size.width/2+300,size.height/2+235);
		guaiwuLev.setColor(color);
		guaiwuLev.setAnchorPoint(0, 0);
		
		guaiwuHP = CCLabel.makeLabel("Ѫ����" + gHP, "roleHP", 35);
		guaiwuHP.setPosition(size.width/2+300,size.height/2+190);
		guaiwuHP.setColor(color);
		guaiwuHP.setAnchorPoint(0, 0);
		
		outInfo = CCLabel.makeLabel("0", "outInfo", 100);
		outInfo.setPosition(size.width/2,size.height/2);
		outInfo.setColor(color);
		outInfo.setAnchorPoint(0, 0);
		
		expLab = CCLabel.makeLabel("���飺"+StartGame.model.getSysExp()+" / " + + returnLev(Integer.parseInt(StartGame.model.getSysLev())), "expLab", 35);
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
		
		//����
		selectImg(8);
	}
	
	/*
	 * ��������
	 * */
	int i = 0;
	public void scheduleFun(float delta){
		i++;
		//�˺�ֵ
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
        	//����ս��
        	//��ȡ����tag
    		CCNode s = getChildByTag(kTagSprite);
            //ֹͣ���鶯��
    		s.stopAllActions();
    		//ִ���¶���
    		CGPoint convertedLocation = CGPoint.ccp(530, 400);
    		CCMoveTo moveTo= CCMoveTo.action(PublicFun.moveTime(s.getPosition(),convertedLocation), convertedLocation);
    		//�����ص�����
          	CCCallFuncN callFuncN = CCCallFuncN.action(this, "onActionFinished");
          	CCSequence seq = CCSequence.actions(moveTo, callFuncN);
            s.runAction(seq);
            selectImg(2);
            
            CCNode s2 = getChildByTag(kTagSprite2);
            //ֹͣ����
    		s2.stopAllActions();
    		CCMoveTo moveTo2= CCMoveTo.action(PublicFun.moveTime(s2.getPosition(),convertedLocation), convertedLocation);
    		CCCallFuncN callFuncN2 = CCCallFuncN.action(this, "onActionFinished");
          	CCSequence seq2 = CCSequence.actions(moveTo2, callFuncN2);
            s2.runAction(seq2);
            selectImg(6);
            
        	//��������
        	//�������﹥�����ޣ�˫�����޹�������
        	if(i%2==0)
            {
        		SoundEngine.sharedEngine().playEffect(app, R.raw.pk);
        		//�˺�=���﹥����*�����-���﹥����*�����-�������
            	shanghai = Integer.parseInt(StartGame.gongjili())*MathNum() - gSAttack * MathNum()-gDef;
            	if (shanghai <= 0)
                {
                    shanghai = 0;
                }
            	gHP = gHP - shanghai;
            	 if (gHP <= 0)
                 {
            		 gHP = 0;
            		 guaiwuHP.setString("Ѫ����"+String.valueOf(gHP));
                     //ֹͣ����
            		 this.unschedule("scheduleFun");
                     System.out.println("ħԳ���ˣ���Ϸ����");
                     //ս�����
                     result = 1;
                     //���������������Ӿ��飺ÿ������60�㾭��
                     int Exp = Integer.parseInt(StartGame.model.getSysExp());
                     Exp = Exp + guaiExp;
                     int Lev=Integer.parseInt(StartGame.model.getSysLev());
                     //�������ֵ���ڵ��������������飬��������
                     if (Exp >= returnLev(Lev))
                     {
                    	 //���������������������ʣ�ྭ������һ����
                         if (Exp > returnLev(Lev))
                         {
                             Exp = Exp - returnLev(Lev);
                             Lev = Lev + 1;
                             //�ȼ�
                             roleLev.setString("�ȼ���"+Lev+"��");
                             //��������
                             expLab.setString("���飺"+Exp+ " / "+returnLev(Lev));
                         }
                         else
                         {
                             Lev = Lev + 1;
                             Exp = 0;
                             //�ȼ�
                             roleLev.setString("�ȼ���"+Lev+"��");
                             //��������
                             expLab.setString("���飺"+Exp+ " / "+returnLev(Lev));
                         }
                     }else
                     {
                         //��������
                    	 expLab.setString("���飺"+Exp+ " / "+returnLev(Lev));
                     }
                     //����ֵ���ȼ��������ݿ�
                     db = new dbHelper(app);
                     try{
                    	 db.updateRole(Exp, Lev);
                     }catch(Exception e)
         			 {
         				System.out.println(e.getMessage());
         			 }finally
         			 {
         				//�ر����ݿ�
         				db.close();
         				//��ת��ս���������
        				CCScene scene = CCScene.node();
        				//�������ؽ�ɫѡ��ͼ��
        				scene.addChild(new Result(app));
        				//���ݸ�������
        				CCDirector.sharedDirector().replaceScene(scene);
         			 }
                 }
                 else
                 {
                	 guaiwuHP.setString("Ѫ����"+String.valueOf(gHP));
                     System.out.println("�ߺ���ħԳ����������ħԳ��ʧ"+shanghai+"��Ѫ");
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
                     roleHP.setString("Ѫ����"+String.valueOf(shengming));
                     //ֹͣ����
            		 this.unschedule("scheduleFun");
                     System.out.println("�ߺ����ˣ���Ϸ����");
                     result = 0;
                     //��ת��ս���������
     				CCScene scene = CCScene.node();
     				//�������ؽ�ɫѡ��ͼ��
     				scene.addChild(new Result(app));
     				//���ݸ�������
     				CCDirector.sharedDirector().replaceScene(scene);
                 }
                 else
                 {
                     roleHP.setString("Ѫ����"+String.valueOf(shengming));
                     System.out.println("ħԳ��ߺ߷����������ߺ���ʧ"+shanghai+"��Ѫ");
                 }
            }
            
        	
        }
	}
	
	//������ʽ������
	/// <summary>
	/// �ȼ���ʽ
	/// </summary>
	/// <param name="lev">��ǰ�ȼ�</param>
	/// <returns>��������</returns>
	public int returnLev(int lev)
	{
	       return 30 * (lev * lev * lev + 5 * lev) - 80;
	}
	
	//�����
	public int MathNum()
	{
		return new Random().nextInt(6);
	}
	
	/*
	 * ���鶯��
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
			//Ĭ��״̬
			case 8:
				//ʵ��CCAnimation����
				animation = CCAnimation.animation("gmSprite");
				//ѭ��ͼƬ
				for(int i = 1;i<4;i++)
				{
					animation.addFrame(String.format("roledef/def-%02d.png",i));
				}
				//ִ���ٶ�
				action = CCAnimate.action(1,animation,false);
				action_back = action.reverse();
				seq = CCSequence.actions(action, action_back);
				//�����ظ�ִ��seq����
				repeatForever = CCRepeatForever.action(seq);
				//ִ�ж���
				sprite01.runAction(repeatForever);
				break;
			default:
				System.out.println("error");
				break;
		}
	}

	/*
	 * �������鶯��
	 * */
	public void spriteAs(CCAnimation animation,CCAnimate action,CCAnimate action_back,CCSequence seq,CCRepeatForever repeatForever,String name)
	{
		//ʵ��CCAnimation����
		animation = CCAnimation.animation("gmSprite");
		//ѭ��ͼƬ
		for(int i = 1;i<4;i++)
		{
			animation.addFrame(String.format("roledef/"+name+"-%02d.png",i));
		}
		//ִ���ٶ�
		action = CCAnimate.action(1f/5,animation,false);
		action_back = action.reverse();
		seq = CCSequence.actions(action, action_back);
		
		repeatForever = CCRepeatForever.action(seq);
		sprite01.runAction(repeatForever);
	}
	public void spriteAs2(CCAnimation animation,CCAnimate action,CCAnimate action_back,CCSequence seq,CCRepeatForever repeatForever,String name)
	{
		//ʵ��CCAnimation����
		animation = CCAnimation.animation("gmSprite2");
		//ѭ��ͼƬ
		for(int i = 1;i<5;i++)
		{
			animation.addFrame(String.format("lev/lev02-%02d.png",i));
		}
		//ִ���ٶ�
		action = CCAnimate.action(1f/5,animation,false);
		action_back = action.reverse();
		seq = CCSequence.actions(action, action_back);
		
		repeatForever = CCRepeatForever.action(seq);
		guaiwu01.runAction(repeatForever);
	}

	/*
	 * �����ƶ���Ŀ�ĵغ�ֹͣ����ͼƬ
	 * */
	public void onActionFinished(Object sender)
	{
		CCNode s = getChildByTag(kTagSprite);
        //ֹͣ���鶯��
		s.stopAllActions();
		CCNode s2 = getChildByTag(kTagSprite2);
        //ֹͣ���鶯��
		s2.stopAllActions();
		
		//��������
		CCAnimation animation = CCAnimation.animation("sprite01");
		//ѭ��ͼƬ
		for(int i = 1;i<5;i++)
		{
			animation.addFrame(String.format("roledef/z-%02d.png",i));
		}
		//ִ���ٶ�
		CCAnimate action = CCAnimate.action(2,animation,false);
		CCAnimate action_back = action.reverse();
		CCSequence seq = CCSequence.actions(action, action_back);
		//�����ظ�ִ��seq����
		CCRepeatForever repeatForever = CCRepeatForever.action(seq);
		//����������ִ�ж���
		sprite01.runAction(repeatForever);
		
		CCAnimation animation2 = CCAnimation.animation("gmguaiwu01");
		//ѭ��ͼƬ
		for(int i = 1;i<9;i++)
		{
			animation2.addFrame(String.format("lev/lev01-%02d.png",i));
		}
		//ִ���ٶ�
		CCAnimate action2 = CCAnimate.action(2,animation2,false);
		CCAnimate action_back2 = action.reverse();
		CCSequence seq2 = CCSequence.actions(action2, action_back2);
		//�����ظ�ִ��seq����
		CCRepeatForever repeatForever2 = CCRepeatForever.action(seq2);
		//����������ִ�ж���
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
