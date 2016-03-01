/*
 * ���ܣ���¼������ɫ��
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
	//��¼����
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
	//�н�ɫ������
	String NanName[] = {"�����","׿����","κ����","������","�����","����־","�����"};
	//Ů��ɫ������
	String NvName[] = {"�������","��ͮ��","�Ϲ���ͮ","����ޱ","Ǯ˼��","�������","��ޱ��"};
	//��ɫѡ���ʾ
	private static int Tag = 1;
	//�Ա�
	private static int Sex = 1;
	
	dbHelper db = null;
	
	public LoginRole(Context context)
	{
		//��ɫ����
		super(new ccColor4B(0, 0, 0, 0));
		
		//��ͼ�㿪�������¼�
		this.setIsTouchEnabled(true);
		
		app = context;
		//ʵ����¼�������
		loginBg = CCSprite.sprite("role/roleselect.png");
		//����λ��
		CGSize size = CCDirector.sharedDirector().winSize();
		CGPoint spritePoint = CGPoint.ccp(size.width/2, size.height/2);
		//����λ��
		loginBg.setPosition(spritePoint);
		
		//սʿ�г�ʼ��ͼƬ
		zNan = CCSprite.sprite("role/znan.png");
		CGPoint bg =loginBg.getPosition();
		CGPoint zNanPoint = CGPoint.ccp(bg.x-239,bg.y+100);
		zNan.setPosition(zNanPoint);
		
		//սʿŮ
		zNv = CCSprite.sprite("role/znva.png");
		CGPoint zNvPoint = CGPoint.ccp(bg.x+239,bg.y+100);
		zNv.setPosition(zNvPoint);
		
		//սʿ��ť
		zBt = CCSprite.sprite("role/zbt.png");
		CGPoint zBtPoint = CGPoint.ccp(bg.x,bg.y+105);
		zBt.setPosition(zBtPoint);
		
		//��ʦ��ť
		fBt = CCSprite.sprite("role/fbt.png");
		CGPoint fBtPoint = CGPoint.ccp(bg.x,bg.y+25);
		fBt.setPosition(fBtPoint);
		fBt.setVisible(false);
		
		//��ʿ��ť
		dBt = CCSprite.sprite("role/dbt.png");
		CGPoint dBtPoint = CGPoint.ccp(bg.x,bg.y-55);
		dBt.setPosition(dBtPoint);
		dBt.setVisible(false);
		
		//����
		shaizi = CCSprite.sprite("role/shaizi.png");
		CGPoint shaiziPoint = CGPoint.ccp(bg.x+200, bg.y-180);
		shaizi.setPosition(shaiziPoint);
		
		//����
		roleName = CCLabel.makeLabel(RandomName(1), "Name", 32);
		CGPoint roleNamePoint = CGPoint.ccp(bg.x-140, bg.y-195);
		roleName.setPosition(roleNamePoint);
		roleName.setAnchorPoint(0, 0);//�����
		
		//��ʼ��Ϸ
		startGm = CCSprite.sprite("role/startbt.png");
		CGPoint startPoint = CGPoint.ccp(bg.x, bg.y-260);
		startGm.setPosition(startPoint);
		startGm.setVisible(true);
		
		//���ذ�ť
		returnBt = CCSprite.sprite("role/returnbt.png");
		CGPoint sPoint = CGPoint.ccp(bg.x+300, bg.y-180);
		returnBt.setPosition(sPoint);
		returnBt.setVisible(false);
		
		//�����������ͼ��
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
	
	//����
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		//��ȡ��������
		CGPoint touch = CGPoint.ccp(event.getX(), event.getY());
		//ת������ϵ
		touch = CCDirector.sharedDirector().convertToGL(touch);
		
		//��ȡ սʿϵ ��ť
		CGRect rectZbt = zBt.getBoundingBox();
		//��ȡ ��ʦϵ ��ť
		CGRect rectFbt = fBt.getBoundingBox();
		//��ȡ ��ʿϵ ��ť
		CGRect rectDbt = dBt.getBoundingBox();
		//��ȡսʿ��ͼƬ����λ��
		CGRect rectNan = zNan.getBoundingBox();
		//��ȡսʿŮͼƬ����λ��
		CGRect rectNv = zNv.getBoundingBox();
		//��ȡ��������
		CGRect rectShaizi = shaizi.getBoundingBox();
		//��ȡ��ʼ��Ϸ��ť
		CGRect RectStart = startGm.getBoundingBox();
		//��ȡ���ذ�ť
		CGRect RectReturn = returnBt.getBoundingBox();
		//սʿ��ť
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
		//��ʦ��ť
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
		//��ʿ��ť
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
			//սʿϵ��
			case 1:
				//�ж��Ƿ���(սʿ��)
				if(CGRect.containsPoint(rectNan, touch))
				{
					CCTexture2D textNan = CCTextureCache.sharedTextureCache().addImage("role/znan.png");
					zNan.setTexture(textNan);
					CCTexture2D textNv = CCTextureCache.sharedTextureCache().addImage("role/znva.png");
					zNv.setTexture(textNv);
					Sex = 1;
					roleName.setString(RandomName(Sex));
				}
				//սʿŮ
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
			//��ʦϵ��
			case 2:
				//�ж��Ƿ���(��ʦ��)
				if(CGRect.containsPoint(rectNan, touch))
				{
					CCTexture2D textNan = CCTextureCache.sharedTextureCache().addImage("role/fnan.png");
					zNan.setTexture(textNan);
					CCTexture2D textNv = CCTextureCache.sharedTextureCache().addImage("role/fnva.png");
					zNv.setTexture(textNv);
					Sex = 1;
					roleName.setString(RandomName(Sex));
				}
				//��ʦŮ
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
			//��ʿϵ��
			case 3:
				//�ж��Ƿ���(��ʿ��)
				if(CGRect.containsPoint(rectNan, touch))
				{
					CCTexture2D textNan = CCTextureCache.sharedTextureCache().addImage("role/dnan.png");
					zNan.setTexture(textNan);
					CCTexture2D textNv = CCTextureCache.sharedTextureCache().addImage("role/dnva.png");
					zNv.setTexture(textNv);
					Sex = 1;
					roleName.setString(RandomName(Sex));
				}
				//��ʿŮ
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
		
		//�ж�����
		if(CGRect.containsPoint(rectShaizi, touch))
		{
			PublicFun.btSound(app);
			//������˸
			CCBlink blink = CCBlink.action(1, 2);
			shaizi.runAction(blink);
			roleName.setString(RandomName(Sex));
		}
		
		//�жϿ�ʼ��ť
		if(CGRect.containsPoint(RectStart, touch))
		{
			PublicFun.btSound(app);
			startGm.setVisible(false);
			//������Ϸ
			//�����浥�û���ѯ�Ƿ�ѡ����ɫ�����ѡ���򲻿��Դ���
			db = new dbHelper(app);
			Cursor cursor = db.selectId("1");
			try{
				//�ж��Ƿ�������
				if(cursor.getCount() == 0)
				{
					//�����ʼ������
					db.insert(strName, Sex, Tag, 0, 1);
					//������Ϸ
					//ʵ��CCScene�����ڵ�
					CCScene scene = CCScene.node();
					//�������ؽ�ɫѡ��ͼ��
					scene.addChild(new StartGame(app));
					//���ݸ�������
					CCDirector.sharedDirector().replaceScene(scene);
				}else
				{
					//�����ɫѡ�����
					//ʵ��CCScene�����ڵ�
					CCScene scene = CCScene.node();
					//�������ؽ�ɫѡ��ͼ��
					scene.addChild(new RoleSelect(app));
					//���ݸ�������
					CCDirector.sharedDirector().replaceScene(scene);
				}
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
			}finally
			{
				cursor.close();
				//�ر����ݿ�
				db.close();
			}
		}
		
		//�ж��Ƿ������ذ�ť
		if(CGRect.containsPoint(RectReturn, touch))
		{
			PublicFun.btSound(app);
			db = new dbHelper(app);
			Cursor cursor = db.selectId("1");
			try
			{
				//�ж��Ƿ�������
				if(cursor.getCount() == 0)
				{
					
				}else
				{
					//ʵ��CCScene�����ڵ�
					CCScene scene = CCScene.node();
					//�������ؽ�ɫѡ��ͼ��
					scene.addChild(new RoleSelect(app));
					//���ݸ�������
					CCDirector.sharedDirector().replaceScene(scene);
				}
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
			}finally
			{
				cursor.close();
				//�ر����ݿ�
				db.close();
			}
		}
		
		return CCTouchDispatcher.kEventHandled;
	}
	
	//�����ȡ����
	public String RandomName(int sex)
	{
		//��
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
