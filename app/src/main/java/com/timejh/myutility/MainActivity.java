package com.timejh.myutility;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.timejh.myutility.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FiveFragment.OnListFragmentInteractionListener {

    private final int REQ_CODE = 100;

    private final int ONE_FRAGMENT = 0;
    private final int TWO_FRAGMENT = 1;
    private final int THREE_FRAGMENT = 2;
    private final int FOUR_FRAGMENT = 3;

    final int TAB_COUNT = 5;

    private List<Integer> viewPagerStacks;
    private boolean isBackPressed = false;

    OneFragment oneFragment;
    TwoFragment twoFragment;
    ThreeFragment threeFragment;
    FourFragment fourFragment;
    FiveFragment fiveFragment;

    private ViewPager viewPager;

    LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fragment init
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        threeFragment = new ThreeFragment();
        fourFragment = new FourFragment();
        fiveFragment = FiveFragment.newInstance(3); // 미리 정해진 그리드 가로축 개수

        // TabLayou 정의
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("계산기"));
        tabLayout.addTab(tabLayout.newTab().setText("단위변환"));
        tabLayout.addTab(tabLayout.newTab().setText("검색"));
        tabLayout.addTab(tabLayout.newTab().setText("현재위치"));
        tabLayout.addTab(tabLayout.newTab().setText("갤러리"));

        // Fragment Pager 설정
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        // Adapter설정
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // 1. Pager가 변경 되었을 때 Tab를 바꿔주는 리스너
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        // 2. Tab이 변경 되었을 때 페이지를 바꿔주는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        // 3. Pager에서 사용할 Listener 추가 등록
        viewPager.addOnPageChangeListener(pageChangeListener);
        // 4. Pager의 뒤로가기 처리를 위한 Stack초기화
        viewPagerStacks = new ArrayList<>();
        viewPagerStacks.add(ONE_FRAGMENT);// 처음 위치 저장

        //Permission Check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            init();
        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (!isBackPressed)
                viewPagerStacks.add(position);
            else
                isBackPressed = false;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    private void init() {
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!isGpsEnable()) { // Gps가 꺼져있는 경우
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS 켜기");
            builder.setMessage("GPS가 꺼져있습니다\n설정창으로 이동하시겠습니까?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }

    // Gps가 켜져있는지 체크 : 롤리팝 이하 버전에서 체크
    private boolean isGpsEnable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 롤리팝 이상버전에서 체크
            return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        String gps = android.provider.Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        return gps.matches(",*gps.*");
    }

    @Override
    public void onBackPressed() {
        isBackPressed = true;
        switch (viewPager.getCurrentItem()) {
            case ONE_FRAGMENT:
                break;
            case TWO_FRAGMENT:
                break;
            case THREE_FRAGMENT:
                if (threeFragment.goBack()) { //webView에서 뒤로가기가 가능하면 뒤로가고 없으면 디바이스의 뒤로가기가 된다.
                    return;
                }
                break;
            case FOUR_FRAGMENT:
                break;
        }
        if (viewPagerStacks.size() > 1) {
            int position = viewPagerStacks.get(viewPagerStacks.size() - 2);
            viewPagerStacks.remove(viewPagerStacks.size() - 1);
            viewPager.setCurrentItem(position);
        } else {
            super.onBackPressed();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            String permArr[] = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permArr, REQ_CODE);
        } else {
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                init();
            } else {
                Toast.makeText(this, "권한을 허용하지 않으면 프로그램을 실행 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = oneFragment;
                    break;
                case 1:
                    fragment = twoFragment;
                    break;
                case 2:
                    fragment = threeFragment;
                    break;
                case 3:
                    fragment = fourFragment;
                    break;
                case 4:
                    fragment = fiveFragment;
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }
}
