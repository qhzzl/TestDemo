package com.zzl.mmm.activity;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.utils.LogUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.zzl.mmm.R;
import com.zzl.mmm.adapter.GsAdapter;
import com.zzl.mmm.nohttp.CallServer;
import com.zzl.mmm.nohttp.HttpListener;
import com.zzl.mmm.utils.AutoUtils;
import com.zzl.mmm.utils.BaseActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_main)
public class Main2Activity extends BaseActivity {
    @ViewInject(R.id.xrefresh)
    private XRefreshView xrefresh;
    @ViewInject(R.id.listview)
    private ListView listview;
    private List<Map<String,String>> mlist=new ArrayList<Map<String, String>>();
    private GsAdapter adapter;

    private int pNo,pageCount;
    public static long lastRefreshTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AutoUtils.auto(this);

        pNo=1;
        getListInfo();
        // 设置是否可以下拉刷新
        xrefresh.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        xrefresh.setPullLoadEnable(true);
        // 设置上次刷新的时间
        xrefresh.restoreLastRefreshTime(lastRefreshTime);
        // 设置刚进入页面时view是否自动刷新
        xrefresh.setAutoRefresh(false);
        xrefresh.setAutoLoadMore(true);
        //XRefreshView不仅支持手势下拉刷新，也支持按钮点击开始刷新，
        //xrefresh.startRefresh();
        //如果刷新时不想让里面的列表滑动，可以这么设置
        //xrefresh.setPinnedContent(true);

        xrefresh.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh() {

                pNo=1;
                getListInfo();

            }

            @Override
            public void onLoadMore(boolean isSlience) {
                pNo+=1;
                getListInfo();
            }

            @Override
            public void onRelease(float direction) {
                super.onRelease(direction);
                if (direction > 0) {
                    toast("下拉");
                } else {
                    toast("上拉");
                }
            }
        });
        xrefresh.setOnAbsListViewScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                LogUtils.i("onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                LogUtils.i("onScroll");
            }
        });

    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void onLoad(){
        xrefresh.stopRefresh();
        lastRefreshTime = xrefresh.getLastRefreshTime();
        xrefresh.stopLoadMore();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void getListInfo(){
        Request<String> request = NoHttp.createStringRequest("", RequestMethod.GET);
        // 添加请求参数
        request.add("pNo", pNo + "");
        //request.setRetryCount(0);
        request.setCacheMode(CacheMode.NONE_CACHE_REQUEST_NETWORK);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                showByResult(response.get());
                if (pNo >= pageCount) {
                    xrefresh.setLoadComplete(true);
                    xrefresh.setPullLoadEnable(false);
                } else {
                    xrefresh.setLoadComplete(false);
                    xrefresh.setPullLoadEnable(true);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, CharSequence message, int responseCode, long networkMillis) {
                toast("错误"+message);
                LogUtil.e(""+message);


            }
        }, false, false);

    }

    //处理
    private void showByResult(String result){
        try {
            JSONObject jsonObject = new JSONObject(result);
            if ("1".equals(jsonObject.getString("state"))) {
                pageCount=jsonObject.getInt("pageCount");
                JSONArray array=jsonObject.getJSONArray("guShenList");
                if (pNo==1) {
                    mlist.clear();
                }
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject2=array.getJSONObject(i);
                    Map<String, String> map=new HashMap<String,String>();
                    map.put("nickName", jsonObject2.has("userName")?jsonObject2.getString("userName"):"");
                    map.put("isPlanner", jsonObject2.has("isPlanner")?jsonObject2.getString("isPlanner"):"0");
                    map.put("gender", jsonObject2.has("gender")?jsonObject2.getString("gender"):"");
                    map.put("tatalYeld", jsonObject2.has("totalYeld")?jsonObject2.getString("totalYeld"):"0.00");
                    map.put("id", jsonObject2.has("id")?jsonObject2.getString("id"):"");
                    map.put("img", jsonObject2.has("img")?jsonObject2.getString("img"):"");
                    mlist.add(map);
                }
                if (adapter==null) {
                    adapter=new GsAdapter(this, mlist);
                    listview.setAdapter(adapter);
                }else{
                    adapter.notifyDataSetChanged();
                }
            }else{
                if (pNo==1) {
                    mlist.clear();
                    pageCount=0;
                    Toast.makeText(this, jsonObject.getString("turnMsg"), Toast.LENGTH_SHORT).show();
                }
                if (adapter==null) {
                    adapter=new GsAdapter(this, mlist);
                    listview.setAdapter(adapter);
                }else{
                    adapter.notifyDataSetChanged();
                }

            }
            onLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
