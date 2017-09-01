package com.example.administrator.addemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.administrator.addemo.R;
import com.example.administrator.addemo.activity.NavigationView.manageActivity;
import com.example.administrator.addemo.adapter.MainViewPageAdapter;

//import butterknife.BindView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

//    @BindView(R.id.main_tablayout)
    TabLayout mTabLayout;
//    @BindView(R.id.main_viewpager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        createPageView();
    }

    private void initView(){
        Toolbar toolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        toolBar.setTitle("ToolBar");//设置标题
        toolBar.setNavigationIcon(R.drawable.common_google_signin_btn_icon_dark);//设置图标
        //设置Menu Item点击
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        mTabLayout = (TabLayout)findViewById(R.id.main_tablayout);
        mViewPager = (ViewPager)findViewById(R.id.main_viewpager);

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        //toolbar带旋转开关按钮
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolBar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        //toolbar不带旋转开关按钮
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
//                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
//
    private void createPageView(){
        mViewPager.setAdapter(new MainViewPageAdapter(this, this.getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Handle navigation view item clicks here.
        switch (item.getItemId()){
            case R.id.nav_camera:
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_gallery:
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_slideshow:
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_manage:
                Intent intent = new Intent(this, manageActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_share:
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_send:
                drawer.closeDrawer(GravityCompat.START);
                break;
            default:
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

}

//    mRecyclerView = new RecyclerView(this);
//    mRecyclerView = (RecyclerView)findViewById(R.id.my_rev);

//    protected void setupRecycler(){
//        MainAdapter adapter = new MainAdapter(this);
//        mRecyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener(this);
//
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
//        SectionedSpanSizeLookup lookup = new SectionedSpanSizeLookup(adapter, layoutManager);
//        layoutManager.setSpanSizeLookup(lookup);
//        mRecyclerView.setLayoutManager(layoutManager);
//    }
//
//    @Override
//    public void ItemClick(View view, int section, int position) {
//        Toast.makeText(this, "点击了第"+section+"组"+"第"+position+"个", Toast.LENGTH_SHORT).show();
//    }

