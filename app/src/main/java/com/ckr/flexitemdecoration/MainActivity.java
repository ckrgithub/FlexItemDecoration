package com.ckr.flexitemdecoration;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ckr.decoration.BaseItemDecoration;
import com.ckr.decoration.DecorationLog;
import com.ckr.flexitemdecoration.adapter.MyFragmentPagerAdapter;
import com.ckr.flexitemdecoration.view.BaseFragment;
import com.ckr.flexitemdecoration.view.HorizontalGridFragment;
import com.ckr.flexitemdecoration.view.LinearFragment;
import com.ckr.flexitemdecoration.view.VerticalGridFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static final String TAG = "MainActivity";
    private static final String PAGE = "page";
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    private Menu menu;
    private Unbinder unBinder;
    private ArrayList<BaseFragment> fragmentList;
    private int currentPage = 0;
    public Map<Integer, Integer> map;
    public static final int[] MENU_ITEM_ID = {R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5, R.id.item6, R.id.item7, R.id.item8, R.id.item9, R.id.item10, R.id.item11};
    public boolean[] is_checked = {true, false, false, false, false, false, false, false, false, false, false};
    public static final String[] TITLES = {"垂直网格", "水平网格", "垂直网格2", "垂直线性", "水平线性"};
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecorationLog.debug();
        if (savedInstanceState == null) {
        } else {
            currentPage = savedInstanceState.getInt(PAGE);
        }
        setContentView(R.layout.activity_main);
        unBinder = ButterKnife.bind(this);
        initFragment();
        initView();
        map = new HashMap<>(MENU_ITEM_ID.length);
        for (int i = 0; i < MENU_ITEM_ID.length; i++) {
            map.put(MENU_ITEM_ID[i], i);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PAGE, currentPage);
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentList = new ArrayList<>();
        for (int i = 0; i < TITLES.length; i++) {
            String name = makeFragmentName(R.id.viewPager, i);
            BaseFragment fragment = (BaseFragment) fragmentManager.findFragmentByTag(name);
            if (fragment == null) {
                if (i == 0) {
                    fragmentList.add(VerticalGridFragment.newInstance(false));
                } else if (i == 1) {
                    fragmentList.add(HorizontalGridFragment.newInstance());
                } else if (i == 2) {
                    fragmentList.add(VerticalGridFragment.newInstance(true));
                } else if (i == 3) {
                    fragmentList.add(LinearFragment.newInstance(BaseItemDecoration.VERTICAL));
                } else if (i == 4) {
                    fragmentList.add(LinearFragment.newInstance(BaseItemDecoration.HORIZONTAL));
                }
            } else {
                fragmentList.add(fragment);
            }
        }
    }

    private void initView() {
        tabLayout.addTab(tabLayout.newTab().setText(TITLES[currentPage]), currentPage, true);
        viewPager.setAdapter(new MyFragmentPagerAdapter(fragmentManager, fragmentList, TITLES));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(currentPage,false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBinder.unbind();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, this.menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: id:" + item.getItemId());
        switch (item.getItemId()) {
            case R.id.item1:
                if (!item.isChecked()) {
                    for (int i = 1; i < MENU_ITEM_ID.length; i++) {
                        disableChecked(i);
                    }
                    break;
                } else {
                    return true;
                }
            case R.id.item2:
            case R.id.item3:
            case R.id.item4:
            case R.id.item5:
                if (!item.isChecked()) {
                    disableChecked(0);
                }
                break;
            case R.id.item6:
            case R.id.item7:
            case R.id.item8:
            case R.id.item9:
            case R.id.item10:
            case R.id.item11:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        boolean checked = !item.isChecked();
        item.setChecked(checked);
        Integer index = map.get(item.getItemId());
        is_checked[index] = checked;
        fragmentList.get(currentPage).refreshFragment(is_checked);
        return true;
    }

    private void disableChecked(int pos) {
        MenuItem menuItem = menu.findItem(MENU_ITEM_ID[pos]);
        if (menuItem.isChecked()) {
            menuItem.setChecked(false);
        }
        is_checked[pos] = false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
        fragmentList.get(currentPage).refreshFragment(is_checked);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
