package com.zzl.mmm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzl.mmm.R;
import com.zzl.mmm.utils.ApplicationUtil;
import com.zzl.mmm.utils.AutoUtils;
import com.zzl.mmm.utils.HttpUtils;

import org.xutils.x;

import java.util.List;
import java.util.Map;

/**
 * ����
 * @author Administrator
 */
@SuppressLint("InflateParams") 
public class GsAdapter extends BaseAdapter {
	private Context context;
	List<Map<String, String>> mlist;

	public GsAdapter(Context context) {
		this.context = context;
	}

	public GsAdapter(Context context, List<Map<String, String>> mlist) {
		this.context = context;
		this.mlist = mlist;
	}

	@Override
	public int getCount() {
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder = null;
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(
					R.layout.listviewc_item, null);
			AutoUtils.auto(arg1);
			holder.headimg_gs = (ImageView) arg1.findViewById(R.id.headimg_gs);
			holder.sex_gs = (ImageView) arg1.findViewById(R.id.sex_gs);
			holder.nickname_gs = (TextView) arg1.findViewById(R.id.nickname_gs);
			holder.label_gs = (TextView) arg1.findViewById(R.id.label_gs);
			holder.shouyilv_gs = (TextView) arg1.findViewById(R.id.shouyilv_gs);
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		x.image().bind(holder.headimg_gs,"http://www.vipasker.com/"+mlist.get(arg0).get("img"), HttpUtils.headImgDefault());
		holder.nickname_gs.setText(mlist.get(arg0).get("nickName").trim());
		String isPlanner=mlist.get(arg0).get("isPlanner");
		if (isPlanner.equals("1")) {
			holder.label_gs.setText("投");
		}else {
			holder.label_gs.setText("民");
		}
		String sex=mlist.get(arg0).get("gender");
		holder.sex_gs.setVisibility(View.VISIBLE);
		if (sex.equals("男")) {
			holder.sex_gs.setImageResource(R.drawable.nan1);
			holder.sex_gs.setBackgroundColor(Color.RED);
		}else if (sex.equals("女")) {
			holder.sex_gs.setImageResource(R.drawable.nv1);
			holder.sex_gs.setBackgroundColor(Color.BLUE);
		}else{
			holder.sex_gs.setVisibility(View.GONE);
		}
		holder.shouyilv_gs.setText(mlist.get(arg0).get("tatalYeld")+"%");
		//holder.qianming_gs.setText(mlist.get(arg0).get(""));
		
		return arg1;
	}

	public final class ViewHolder {
		public ImageView headimg_gs;
		public ImageView sex_gs;
		public TextView nickname_gs, label_gs;
		public TextView shouyilv_gs, qianming_gs;
	}
}
