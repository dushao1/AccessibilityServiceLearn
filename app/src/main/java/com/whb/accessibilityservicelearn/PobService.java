package com.whb.accessibilityservicelearn;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.math.MathUtils;

import java.util.List;

/**
 * @author WangHongBin ^_^
 * @date 2020/6/8 20:07
 **/
public class PobService extends AccessibilityService {

    private final String TAG = PobService.class.getSimpleName();

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d("RobService", "onAccessibility" + "建立连接");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            AccessibilityButtonController mAccessibilityButtonController = getAccessibilityButtonController();
//            mAccessibilityButtonController.registerAccessibilityButtonCallback(new AccessibilityButtonController.AccessibilityButtonCallback() {
//                @Override
//                public void onClicked(AccessibilityButtonController controller) {
//                    super.onClicked(controller);
//                    // 底部导航栏中辅助功能按钮点击事件回调
//                    Toast.makeText(RobService.this, "on Click", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onAvailabilityChanged(AccessibilityButtonController controller, boolean available) {
//                    super.onAvailabilityChanged(controller, available);
//                    // 辅助功能可用性改变的回调。返回了辅助功能底部按钮是否可用的布尔值，和按钮控制器
//                    // available = true 表示该按钮对本服务可用
//                    // available = false 是由于设备显示了按钮，或按钮被分配到另一个服务或其他原因。
//                }
//            });
//        }

        Log.d("RobService", "onAccessibilityEvent:event:" + event.getAction());
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                handleNotification(event);
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                String className = event.getClassName().toString();
                Log.d("RobService", "source ===" + event.getSource());
                Log.d(TAG, "className=========" + className);
                String clazz = "cn.kuwo.player.activities.MainActivity";
                AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    List<AccessibilityNodeInfo> list = rootInActiveWindow.findAccessibilityNodeInfosByViewId("cn.kuwo.player:id/recycler_view");
                    if (list !=null) {
                        Log.d("RobService", "list长度:" + list.size());
                    }

                    if (list != null && list.size() >0 && System.currentTimeMillis() % 6000 <1000) {
                        Log.d("RobService", "时间:" + 6000);
                        for (AccessibilityNodeInfo accessibilityNodeInfo : list) {

//                            argument.
//                            boolean b = accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD,argument);
//                            boolean b = accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
//                            if(accessibilityNodeInfo.isScrollable()){
//                                boolean b = accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD,argument);
////                                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
//                            }

                            boolean b = scrollView(accessibilityNodeInfo);
                            Log.d("RobService","触控结果:" + b);
//                            if(b){
//                                try {
//                                    Log.d("RobService","触控结果:true 休眠开始" );
//
//                                Thread.sleep(5000);
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
                        }

                    }
//                    List<AccessibilityNodeInfo> music = rootInActiveWindow.findAccessibilityNodeInfosByText("音乐片段");
//                    if (music != null && music.size() > 0) {
//                        AccessibilityNodeInfo accessibilityNodeInfo = music.get(0);
//                        accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                        try {
//                            Thread.sleep(5000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        List<AccessibilityNodeInfo> list = rootInActiveWindow.findAccessibilityNodeInfosByViewId("@android:id/list");
//                        if (list != null) {
//                            AccessibilityNodeInfo accessibilityNodeInfo = list.get(0);
//                            accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
//                        }
//                    }
                }

//                if (className.equals("com.tencent.mm.ui.LauncherUI")) {
//                    Log.d("RobService", "classname---------------------" + className);
//                    getPacket();
//                } else if (className.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyNotHookReceiveUI")) {
//                    Log.d("RobService", "classname---------------------" + className);
//                    openPacket();
//                } else if (className.equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI")) {
//                    Log.d("RobService", "classname---------------------" + className);
//                    close();
//                }

                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean scrollView(AccessibilityNodeInfo nodeInfo) {

//        if (nodeInfo == null) return false;
//
//        if (nodeInfo.isScrollable()) {
//            nodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
////            return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
//        }
//
//        for (int i = 0; i < nodeInfo.getChildCount(); i++) {
//            if (scrollView(nodeInfo.getChild(i)) ){
//                return true;
//            }
//        }
//
//        return false;
        final boolean[] result = {false};
        if (nodeInfo.isScrollable()) {
            Path mPath = new Path();
            mPath.moveTo(500, 2029);//滑动的起始点
            mPath.lineTo(500, 200);//滑动终点。不指定lineTo的坐标，只配置moveTo坐标时执行点击动作，点击位置为moveTo指定的坐标。
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Log.d("RobService", "模拟手势"  );
                dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription
                                        (mPath, 100, 500)).build(), new GestureResultCallback() {
                    @Override
                    public void onCompleted(GestureDescription gestureDescription) {
                        super.onCompleted(gestureDescription);
                        System.out.println("模拟手势成功");
                        result[0] = true;

                    }

                    @Override
                    public void onCancelled(GestureDescription gestureDescription) {
                        super.onCancelled(gestureDescription);
                        System.out.println("模拟手势失败");
                        result[0] = false;
                    }
                }, null);
            } else{
                Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
            }
        }
        return result[0];
    }

    /**
     * 处理通知栏信息
     * <p>
     * 如果是微信红包的提示信息,则模拟点击
     *
     * @param event
     */
    private void handleNotification(AccessibilityEvent event) {
        List<CharSequence> texts = event.getText();
        if (!texts.isEmpty()) {
            for (CharSequence text : texts) {
                String content = text.toString();
                //如果微信红包的提示信息,则模拟点击进入相应的聊天窗口
                if (content.contains("[微信红包]")) {
                    if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification) {
                        Notification notification = (Notification) event.getParcelableData();
                        PendingIntent pendingIntent = notification.contentIntent;
                        try {
                            pendingIntent.send();
                        } catch (PendingIntent.CanceledException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 关闭红包详情界面,实现自动返回聊天窗口
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void close() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            //为了演示,直接查看了关闭按钮的id
            List<AccessibilityNodeInfo> infos = nodeInfo.findAccessibilityNodeInfosByViewId("@id/ez");
            nodeInfo.recycle();
            for (AccessibilityNodeInfo item : infos) {
                item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    /**
     * 模拟点击,拆开红包
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void openPacket() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            //为了演示,直接查看了红包控件的id
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("@id/b9m");
            nodeInfo.recycle();
            for (AccessibilityNodeInfo item : list) {
                item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    /**
     * 模拟点击,打开抢红包界面
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void getPacket() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        AccessibilityNodeInfo node = recycle(rootNode);

        node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        AccessibilityNodeInfo parent = node.getParent();
        while (parent != null) {
            if (parent.isClickable()) {
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                break;
            }
            parent = parent.getParent();
        }

    }

    /**
     * 递归查找当前聊天窗口中的红包信息
     * <p>
     * 聊天窗口中的红包都存在"领取红包"一词,因此可根据该词查找红包
     *
     * @param node
     */
    public AccessibilityNodeInfo recycle(AccessibilityNodeInfo node) {
        if (node.getChildCount() == 0) {
            if (node.getText() != null) {
                if ("我的作品".equals(node.getText().toString())) {
                    return node;
                }
            }
        } else {
            for (int i = 0; i < node.getChildCount(); i++) {
                if (node.getChild(i) != null) {
                    recycle(node.getChild(i));
                }
            }
        }
        return node;
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
    }
}
