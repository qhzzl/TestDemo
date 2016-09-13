package com.zzl.mmm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zzl.mmm.R;
import com.zzl.mmm.utils.AutoUtils;
import com.zzl.mmm.utils.BaseActivity;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/6/24.
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity{
    @ViewInject(R.id.phone)
    private EditText phone;
    @ViewInject(R.id.pwd)
    private EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AutoUtils.setSize(this, true, 720, 1280);
        AutoUtils.auto(this);
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.enable();

    }
    @Event(value = R.id.login)
    private void loginByPhone(View view){
        //get的post的请求基本一致，也就添加参数的方法不同而已。
        // 调用get方法，添加参数是用addQueryStringParameter。
        // 调用post方法，添加参数是用addBodyParameter。
        RequestParams params = new RequestParams("");
        //params.setSslSocketFactory(...); // 设置ssl安全证书
        //params.addQueryStringParameter("wd", "xUtils");//get参数
        // 设置要传给后台的json字符串
        //params.setBodyContent(paramJsonObject.toString());
        params.addBodyParameter("phone", phone.getText().toString());
        params.addBodyParameter("pwd", pwd.getText().toString());
        Callback.Cancelable cancelable=x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Person person=new Person("长江",18);
//                new PersonDB().savePerson(person);
//                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
//                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                //finish();
//                startActivity(new Intent(LoginActivity.this,Main2Activity.class));
                startActivity(new Intent(LoginActivity.this,SVGActivity.class));
//                startActivity(new Intent(LoginActivity.this,SwipeMenuActivity.class));
//                startActivity(new Intent(LoginActivity.this,WebViewActivity.class));
            }

            // 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
            // 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                if (ex instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    // ...
                } else { // 其他错误
                    // ...
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            // 不管成功或者失败最后都会回调该接口
            @Override
            public void onFinished() {

            }
        });
        //cancelable.cancel();//取消请求
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
