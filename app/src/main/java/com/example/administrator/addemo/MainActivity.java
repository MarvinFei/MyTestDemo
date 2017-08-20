package com.example.administrator.addemo;

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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.addemo.adActivity.AdmobBannerActivity;
import com.example.administrator.addemo.adActivity.AdmobInterstitialActivity;
import com.example.administrator.addemo.adActivity.CloudMobiActivity;
import com.example.administrator.addemo.adActivity.MyNativeAdActivity;
import com.example.administrator.addemo.adapter.MainViewPageAdapter;
import com.example.administrator.addemo.adapter.MyRecyclerAdapter;

import butterknife.BindView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener{

    private Button mButton;
    private Button nativeAd;
    private Button admobBannerButton;
    private Button admobInterstitialButton;
    private Button cloudMobiButton;

//    @BindView(R.id.main_tablayout)
//    TabLayout mTabLayout;
//    @BindView(R.id.main_viewpager)
//    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MobileAds.initialize(this, ADMOB_ADDEMO_ID);
        initView();
//        createView();
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


        mButton = (Button)findViewById(R.id.show_button);
        nativeAd = (Button)findViewById(R.id.my_native_ad);
        admobBannerButton = (Button)findViewById(R.id.admob_banner_button);
        admobInterstitialButton = (Button)findViewById(R.id.admob_interstitial_button);
        cloudMobiButton = (Button)findViewById(R.id.cloud_mobi_button);
        mButton.setOnClickListener(this);
        nativeAd.setOnClickListener(this);
        admobBannerButton.setOnClickListener(this);
        admobInterstitialButton.setOnClickListener(this);
        cloudMobiButton.setOnClickListener(this);

    }

//    private void createView(){
//        mViewPager.setAdapter(new MainViewPageAdapter(this, this.getSupportFragmentManager()));
//        mTabLayout.setupWithViewPager(mViewPager);
//    }


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
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v){
        Intent intent = new Intent(this, AdmobBannerActivity.class);
        switch (v.getId()){
            case R.id.show_button:
//                loadInterstitialAd();
                Toast.makeText(this, "不加载插页广告", Toast.LENGTH_SHORT);
                break;
            case R.id.my_native_ad:
                //facebook展示原生广告
                intent.setClass(this, MyNativeAdActivity.class);
                startActivity(intent);
                break;
            case R.id.admob_banner_button:
                intent.setClass(this, AdmobBannerActivity.class);
                startActivity(intent);
                break;
            case R.id.admob_interstitial_button:
                intent.setClass(this, AdmobInterstitialActivity.class);
                startActivity(intent);
                break;
            case R.id.cloud_mobi_button:
                intent.setClass(this, CloudMobiActivity.class);
                startActivity(intent);
                break;
            default:
                break;
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

}
