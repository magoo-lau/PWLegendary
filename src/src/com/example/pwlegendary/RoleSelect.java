/*
 * ��ɫѡ����
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
	//��ť��ɫ
	CCSprite roleSelectBj;
	CCSprite createBt;
	CCSprite startBt;
	CCSprite deleteBt;
	//��ɫͷ��
	CCSprite roleImg;
	//��ɫ���Ʊ�ǩ
	CCLabel roleName;
	//�����ǩ
	CCLabel roleLev;
	//���ͱ�ǩ
	CCLabel roleType;
	//�洢�û�����ID���Ա𡢽�ɫ����
	static String rID,rName,rSex,rType,rLev;
	//ɾ��������
	CCSprite deleteMes;
	CCSprite mesClose;
	CCSprite mesConfirm;
	CCSprite mesConcel;
	dbHelper db = null;
	
	public RoleSelect(Context context)
	{
		//��ɫ����
		super(new ccColor4B(0, 0, 0, 0));
		app = context;
		//��ͼ�㿪�������¼�
		this.setIsTouchEnabled(true);
		
		//ʵ��ѡ���ɫ����ͼ�������
		roleSelectBj = CCSprite.sprite("role/selectrole.png");
		CGSize size = CCDirector.sharedDirector().winSize();
		CGPoint spritePoint = CGPoint.ccp(size.width/2, size.height/2);
		roleSelectBj.setPosition(spritePoint);
		
		db = new dbHelper(app);
		Cursor cursor = db.selectId("1");
		try
		{
			//�ж��Ƿ�������
			if(cursor.getCount() == 0)
			{
				System.out.println("������");
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
		//��Ϸ����
		roleName = CCLabel.makeLabel(rName, "gameName", 25);
		CGPoint bg =roleSelectBj.getPosition();
		roleName.setPosition(bg.x+10,bg.y+185);
		roleName.setAnchorPoint(0, 0);//�����
		//�ȼ�
		roleLev = CCLabel.makeLabel(rLev, "gameLev", 25);
		roleLev.setPosition(bg.x+10,bg.y+145);
		roleLev.setAnchorPoint(0, 0);//�����
		
		//�жϽ�ɫְҵ���Ա���ʾ��Ӧ��ͼƬ������
		switch(Integer.parseInt(rType))
		{
			case 1:
				rType = "սʿ";
				if(rSex.equals("1"))
				{
					//ͷ��
					roleImg = CCSprite.sprite("role/sznan.png");
					roleImg.setPosition(bg.x-225,bg.y+110);
					roleImg.setAnchorPoint(0, 0);//�����
				}else
				{
					roleImg = CCSprite.sprite("role/sznv.png");
					roleImg.setPosition(bg.x-225,bg.y+110);
					roleImg.setAnchorPoint(0, 0);//�����
				}
				break;
			case 2:
				rType = "��ʦ";
				if(rSex.equals("1"))
				{
					//ͷ��
					roleImg = CCSprite.sprite("role/sfnan.png");
					roleImg.setPosition(bg.x-225,bg.y+110);
					roleImg.setAnchorPoint(0, 0);//�����
				}else
				{
					roleImg = CCSprite.sprite("role/sfnv.png");
					roleImg.setPosition(bg.x-225,bg.y+110);
					roleImg.setAnchorPoint(0, 0);//�����
				}
				break;
			case 3:
				rType = "��ʿ";
				if(rSex.equals("1"))
				{
					//ͷ��
					roleImg = CCSprite.sprite("role/sdnan.png");
					roleImg.setPosition(bg.x-225,bg.y+110);
					roleImg.setAnchorPoint(0, 0);//�����
				}else
				{
					roleImg = CCSprite.sprite("role/sdnv.png");
					roleImg.setPosition(bg.x-225,bg.y+110);
					roleImg.setAnchorPoint(0, 0);//�����
				}
				break;
			default:
				System.out.println("δ֪");
				break;
		}
		//ְҵ
		roleType = CCLabel.makeLabel(rType, "gameType", 25);
		roleType.setPosition(bg.x+10,bg.y+105);
		roleType.setAnchorPoint(0, 0);//�����
		
		//ʵ��������ɫ��ť
		createBt = CCSprite.sprite("role/createrole.png");
		//����λ��
		CGPoint cPoint = CGPoint.ccp(size.width/2-180, size.height/2-270);
		//����λ��
		createBt.setPosition(cPoint);
		
		//ʵ����ʼ��Ϸ��ť
		startBt = CCSprite.sprite("role/startgame.png");
		//����λ��
		CGPoint sPoint = CGPoint.ccp(size.width/2, size.height/2-280);
		//����λ��
		startBt.setPosition(sPoint);
		
		//ʵ����ʼ��Ϸ��ť
		deleteBt = CCSprite.sprite("role/deleterole.png");
		//����λ��
		CGPoint dPoint = CGPoint.ccp(size.width/2+180, size.height/2-270);
		//����λ��
		deleteBt.setPosition(dPoint);
		
		//ɾ��������
		deleteMes = CCSprite.sprite("role/delmes.png");
		CGPoint mPoint = CGPoint.ccp(size.width/2, size.height/2);
		deleteMes.setPosition(mPoint);
		deleteMes.setVisible(false);
		//������ر�
		mesClose = CCSprite.sprite("role/mesclose.png");
		CGPoint xPoint = CGPoint.ccp(size.width/2+200, size.height/2+95);
		mesClose.setPosition(xPoint);
		mesClose.setVisible(false);
		//ȷ����ť
		mesConfirm = CCSprite.sprite("role/mesconfirm.png");
		CGPoint ccPoint = CGPoint.ccp(size.width/2-95, size.height/2-80);
		mesConfirm.setPosition(ccPoint);
		mesConfirm.setVisible(false);
		//ȡ����ť
		mesConcel = CCSprite.sprite("role/mesconcel.png");
		CGPoint czPoint = CGPoint.ccp(size.width/2+88, size.height/2-80);
		mesConcel.setPosition(czPoint);
		mesConcel.setVisible(false);
		
		//�����������ͼ��
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
	
	//����
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		//��ȡ��������
		CGPoint touch = CGPoint.ccp(event.getX(), event.getY());
		//ת������ϵ
		touch = CCDirector.sharedDirector().convertToGL(touch);
		//��ȡ������ɫ��ť
		CGRect cbt = createBt.getBoundingBox();
		//��ȡ��ʼ��Ϸ��ť
		CGRect sbt = startBt.getBoundingBox();
		//��ȡɾ����ɫ��ť
		CGRect dbt = deleteBt.getBoundingBox();
		//��ȡ������رհ�ť
		CGRect mesclose = mesClose.getBoundingBox();
		//��ȡȡ����ť
		CGRect mesconcel = mesConcel.getBoundingBox();
		//��ȡȷ����ť
		CGRect mesconfirm = mesConfirm.getBoundingBox();
		//�ر��¼�
		if(CGRect.containsPoint(mesclose, touch))
		{
			mesHide();
		}
		if(CGRect.containsPoint(mesconcel, touch))
		{
			mesHide();
		}
		//ȷ��ɾ����ɫ
		if(CGRect.containsPoint(mesconfirm, touch))
		{
			//ֻ�н�ɫ��ʾʱ�ſ��Դ����¼�
			if(deleteMes.getVisible())
			{
				//ɾ����ɫ
				db = new dbHelper(app);
				db.clearFeedTable();
				db.close();
				//ʵ��CCScene�����ڵ�
				CCScene scene = CCScene.node();
				//�������ؽ�ɫѡ��ͼ��
				scene.addChild(new LoginRole(app));
				//���ݸ�������
				CCDirector.sharedDirector().replaceScene(scene);
			}
			//���ص�����
			mesHide();
		}
		//���������ť
		if(CGRect.containsPoint(cbt, touch))
		{
			PublicFun.btSound(app);
			db = new dbHelper(app);
			Cursor cursor = db.selectId("1");
			try
			{
				//�ж��Ƿ�������
				if(cursor.getCount() == 0)
				{
					//ʵ��CCScene�����ڵ�
					CCScene scene = CCScene.node();
					//�������ؽ�ɫѡ��ͼ��
					scene.addChild(new LoginRole(app));
					//���ݸ�������
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
		//�����ʼ��Ϸ��ť
		if(CGRect.containsPoint(sbt, touch))
		{
			try
			{
				//ʵ��CCScene�����ڵ�
				CCScene scene = CCScene.node();
				//�������ؽ�ɫѡ��ͼ��
				scene.addChild(new StartGame(app));
				//���ݸ�������
				CCDirector.sharedDirector().replaceScene(scene);
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			
		}
		
		//���ɾ����ɫ��ť
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
	
	//���ص�����
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
