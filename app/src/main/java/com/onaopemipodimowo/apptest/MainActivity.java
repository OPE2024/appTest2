package com.onaopemipodimowo.apptest;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


import fragment.ComposeFragment;
import fragment.HomeFragment;
import fragment.MapFragment;
import fragment.ProfileFragment;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private static final int NUM_PAGES=4;
    private ViewPager2 viewPager2;
    private FragmentStateAdapter pageAdapter;
    private FirebaseAuth authProfile;
//    boolean home;
//    private RecyclerView list;
//    private TextView mTextViewResult;
//    private String myResponse;
    private String TAG = "MainActivity";
//    HomeAdapter adapter;
//
//    List<Home> homes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.ViewP);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        pageAdapter = new ScreenSlidePageAdapter(this);
       viewPager2.setAdapter(pageAdapter);
        setViewPagerListener();
        setBottomNavigationView();


    }


    private void setBottomNavigationView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

               if (item.getItemId()==R.id.homeMenuIId){
                   viewPager2.setCurrentItem(0);

                   return true;
               }
               if (item.getItemId()==R.id.mapMenuId){
                   viewPager2.setCurrentItem(1);

                   return true;
               }
               if (item.getItemId()==R.id.composeMenuId){
                   viewPager2.setCurrentItem(2);
                   ;
                   return true;
               }
               if (item.getItemId()==R.id.Profile){
                   viewPager2.setCurrentItem(3);
                   return true;
               }
               return true;
            }
        });


    }

    private void setViewPagerListener(){
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position){
                if (position==0){
                    bottomNavigationView.setSelectedItemId(R.id.homeMenuIId);
                }
                if (position==1){
                    bottomNavigationView.setSelectedItemId(R.id.mapMenuId);
                }
                if (position==2){
                    bottomNavigationView.setSelectedItemId(R.id.composeMenuId);
                }
                if (position==3){
                    bottomNavigationView.setSelectedItemId(R.id.Profile);
                }

            }
        });
    }
    private class ScreenSlidePageAdapter extends FragmentStateAdapter{
        public ScreenSlidePageAdapter(MainActivity mainActivity){super(mainActivity);}


        @NonNull
        @Override
        public Fragment createFragment(int postion){
            Fragment fragment;
            switch (postion){
                case 0:
                     fragment = new HomeFragment();
                     break;
                case 1:
                     fragment = new MapFragment();
                     break;
                case 2:
                    fragment = new ComposeFragment();
                    break;
                case 3:
                    fragment = new ProfileFragment();
                    break;
                default:
                    return null;
            }
            return fragment;
        }
        @Override
        public int getItemCount(){return NUM_PAGES;}
    }
    private class ZoomOutPageTransformer implements ViewPager2.PageTransformer{
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position){
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1){
                view.setAlpha(0f);
            }else if (position <= 1){
                float scaleFactor = Math.max(MIN_SCALE,1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0){
                    view.setTranslationX(horzMargin - vertMargin / 2);
                }else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // scale the page down
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                //fade the page relative to its size
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)/ (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            }   else {
                view.setAlpha(0f);
            }
        }
    }


    // create actionBar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Inflate menu items
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // when any item menu is selcted
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if (id == R.id.menu_refresh){
            //Refresh activity
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        }/* else if (id == R.id.menu_update_profile){
            Intent intent = new Intent(MainActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
        }else if (id == R.id.menu_update_email){
            Intent intent = new Intent(MainActivity.this, UpdateEmailActivity.class);
            startActivity(intent);
        }else if (id == R.id.menu_settings){
            Toast.makeText(MainActivity.this, "menu_settings", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.menu_change_password){
            Intent intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        }else if (id == R.id.menu_delete_profile){
            Intent intent = new Intent(MainActivity.this, DeleteProfileActivty.class);
            startActivity(intent);
        }*/ else if (id == R.id.menu_logout){
            authProfile.signOut();
            Toast.makeText(MainActivity.this,"Logged Out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);

            //Clear stack to prevent user coming back to MainActivity on pressing back button after logging out
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // close MainActivity
        }else {
            Toast.makeText(MainActivity.this, "Something went Wrong!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }


}






