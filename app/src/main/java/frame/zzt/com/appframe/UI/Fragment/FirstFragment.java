package frame.zzt.com.appframe.UI.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.UI.Activity.ActivityFirst;

/**
 * Created by allen on 18/8/8.
 */

public class FirstFragment extends Fragment {

    @BindView(R.id.listView_first)
    public ListView mListView;

    @BindString(R.string.tab_item_first )
    public String title ;

    @BindString(R.string.tab_item_desc )
    public String desc ;

    private View mRootView;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_first, container, false);

        unbinder = ButterKnife.bind(this , mRootView);

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
            new DemoInfo( R.string.tab_item_first  , R.string.tab_item_desc ,ActivityFirst.class),
    };

    public class DemoListAdapter extends BaseAdapter {
        public DemoListAdapter() {
            super();
        }
        @Override
        public View getView(int index, View convertView, ViewGroup parent) {
            MyViewHolder myViewHolder ;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.list_info_item, null);
                myViewHolder= new MyViewHolder(convertView);
                convertView.setTag(myViewHolder);
            }else {
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
            @BindView(R.id.title)  TextView title;
            @BindView(R.id.desc)  TextView desc;
            public MyViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    private class DemoInfo {
        private final int title;
        private final int desc;
        private final Class<? extends Activity> demoClass;

        public DemoInfo(int title, int desc,
                        Class<? extends Activity> demoClass) {
            this.title = title;
            this.desc = desc;
            this.demoClass = demoClass;
        }
    }
}