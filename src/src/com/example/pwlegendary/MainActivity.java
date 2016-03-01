/*
 * ���ܣ��������
 * */
package com.example.pwlegendary;
//www.javaapk.com
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import android.os.Bundle;
import android.view.WindowManager;
import android.app.Activity;
import android.content.SharedPreferences;

public class MainActivity extends Activity {

	//����CCGLSurfaceView
	private CCGLSurfaceView surfaceView = null;
	dbHelper db = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//����ȫ��
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //��Ļ�������ر�
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//ʵ��CCGLSurfaceViewView
		surfaceView = new CCGLSurfaceView(this);
		//ʵ�����ݶ���
		CCDirector director = CCDirector.sharedDirector();
		//���õ�ǰ����
		director.attachInView(surfaceView);
		//�Ƿ���ʾFPSֵ
		director.setDisplayFPS(true);
		//ÿ֡��Ⱦ
		director.setAnimationInterval(1.0f / 30);
		//��������
		CCScene scene = CCScene.node();
		//��������
		GameLoding game = new GameLoding(this);
		//���������������
		scene.addChild(game);
		//����ִ�г���
		director.runWithScene(scene);
		//����View
		setContentView(surfaceView);
		
		//�ж��û��Ƿ��һ��ʹ��
        SharedPreferences pref = this.getSharedPreferences("myFirstLogin", 0);
        Boolean isFristIn = pref.getBoolean("FIRST",true);
        if(isFristIn)
        {
        	pref.edit().putBoolean("FIRST", false).commit();
        	db = new dbHelper(MainActivity.this);
        	db.openDatabase();
        	db.close();
        }else
        {
        	pref.edit().putBoolean("FIRST", false).commit();
        }
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(db != null)
		{
			db.close();
		}
		//�����˳�
		CCDirector.sharedDirector().end();
	}
}
