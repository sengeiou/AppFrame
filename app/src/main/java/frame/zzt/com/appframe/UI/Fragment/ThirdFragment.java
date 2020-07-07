package frame.zzt.com.appframe.UI.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import frame.zzt.com.appframe.DragView.DragLayoutActivity;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.anim.AnimActivity;
import frame.zzt.com.appframe.databind.ActDataBind;
import frame.zzt.com.appframe.databind.ActDoubleDataBind;
import frame.zzt.com.appframe.mtoast.ActivityToastCompat;
import frame.zzt.com.appframe.mvvmbind.ActivityMVVMDemo;
import frame.zzt.com.appframe.networkopt.ActivityNetwork;
import frame.zzt.com.appframe.seekbar.ActivitySeekBar;
import frame.zzt.com.appframe.signed.ActivitySigned;
import frame.zzt.com.appframe.singleList.ActivitySingleList;
import frame.zzt.com.appframe.slidelock.ActivityHorizontalSlideLock;
import frame.zzt.com.appframe.tablayout.ActivityTablayout;
import frame.zzt.com.appframe.widgetview.ActivityWidget;

/**
 * Created by allen on 18/8/8.
 */

public class ThirdFragment extends Fragment {

    @BindView(R.id.listView_third)
    public ListView mListView;

    @BindString(R.string.tab_item_first)
    public String title;

    @BindString(R.string.tab_item_desc)
    public String desc;

    private View mRootView;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_third, container, false);

        unbinder = ButterKnife.bind(this, mRootView);

        initView();

        return mRootView;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    private void initView() {
        // 添加ListItem，设置事件响应
        mListView.setAdapter(new DemoListAdapter());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v, int index,
                                    long arg3) {
                onListItemClick(index);
            }
        });
    }

    void onListItemClick(int index) {
        Intent intent;
        intent = new Intent(getActivity(), DEMOS[index].demoClass);
        this.startActivity(intent);
    }

    private DemoInfo[] DEMOS = {
            new DemoInfo(R.string.show_item_toast, R.string.show_item_toast, ActivityToastCompat.class),
            new DemoInfo(R.string.show_item_databingding_mvvm, R.string.show_item_databingding_mvvm, ActivityMVVMDemo.class),
            new DemoInfo(R.string.show_item_databingding, R.string.show_item_databingding, ActDataBind.class),
            new DemoInfo(R.string.show_item_databingding_double, R.string.show_item_databingding_double, ActDoubleDataBind.class),
            new DemoInfo(R.string.tab_item_anim_activity, R.string.tab_item_anim_activity_desc, AnimActivity.class),
            new DemoInfo(R.string.show_item_h_slide_lock, R.string.show_item_h_slide_lock_desc, ActivityHorizontalSlideLock.class),
            new DemoInfo(R.string.show_item_seek_bar, R.string.show_item_seek_bar_desc, ActivitySeekBar.class),
            new DemoInfo(R.string.show_item_widget, R.string.show_item_widget_desc, ActivityWidget.class),
            new DemoInfo(R.string.show_item_view_signed, R.string.show_item_view_signed_desc, ActivitySigned.class),
            new DemoInfo(R.string.show_item_network_evbus, R.string.show_item_network_evbus, ActivityNetwork.class),
            new DemoInfo(R.string.show_item_tablayout, R.string.show_item_tablayout, ActivityTablayout.class),
            new DemoInfo(R.string.show_item_drag, R.string.show_item_drag, DragLayoutActivity.class),
            new DemoInfo(R.string.show_item_drag, R.string.show_item_drag, DragLayoutActivity.class),
            new DemoInfo(R.string.show_item_signlist, R.string.show_item_signlist, ActivitySingleList.class),
    };

    public class DemoListAdapter extends BaseAdapter {
        public DemoListAdapter() {
            super();
        }

        @Override
        public View getView(int index, View convertView, ViewGroup parent) {
            MyViewHolder myViewHolder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.list_info_item, null);
                myViewHolder = new MyViewHolder(convertView);
                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }
            myViewHolder.title.setText(DEMOS[index].title);
            myViewHolder.desc.setText(DEMOS[index].desc);
            if (index >= 25) {
                myViewHolder.title.setTextColor(Color.YELLOW);
            }
            return convertView;
        }

        @Override
        public int getCount() {
            return DEMOS.length;
        }

        @Override
        public Object getItem(int index) {
            return DEMOS[index];
        }

        @Override
        public long getItemId(int id) {
            return id;
        }

        class MyViewHolder {
            @BindView(R.id.title)
            TextView title;
            @BindView(R.id.desc)
            TextView desc;

            public MyViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
