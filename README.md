# 悬浮窗体实现

<p>突然对悬浮窗体感兴趣，查资料做了个小Demo，效果是<span style="color: #0000ff;">点击按钮后，关闭当前Activity，显示悬浮窗口，窗口可以拖动，双击后消失。</span>效果图如下：</p>
<img alt="" src="http://images.cnitblog.com/blog/359646/201502/091217441838541.gif">
<p>它的使用原理很简单，就是借用了WindowManager这个管理类来实现的。<br />1.首先在AndroidManifest.xml中添加使用权限：</p>
<div class="cnblogs_code">
<pre><span style="color: #0000ff;">&lt;</span><span style="color: #800000;">uses-permission </span><span style="color: #ff0000;">android:name</span><span style="color: #0000ff;">="android.permission.SYSTEM_ALERT_WINDOW"</span> <span style="color: #0000ff;">/&gt;</span></pre>
</div>
<p>2.悬浮窗口布局实现</p>
<div class="cnblogs_code">
<pre><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">class</span> DesktopLayout <span style="color: #0000ff;">extends</span><span style="color: #000000;"> LinearLayout {

    </span><span style="color: #0000ff;">public</span><span style="color: #000000;"> DesktopLayout(Context context) {
        </span><span style="color: #0000ff;">super</span><span style="color: #000000;">(context);
        setOrientation(LinearLayout.VERTICAL);</span><span style="color: #008000;">//</span><span style="color: #008000;"> 水平排列
        

        </span><span style="color: #008000;">//</span><span style="color: #008000;">设置宽高</span>
        <span style="color: #0000ff;">this</span>.setLayoutParams( <span style="color: #0000ff;">new</span><span style="color: #000000;"> LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        
        View view </span>=<span style="color: #000000;"> LayoutInflater.from(context).inflate(  
                R.layout.desklayout, </span><span style="color: #0000ff;">null</span><span style="color: #000000;">); 
        </span><span style="color: #0000ff;">this</span><span style="color: #000000;">.addView(view);
    }</span></pre>
</div>
<p>3.在activity中让它显示出来。</p>
<div class="cnblogs_code">
<pre>        <span style="color: #008000;">//</span><span style="color: #008000;"> 取得系统窗体</span>
        mWindowManager =<span style="color: #000000;"> (WindowManager) getApplicationContext()
                .getSystemService(</span>"window"<span style="color: #000000;">);

        </span><span style="color: #008000;">//</span><span style="color: #008000;"> 窗体的布局样式</span>
        mLayout = <span style="color: #0000ff;">new</span><span style="color: #000000;"> WindowManager.LayoutParams();

        </span><span style="color: #008000;">//</span><span style="color: #008000;"> 设置窗体显示类型&mdash;&mdash;TYPE_SYSTEM_ALERT(系统提示)</span>
        mLayout.type =<span style="color: #000000;"> WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        </span><span style="color: #008000;">//</span><span style="color: #008000;"> 设置窗体焦点及触摸：
        </span><span style="color: #008000;">//</span><span style="color: #008000;"> FLAG_NOT_FOCUSABLE(不能获得按键输入焦点)</span>
        mLayout.flags =<span style="color: #000000;"> WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        </span><span style="color: #008000;">//</span><span style="color: #008000;"> 设置显示的模式</span>
        mLayout.format =<span style="color: #000000;"> PixelFormat.RGBA_8888;

        </span><span style="color: #008000;">//</span><span style="color: #008000;"> 设置对齐的方法</span>
        mLayout.gravity = Gravity.TOP |<span style="color: #000000;"> Gravity.LEFT;

        </span><span style="color: #008000;">//</span><span style="color: #008000;"> 设置窗体宽度和高度</span>
        mLayout.width =<span style="color: #000000;"> WindowManager.LayoutParams.WRAP_CONTENT;
        mLayout.height </span>= WindowManager.LayoutParams.WRAP_CONTENT;</pre>
</div>
<p>详细 MainActivity 代码如下：</p>
<div class="cnblogs_code" onclick="cnblogs_code_show('d221eb6f-f914-4b2e-b1b3-746082e22b2b')"><img id="code_img_closed_d221eb6f-f914-4b2e-b1b3-746082e22b2b" class="code_img_closed" src="http://images.cnblogs.com/OutliningIndicators/ContractedBlock.gif" alt="" /><img id="code_img_opened_d221eb6f-f914-4b2e-b1b3-746082e22b2b" class="code_img_opened" style="display: none;" onclick="cnblogs_code_hide('d221eb6f-f914-4b2e-b1b3-746082e22b2b',event)" src="http://images.cnblogs.com/OutliningIndicators/ExpandedBlockStart.gif" alt="" />
<div id="cnblogs_code_open_d221eb6f-f914-4b2e-b1b3-746082e22b2b" class="cnblogs_code_hide">
<pre><span style="color: #0000ff;">package</span><span style="color: #000000;"> com.yc.yc_suspendingform;

</span><span style="color: #0000ff;">import</span><span style="color: #000000;"> android.app.Activity;
</span><span style="color: #0000ff;">import</span><span style="color: #000000;"> android.graphics.PixelFormat;
</span><span style="color: #0000ff;">import</span><span style="color: #000000;"> android.graphics.Rect;
</span><span style="color: #0000ff;">import</span><span style="color: #000000;"> android.os.Bundle;
</span><span style="color: #0000ff;">import</span><span style="color: #000000;"> android.util.Log;
</span><span style="color: #0000ff;">import</span><span style="color: #000000;"> android.view.Gravity;
</span><span style="color: #0000ff;">import</span><span style="color: #000000;"> android.view.MotionEvent;
</span><span style="color: #0000ff;">import</span><span style="color: #000000;"> android.view.View;
</span><span style="color: #0000ff;">import</span><span style="color: #000000;"> android.view.View.OnClickListener;
</span><span style="color: #0000ff;">import</span><span style="color: #000000;"> android.view.View.OnTouchListener;
</span><span style="color: #0000ff;">import</span><span style="color: #000000;"> android.view.WindowManager;
</span><span style="color: #0000ff;">import</span><span style="color: #000000;"> android.widget.Button;
</span><span style="color: #0000ff;">import</span><span style="color: #000000;"> com.yc.yc_floatingform.R;

</span><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">class</span> MainActivity <span style="color: #0000ff;">extends</span><span style="color: #000000;"> Activity {
    </span><span style="color: #0000ff;">private</span><span style="color: #000000;"> WindowManager mWindowManager;
    </span><span style="color: #0000ff;">private</span><span style="color: #000000;"> WindowManager.LayoutParams mLayout;
    </span><span style="color: #0000ff;">private</span><span style="color: #000000;"> DesktopLayout mDesktopLayout;
    </span><span style="color: #0000ff;">private</span> <span style="color: #0000ff;">long</span><span style="color: #000000;"> startTime;
    </span><span style="color: #008000;">//</span><span style="color: #008000;"> 声明屏幕的宽高</span>
    <span style="color: #0000ff;">float</span><span style="color: #000000;"> x, y;
    </span><span style="color: #0000ff;">int</span><span style="color: #000000;"> top;

    @Override
    </span><span style="color: #0000ff;">protected</span> <span style="color: #0000ff;">void</span><span style="color: #000000;"> onCreate(Bundle savedInstanceState) {
        </span><span style="color: #0000ff;">super</span><span style="color: #000000;">.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);        
        createWindowManager();
        createDesktopLayout();
        Button btn </span>=<span style="color: #000000;"> (Button) findViewById(R.id.btn);
        btn.setOnClickListener(</span><span style="color: #0000ff;">new</span><span style="color: #000000;"> OnClickListener() {
            </span><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">void</span><span style="color: #000000;"> onClick(View v) {
                showDesk();
            }
        });
    }
    </span><span style="color: #008000;">/**</span><span style="color: #008000;">
     * 创建悬浮窗体
     </span><span style="color: #008000;">*/</span>
    <span style="color: #0000ff;">private</span> <span style="color: #0000ff;">void</span><span style="color: #000000;"> createDesktopLayout() {
        mDesktopLayout </span>= <span style="color: #0000ff;">new</span> DesktopLayout(<span style="color: #0000ff;">this</span><span style="color: #000000;">);
        mDesktopLayout.setOnTouchListener(</span><span style="color: #0000ff;">new</span><span style="color: #000000;"> OnTouchListener() {
            </span><span style="color: #0000ff;">float</span><span style="color: #000000;"> mTouchStartX;
            </span><span style="color: #0000ff;">float</span><span style="color: #000000;"> mTouchStartY;

            @Override
            </span><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">boolean</span><span style="color: #000000;"> onTouch(View v, MotionEvent event) {
                </span><span style="color: #008000;">//</span><span style="color: #008000;"> 获取相对屏幕的坐标，即以屏幕左上角为原点</span>
                x =<span style="color: #000000;"> event.getRawX();
                y </span>= event.getRawY() - top; <span style="color: #008000;">//</span><span style="color: #008000;"> 25是系统状态栏的高度</span>
                Log.i("startP", "startX" + mTouchStartX + "====startY"
                        +<span style="color: #000000;"> mTouchStartY);
                </span><span style="color: #0000ff;">switch</span><span style="color: #000000;"> (event.getAction()) {
                </span><span style="color: #0000ff;">case</span><span style="color: #000000;"> MotionEvent.ACTION_DOWN:
                    </span><span style="color: #008000;">//</span><span style="color: #008000;"> 获取相对View的坐标，即以此View左上角为原点</span>
                    mTouchStartX =<span style="color: #000000;"> event.getX();
                    mTouchStartY </span>=<span style="color: #000000;"> event.getY();
                    Log.i(</span>"startP", "startX" + mTouchStartX + "====startY"
                            +<span style="color: #000000;"> mTouchStartY);
                    </span><span style="color: #0000ff;">long</span> end = System.currentTimeMillis() -<span style="color: #000000;"> startTime;
                    </span><span style="color: #008000;">//</span><span style="color: #008000;"> 双击的间隔在 300ms以下</span>
                    <span style="color: #0000ff;">if</span> (end &lt; 300<span style="color: #000000;">) {
                        closeDesk();
                    }
                    startTime </span>=<span style="color: #000000;"> System.currentTimeMillis();
                    </span><span style="color: #0000ff;">break</span><span style="color: #000000;">;
                </span><span style="color: #0000ff;">case</span><span style="color: #000000;"> MotionEvent.ACTION_MOVE:
                    </span><span style="color: #008000;">//</span><span style="color: #008000;"> 更新浮动窗口位置参数</span>
                    mLayout.x = (<span style="color: #0000ff;">int</span>) (x -<span style="color: #000000;"> mTouchStartX);
                    mLayout.y </span>= (<span style="color: #0000ff;">int</span>) (y -<span style="color: #000000;"> mTouchStartY);
                    mWindowManager.updateViewLayout(v, mLayout);
                    </span><span style="color: #0000ff;">break</span><span style="color: #000000;">;
                </span><span style="color: #0000ff;">case</span><span style="color: #000000;"> MotionEvent.ACTION_UP:

                    </span><span style="color: #008000;">//</span><span style="color: #008000;"> 更新浮动窗口位置参数</span>
                    mLayout.x = (<span style="color: #0000ff;">int</span>) (x -<span style="color: #000000;"> mTouchStartX);
                    mLayout.y </span>= (<span style="color: #0000ff;">int</span>) (y -<span style="color: #000000;"> mTouchStartY);
                    mWindowManager.updateViewLayout(v, mLayout);

                    </span><span style="color: #008000;">//</span><span style="color: #008000;"> 可以在此记录最后一次的位置</span>
<span style="color: #000000;">
                    mTouchStartX </span>= mTouchStartY = 0<span style="color: #000000;">;
                    </span><span style="color: #0000ff;">break</span><span style="color: #000000;">;
                }
                </span><span style="color: #0000ff;">return</span> <span style="color: #0000ff;">true</span><span style="color: #000000;">;
            }
        });
    }

    @Override
    </span><span style="color: #0000ff;">public</span> <span style="color: #0000ff;">void</span> onWindowFocusChanged(<span style="color: #0000ff;">boolean</span><span style="color: #000000;"> hasFocus) {
        </span><span style="color: #0000ff;">super</span><span style="color: #000000;">.onWindowFocusChanged(hasFocus);
        Rect rect </span>= <span style="color: #0000ff;">new</span><span style="color: #000000;"> Rect();
        </span><span style="color: #008000;">//</span><span style="color: #008000;"> /取得整个视图部分,注意，如果你要设置标题样式，这个必须出现在标题样式之后，否则会出错</span>
<span style="color: #000000;">        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        top </span>= rect.top;<span style="color: #008000;">//</span><span style="color: #008000;">状态栏的高度，所以rect.height,rect.width分别是系统的高度的宽度</span>
<span style="color: #000000;">
        Log.i(</span>"top",""+<span style="color: #000000;">top);
    }

    </span><span style="color: #008000;">/**</span><span style="color: #008000;">
     * 显示DesktopLayout
     </span><span style="color: #008000;">*/</span>
    <span style="color: #0000ff;">private</span> <span style="color: #0000ff;">void</span><span style="color: #000000;"> showDesk() {
        mWindowManager.addView(mDesktopLayout, mLayout);
        finish();
    }

    </span><span style="color: #008000;">/**</span><span style="color: #008000;">
     * 关闭DesktopLayout
     </span><span style="color: #008000;">*/</span>
    <span style="color: #0000ff;">private</span> <span style="color: #0000ff;">void</span><span style="color: #000000;"> closeDesk() {
        mWindowManager.removeView(mDesktopLayout);
        finish();
    }

    </span><span style="color: #008000;">/**</span><span style="color: #008000;">
     * 设置WindowManager
     </span><span style="color: #008000;">*/</span>
    <span style="color: #0000ff;">private</span> <span style="color: #0000ff;">void</span><span style="color: #000000;"> createWindowManager() {
        </span><span style="color: #008000;">//</span><span style="color: #008000;"> 取得系统窗体</span>
        mWindowManager =<span style="color: #000000;"> (WindowManager) getApplicationContext()
                .getSystemService(</span>"window"<span style="color: #000000;">);

        </span><span style="color: #008000;">//</span><span style="color: #008000;"> 窗体的布局样式</span>
        mLayout = <span style="color: #0000ff;">new</span><span style="color: #000000;"> WindowManager.LayoutParams();

        </span><span style="color: #008000;">//</span><span style="color: #008000;"> 设置窗体显示类型&mdash;&mdash;TYPE_SYSTEM_ALERT(系统提示)</span>
        mLayout.type =<span style="color: #000000;"> WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        </span><span style="color: #008000;">//</span><span style="color: #008000;"> 设置窗体焦点及触摸：
        </span><span style="color: #008000;">//</span><span style="color: #008000;"> FLAG_NOT_FOCUSABLE(不能获得按键输入焦点)</span>
        mLayout.flags =<span style="color: #000000;"> WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        </span><span style="color: #008000;">//</span><span style="color: #008000;"> 设置显示的模式</span>
        mLayout.format =<span style="color: #000000;"> PixelFormat.RGBA_8888;

        </span><span style="color: #008000;">//</span><span style="color: #008000;"> 设置对齐的方法</span>
        mLayout.gravity = Gravity.TOP |<span style="color: #000000;"> Gravity.LEFT;

        </span><span style="color: #008000;">//</span><span style="color: #008000;"> 设置窗体宽度和高度</span>
        mLayout.width =<span style="color: #000000;"> WindowManager.LayoutParams.WRAP_CONTENT;
        mLayout.height </span>=<span style="color: #000000;"> WindowManager.LayoutParams.WRAP_CONTENT;

    }

}</span></pre>
</div>
