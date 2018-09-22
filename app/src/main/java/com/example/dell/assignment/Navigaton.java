package com.example.dell.assignment;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.dell.assignment.UI.ChiActivity;
import com.example.dell.assignment.UI.Gioithieu_Fragment;
import com.example.dell.assignment.UI.ThongKe_Fragment;
import com.example.dell.assignment.UI.ThuActivity;

public class Navigaton extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
        private ChiActivity Chi_fragment;
        private ThuActivity Thu_fragment;
        private ThongKe_Fragment thongKe_fragment;
        private Gioithieu_Fragment gioithieu_fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigaton);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Chi_fragment = new ChiActivity();
        Thu_fragment = new ThuActivity();
        thongKe_fragment = new ThongKe_Fragment();
        gioithieu_fragment = new Gioithieu_Fragment();
        hienthimanhinhthu();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_thu) {
            thu();
        } else if (id == R.id.nav_chi) {
            chi();

        } else if (id == R.id.nav_thongke) {

            thongke();

        }  else if (id == R.id.nav_gioithieu) {
            gioithieu();

        } else if (id == R.id.nav_back) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void thu() {
        hienthimanhinhthu();
    }

    public void chi() {
        hienthimanhinhchi();
    }
    public void thongke(){
        hienthimanhinhthongke();
    }
    public void gioithieu(){
        hienthimanhinhgioithieu();
    }
    private void hienthimanhinhgioithieu(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (gioithieu_fragment.isAdded()) { //kiem tra em co co hien thi hay khong
            ft.show(gioithieu_fragment);
        } else {//khong co  them vao
            ft.add(R.id.container1, gioithieu_fragment);
        }
        if (Chi_fragment.isAdded()) {//neu dang hien thi --> an di
            ft.hide(Chi_fragment);
        }
        if (Thu_fragment.isAdded()) {//neu dang hien thi --> an di
            ft.hide(Thu_fragment);
        }
        if (thongKe_fragment.isAdded()){
            ft.remove(thongKe_fragment);
        }
        //sau khi thay doi fragment thi phai xac thuc lai
        ft.commit();
    }
    private void hienthimanhinhthongke(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (thongKe_fragment.isAdded()) { //kiem tra em co co hien thi hay khong
            ft.show(thongKe_fragment);
        } else {//khong co  them vao
            ft.add(R.id.container1, thongKe_fragment);
        }
        if (Chi_fragment.isAdded()) {//neu dang hien thi --> an di
            ft.hide(Chi_fragment);
        }
        if (Thu_fragment.isAdded()) {//neu dang hien thi --> an di
            ft.hide(Thu_fragment);
        }
        if (gioithieu_fragment.isAdded()){
            ft.hide(gioithieu_fragment);
        }
        //sau khi thay doi fragment thi phai xac thuc lai
        ft.commit();
    }
    private void hienthimanhinhthu() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (Thu_fragment.isAdded()) { //kiem tra em co co hien thi hay khong
            ft.show(Thu_fragment);
        } else {//khong co  them vao
            ft.add(R.id.container1, Thu_fragment);
        }
        if (Chi_fragment.isAdded()) {//neu dang hien thi --> an di
            ft.hide(Chi_fragment);
        }
        if (thongKe_fragment.isAdded()) {//neu dang hien thi --> an di
            ft.remove(thongKe_fragment);
        }
        if (gioithieu_fragment.isAdded()){
            ft.hide(gioithieu_fragment);
        }
        //sau khi thay doi fragment thi phai xac thuc lai
        ft.commit();
    }

    private void hienthimanhinhchi() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (Chi_fragment.isAdded()) { //kiem tra em co co hien thi hay khong
            ft.show(Chi_fragment);
        } else {//khong co  them vao
            ft.add(R.id.container1, Chi_fragment);
        }
        if (Thu_fragment.isAdded()) {//neu dang hien thi --> an di
            ft.hide(Thu_fragment);
        }
        if (thongKe_fragment.isAdded()) {//neu dang hien thi --> an di
            ft.remove(thongKe_fragment);
        }
        if (gioithieu_fragment.isAdded()){
            ft.hide(gioithieu_fragment);
        }
        ft.commit();
    }
}
