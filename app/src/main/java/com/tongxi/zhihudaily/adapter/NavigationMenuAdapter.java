package com.tongxi.zhihudaily.adapter;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tongxi.zhihudaily.R;
import com.tongxi.zhihudaily.model.ThemeListBean;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qf on 2016/10/22.
 */
public class NavigationMenuAdapter extends BaseAdapter {

//    private List<ThemeListBean.OthersBean> themeList;   //暂时以其他主题列表显示（主题分为关注主题和其他主题，关注主题暂时为0）
    private List<HashMap<String,Object>> themeList;    //键“theme” 值 ThemeListBean.OthersBean 对象   “isAdd” 值 Boolean
    private Context context;
    private LayoutInflater inflater;
    private Holder holder;
    private boolean isAdd;
    private DrawerLayout drawerLayout;

    public NavigationMenuAdapter(Context context,List<HashMap<String,Object>> themeList,DrawerLayout drawerLayout) {
        this.themeList = themeList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.drawerLayout = drawerLayout;
    }

    @Override
    public int getCount() {
        return themeList == null?0:themeList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_navigation_menu, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }

        HashMap<String, Object> map = themeList.get(position);
        ThemeListBean.OthersBean theme = (ThemeListBean.OthersBean) map.get("theme");
        final Boolean added = (Boolean) map.get("isAdd");
        if (added){
            holder.tvAddTheme.setBackgroundResource(R.mipmap.home_arrow);
        }else {
            holder.tvAddTheme.setBackgroundResource(R.mipmap.menu_follow);
        }
        holder.tvTheme.setText(theme.getName());

        //设置关注主题的监听事件
        holder.tvAddTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean _added = !added;
                themeList.get(position).put("isAdd",_added);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class Holder {
        @BindView(R.id.tvTheme)
        TextView tvTheme;
        @BindView(R.id.tvAddTheme)
        TextView tvAddTheme;

        Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
