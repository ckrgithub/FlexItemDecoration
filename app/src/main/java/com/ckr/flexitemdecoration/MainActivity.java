package com.ckr.flexitemdecoration;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ckr.flexitemdecoration.adapter.MyFragmentPagerAdpater;
import com.ckr.flexitemdecoration.view.BaseFragment;
import com.ckr.flexitemdecoration.view.MainFragment;
import com.ckr.flexitemdecoration.view.ThreeMainFragment;
import com.ckr.flexitemdecoration.view.TwoMainFragment;
import com.ckr.flexitemdecoration.widget.BaseItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity";
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    private Menu menu;
    private Unbinder unBinder;
    private ArrayList<BaseFragment> fragmentList;
    private int currentPage = 0;
    public Map<Integer, Integer> map;
    public static final int[] MENU_ITEM_ID = {R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5};
    public boolean[] is_checked = {true, false, false, false, false};
    public static final String[] TITLES = {"垂直网格","水平网格", "垂直线性", "水平线性"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unBinder = ButterKnife.bind(this);
        initFragment();
        initView();
        map = new HashMap<>(MENU_ITEM_ID.length);
        for (int i = 0; i < MENU_ITEM_ID.length; i++) {
            map.put(MENU_ITEM_ID[i], i);
        }
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(MainFragment.newInstance());
        fragmentList.add(ThreeMainFragment.newInstance());
        fragmentList.add(TwoMainFragment.newInstance(BaseItemDecoration.VERTICAL));
        fragmentList.add(TwoMainFragment.newInstance(BaseItemDecoration.HORIZONTAL));
    }

    private void initView() {
        tabLayout.addTab(tabLayout.newTab().setText(TITLES[currentPage]), true);
        viewPager.setAdapter(new MyFragmentPagerAdpater(getSupportFragmentManager(), fragmentList, TITLES));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);
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
