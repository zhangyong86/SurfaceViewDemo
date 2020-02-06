package com.example.surfaceviewtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 一直动的动画使用SurfaceView(子线程更新)
 * 触摸动画使用普通View
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;//surface和客户端的桥梁
    private MyThread myThread;//子线程

    public MySurfaceView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        holder = this.getHolder();
        holder.addCallback(this);
        myThread = new MyThread(holder);//创建一个绘图线程
    }

    /**
     * surface大小发生变化
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
    }

    /**
     * surface创建，一般在这里调用画图线程
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        myThread.isRun = true;
        myThread.start();
    }

    /**
     * surface销毁，画图线程的停止、释放
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        myThread.isRun = false;
    }
}

//线程内部类
class MyThread extends Thread {
    private SurfaceHolder holder;
    public boolean isRun;

    public MyThread(SurfaceHolder holder) {
        this.holder = holder;
        isRun = true;
    }

    /**
     * 通过holder.lockCanvas锁定视图获取Canvas(Start editing the pixels in the surface)
     * 通过Canvas绘制内容
     * 最后通过holder.unlockCanvasAndPost(c)释放锁视图(Finish editing pixels in the surface)
     */
    @Override
    public void run() {
        int count = 0;
        while (isRun) {
            Canvas c = null;
            try {
                synchronized (holder) {
                    c = holder.lockCanvas();//锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作了。

                    //使用Canvas绘制内容
                    c.drawColor(Color.BLACK);//设置画布背景颜色
                    Paint p = new Paint(); //创建画笔
                    p.setColor(Color.WHITE);
                    Rect r = new Rect(100, 50, 300, 250);
                    c.drawRect(r, p);//矩形
                    c.drawText("这是第" + (count++) + "秒", 100, 310, p);//文本
                    Thread.sleep(1000);//睡眠时间为1秒
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            } finally {
                if (c != null) {
                    holder.unlockCanvasAndPost(c);//结束锁定画图，并提交改变。
                }
            }
        }
    }
}
