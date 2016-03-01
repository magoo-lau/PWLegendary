/*
 * 功能：程序入口
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

	//声明CCGLSurfaceView
	private CCGLSurfaceView surfaceView = null;
	dbHelper db = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//界面全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //屏幕不暗不关闭
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//实例CCGLSurfaceViewView
		surfaceView = new CCGLSurfaceView(this);
		//实例导演对象
		CCDirector director = CCDirector.sharedDirector();
		//设置当前对象
		director.attachInView(surfaceView);
		//是否显示FPS值
		director.setDisplayFPS(true);
		//每帧渲染
		director.setAnimationInterval(1.0f / 30);
		//创建场景
		CCScene scene = CCScene.node();
		//创建布景
		GameLoding game = new GameLoding(this);
		//将布景添加至场景
		scene.addChild(game);
		//导演执行场景
		director.runWithScene(scene);
		//加载View
		setContentView(surfaceView);
		
		//判断用户是否第一次使用
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
		//导演退场
		CCDirector.sharedDirector().end();
	}
}
