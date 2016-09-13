package com.zzl.mmm.activity;

import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.utils.LogUtils;
import com.zzl.mmm.R;
import com.zzl.mmm.adapter.GsAdapter;
import com.zzl.mmm.utils.AutoUtils;
import com.zzl.mmm.utils.BaseActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
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
public class MainActivity extends BaseActivity {
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
        // 设置时候可以自动刷新
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
        RequestParams params = new RequestParams("");
        params.addQueryStringParameter("pNo",pNo+"");
        params.setCacheMaxAge(1000 * 60 * 60 * 24);
        Callback.Cancelable cancelable
                // 使用CacheCallback, xUtils将为该请求缓存数据.
                = x.http().get(params, new Callback.CacheCallback<String>() {

            private boolean hasError = false;
            private String result = null;

            @Override
            public boolean onCache(String result) {
                // 得到缓存数据, 缓存过期后不会进入这个方法.
                // 如果服务端没有返回过期时间, 参考params.setCacheMaxAge(maxAge)方法.
                //
                // * 客户端会根据服务端返回的 header 中 max-age 或 expires 来确定本地缓存是否给 onCache 方法.
                //   如果服务端没有返回 max-age 或 expires, 那么缓存将一直保存, 除非这里自己定义了返回false的
                //   逻辑, 那么xUtils将请求新数据, 来覆盖它.
                //
                // * 如果信任该缓存返回 true, 将不再请求网络;
                //   返回 false 继续请求网络, 但会在请求头中加上ETag, Last-Modified等信息,
                //   如果服务端返回304, 则表示数据没有更新, 不继续加载数据.
                //
                toast("缓存");
                this.result = result;
                return true; // true: 信任缓存数据, 不在发起网络请求; false不信任缓存数据.
            }

            @Override
            public void onSuccess(String result) {
                // 注意: 如果服务返回304 或 onCache 选择了信任缓存, 这时result为null.
                if (result != null) {
                    this.result = result;
                    toast("网络访问");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                hasError = true;
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

            @Override
            public void onFinished() {
                if (!hasError && result != null) {
                    // 成功获取数据
                    //Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                    showByResult(result);
                    if (pNo>=pageCount){
                        xrefresh.setLoadComplete(true);
                        xrefresh.setPullLoadEnable(false);
                    }else{
                        xrefresh.setLoadComplete(false);
                        xrefresh.setPullLoadEnable(true);
                    }
                }
            }
        });
        // cancelable.cancel(); // 取消请求
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
