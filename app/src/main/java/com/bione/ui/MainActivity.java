package com.bione.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import com.bione.R;
import com.bione.ui.base.BaseActivity;

import com.bione.ui.home.ChatFragment;
import com.bione.ui.home.fragments.CustomerReceiptFragment;
import com.bione.ui.home.FaqFragment;
import com.bione.ui.home.DashBoardFragment;
import com.bione.ui.home.MyCounsellingFragment;
import com.bione.ui.home.ProfileFragment;
import com.bione.ui.home.WebviewActivity;
import com.bione.utils.Log;
import com.google.android.material.navigation.NavigationView;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends BaseActivity {


    private static final Float END_SCALE = 0.8f;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView ivProfile;
    private TextView txtName, txtPhone, tvLogout;
    private Toolbar toolbar;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_DASH = "Dashboard";
    private static final String TAG_PROFILE = "Profile";
    private static final String TAG_CHAT = "Chat";
    private static final String TAG_FAQ = "Faq";
    private static final String TAG_SESSION = "Session";
    private static final String TAG_CUSTOMER_RECEIPT = "Payment Receipt";
    //    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_DASH;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    private String category = "";

    private ResideMenu resideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();


//        tvLogout = findViewById(R.id.tvLogout);

//        tvLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                new AlertDialog.Builder(MainActivity.this)
//                        .setMessage("Are you sure?")
//                        .setPositiveButton(R.string.text_yes, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(final DialogInterface dialog, final int which) {
////                                Toast.makeText(getApplicationContext(), "Yes", Toast.LENGTH_SHORT).show();
//                                Paper.book().destroy();
//                                Intent intent = new Intent(MainActivity.this, Splash.class);
//                                // set the new task and clear flags
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(intent);
//                            }
//                        })
//                        .setNegativeButton(R.string.text_no, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
////                                Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .show();
//            }
//        });


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        Log.d("getHeaderCount", "------" + navigationView.getHeaderCount());
        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = navHeader.findViewById(R.id.name);
        txtPhone = navHeader.findViewById(R.id.phone);

//        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        ivProfile = (ImageView) navHeader.findViewById(R.id.ivProfile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);


        // load nav menu header data
        loadNavHeader();

        // Logic for mobilenumber verification
//        if (CommonData.getUserData().getMobilenumber() == null || CommonData.getUserData().getMobilenumber().equals("")) {
//            openDialog();
//        }

        //logic for sales person screen
//        isSalesPerson();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_DASH;
            loadHomeFragment();
        }

    }


    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {

//        // name, phone
//        Customer customer = CommonData.getUserData();
//        if (customer.getFirstname() != null) {
//            if (customer.getLastname() != null) {
//                txtName.setText(customer.getFirstname() + " " + customer.getLastname());
//            } else {
//                txtName.setText(customer.getFirstname());
//            }
//        }
//        if (customer.getMobilenumber() != null) {
//            txtPhone.setText("+" + customer.getMobilenumber());
//        }
//
//        if (CommonData.getUserPhoto() != null) {
//            File photoFile = new File(CommonData.getUserPhoto().get(0).getThumbnailSmallPath());
//
//            Glide.with(getApplicationContext())
//                    .load(photoFile)
//                    .apply(RequestOptions.circleCropTransform())
//                    .into(ivProfile);
//        }


    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
//            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
//        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:

                // DASH fragment
                DashBoardFragment dashboardFragment = new DashBoardFragment();
//                dashboardFragment.
                return dashboardFragment;

            case 1:
                // PROFILE fragment
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;

            case 2:
                // Session fragment
                MyCounsellingFragment sessionFragment = new MyCounsellingFragment();
                return sessionFragment;

            case 3:
                // CHAT fragment
//                ZohoSalesIQ.Chat.show();
                ChatFragment chatFragment = new ChatFragment();
                return chatFragment;
//                CustomerReceiptFragment customerReceiptFragment = new CustomerReceiptFragment();
//                return customerReceiptFragment;

            case 4:
                // FAQ fragment
                FaqFragment faqFragment = new FaqFragment();
                return faqFragment;

            case 5:
                CustomerReceiptFragment customerReceiptFragment = new CustomerReceiptFragment();
                return customerReceiptFragment;
//                Intent intent = new Intent(MainActivity.this, CustomerReceiptFragment.class);
//                startActivity(intent);


            default:
                return new DashBoardFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void hideShowItem(boolean isShow) {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_receipt).setVisible(isShow);
    }

    private void setUpNavigationView() {


        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;

                    case R.id.nav_dashboard:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_DASH;
                        break;

                    case R.id.nav_profile:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PROFILE;
                        break;

//                    case R.id.nav_session:
//                        navItemIndex = 2;
//                        CURRENT_TAG = TAG_SESSION;
//                        break;

                    case R.id.nav_chat:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_CHAT;
                        break;

//                    case R.id.nav_faq:
//                        navItemIndex = 4;
//                        CURRENT_TAG = TAG_FAQ;
//                        break;

                    case R.id.nav_receipt:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_CUSTOMER_RECEIPT;

                        break;

                    case R.id.nav_logout:
                        Toast.makeText(MainActivity.this, "Logout Clicked", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
//                        startActivity(new Intent(MainActivity.this, WebviewActivity.class));
//                        drawer.closeDrawers();
//                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, WebviewActivity.class));
                        drawer.closeDrawers();
                        return true;

                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);

                loadNavHeader();
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        CoordinatorLayout contentView = findViewById(R.id.contentView);
        drawer.setDrawerShadow(R.color.white, GravityCompat.START);
        drawer.setScrimColor(getResources().getColor(R.color.transparent)); // remove shadow above contentView
        drawer.setDrawerElevation(0); // remove right side line
        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                final Float diffScaleFloat = slideOffset * (1 - END_SCALE);
                final Float offsetScale = 1 - diffScaleFloat;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                final Float xOffset = drawerView.getWidth() * slideOffset;
                final Float xOffsetDiff = contentView.getWidth() * diffScaleFloat / 2;
                final Float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);


//                GradientDrawable shape = new GradientDrawable();
//                shape.setShape(GradientDrawable.RECTANGLE);
//                shape.setColor(Color.WHITE);
////                shape.setCornerRadii(new float[] { 40, 40, 40, 40, 0, 0, 0, 0 });
//                shape.setCornerRadius(40f);
//                contentView.setBackground(shape);


            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_DASH;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {

    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return resideMenu.dispatchTouchEvent(ev);
//    }

    private void setResideMenu() {
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.color.colorPrimary);
        resideMenu.attachToActivity(this);
        resideMenu.setScaleValue(0.6f);
        resideMenu.setShadowVisible(false);

        // create menu items;
        String titles[] = {"Home", "Profile", "Calendar", "Settings"};
        int icon[] = {R.drawable.ic_close, R.drawable.ic_button_next_light, R.drawable.ic_login_next, R.drawable.ic_facebook};

        for (int i = 0; i < titles.length; i++) {
            ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
            item.setOnClickListener(this);
            resideMenu.addMenuItem(item, ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
        }

        resideMenu.setMenuListener(new ResideMenu.OnMenuListener() {
            @Override
            public void openMenu() {
                Toast.makeText(getApplicationContext(), "Menu is opened!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void closeMenu() {
                Toast.makeText(getApplicationContext(), "Menu is closed!", Toast.LENGTH_SHORT).show();
            }
        });
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
    }


}