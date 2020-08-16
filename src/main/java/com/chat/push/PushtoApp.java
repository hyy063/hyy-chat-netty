package com.chat.push;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;

import java.io.IOException;




public class PushtoApp  {
    //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
    private static String appId = "CvSoDqgtu78k7hhk98LS34";
    private static String appKey = "oTBmJpz4B4ApqwTw12Psi9";
    private static String masterSecret = "dHqsh3TA4a5RWkNuLYann5";
    private static String host = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void send(String title, String text, String cid) throws IOException {
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        NotificationTemplate template = notificationTemplateDemo(title, text);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(cid);
        IPushResult ret = null;

        try {
            ret = push.pushMessageToSingle(message, target);
        }
        catch(RequestException e){
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null){
            System.out.println(ret.getResponse().toString());
        }else {
            System.out.println("服务端响应异常");
        }

    }

    public static NotificationTemplate notificationTemplateDemo(String title, String text) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(title);
        style.setText(text);
        // 配置通知栏图标
        style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);

        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(1);
        template.setTransmissionContent(text);
        return template;
    }
}
