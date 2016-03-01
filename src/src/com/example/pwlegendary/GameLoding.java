/*
 * ���ܣ�������
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
	
	//�����������������
	CCSprite gmLoding;
	//����Logo�������
	CCSprite gmLogo;
	Context app;
	dbHelper db = null;
	
	public GameLoding(Context context)
	{
		app = context;
		
		//ʵ���������������
		gmLoding = CCSprite.sprite("loding/loding_01.png");
		gmLogo = CCSprite.sprite("loding/logo.png");
		//��ȡ��Ļ��ȡ��߶�
		CGSize size = CCDirector.sharedDirector().winSize();
		//������λ��
		CGPoint lodingPoint = CGPoint.ccp(size.width/2, size.height/3);
		CGPoint logoPoint = CGPoint.ccp(size.width/2,size.height/2);
		//�趨λ��
		gmLoding.setPosition(lodingPoint);
		gmLogo.setPosition(logoPoint);
		//�����������������ͼ��
		this.addChild(gmLoding);
		this.addChild(gmLogo);
		
		//ʵ��CCAnimation����
		CCAnimation animation = CCAnimation.animation("gmLoding");
		//ѭ��ͼƬ
		for(int i = 1;i<11;i++)
		{
			animation.addFrame(String.format("loding/loding_%02d.png",i));
		}
		//ִ���ٶ�
		CCAnimate action = CCAnimate.action(1,animation,false);
		
		//�����ص�����
		CCCallFuncN callFuncN = CCCallFuncN.action(this, "onActionFinished");
		
		//���鶯������callFuncN����ִ�н��
		CCSequence sequence = CCSequence.actions(action, callFuncN);
		
		//����������ִ�ж���
		gmLoding.runAction(sequence);
	}
	
	//������ִ����Ϻ�����ɫ��¼����
	public void onActionFinished(Object sender)
	{
		db = new dbHelper(app);
		Cursor cursor = db.selectId("1");
		//�ж��Ƿ�������,��������ת��������ɫ��������������תѡ���ɫ����
		if(cursor.getCount() == 0)
		{
			//ʵ��CCScene�����ڵ�
			CCScene scene = CCScene.node();
			//�������ش�����ɫͼ��
			scene.addChild(new LoginRole(app));
			//���ݸ�������
			CCDirector.sharedDirector().replaceScene(scene);
		}else
		{
			//ʵ��CCScene�����ڵ�
			CCScene scene = CCScene.node();
			//�������ؽ�ɫѡ��ͼ��
			scene.addChild(new RoleSelect(app));
			//���ݸ�������
			CCDirector.sharedDirector().replaceScene(scene);
		}
		cursor.close();
		//�ر����ݿ�
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
