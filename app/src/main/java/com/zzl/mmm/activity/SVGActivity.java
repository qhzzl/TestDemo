package com.zzl.mmm.activity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGBuilder;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zzl.mmm.R;
import com.zzl.mmm.utils.AutoUtils;
import com.zzl.mmm.utils.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

@ContentView(R.layout.activity_svg)
public class SVGActivity extends BaseActivity {
    @ViewInject(R.id.svg_img)
    private ImageView svg_img;
    @ViewInject(R.id.banner)
    private Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        AutoUtils.auto(this);
//        //svg-android第三方jar包
//        SVG svg = new SVGBuilder().readFromResource(getResources(), R.raw.back).build();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            svg_img.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
//        svg_img.setImageDrawable(svg.getDrawable());
        Log.i("tt",svg_img.getDrawable().getBounds().height()+"");

        String[] images= new String[] {"http://img.zcool.cn/community/0192a557a352ef0000012e7ed104e4.jpg"
                ,"http://img.zcool.cn/community/01ae5656e1427f6ac72531cb72bac5.jpg"
                ,"http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg"};
        //banner
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
//        banner.setDelayTime(2000);
//        banner.setImages(images);
        svg_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(SVGActivity.this)
                        .create();
                Window window = dialog.getWindow();
                //window.setContentView(R.layout.mydialog);
                //dialog.setView(view);//http://blog.csdn.net/acnt3w/article/details/7317285--区别（不用这个）
                window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
                window.setWindowAnimations(R.style.mystyle);  //添加动画
                dialog.show();
            }
        });
    }

}
