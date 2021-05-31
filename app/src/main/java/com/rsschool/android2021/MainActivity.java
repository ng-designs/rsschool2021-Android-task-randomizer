package com.rsschool.android2021;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements FragmentInterface {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(0);
    }

    private void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment);
        transaction.commit();
        // TODO: invoke function which apply changes of the transaction
    }

    private void openSecondFragment(int min, int max) {
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, secondFragment);
        transaction.commit();
        // TODO: implement it
    }

    @Override
    public void generateRandom(int min, int max) {
        openSecondFragment(min, max);
    }

    @Override
    public void onReturn(int prevRes) {
        openFirstFragment(prevRes);
    }

    @Override
    public void onBackPressed() {
        SecondFragment secFragment = (SecondFragment) getSupportFragmentManager().findFragmentByTag("SECOND");
        if (secFragment != null && secFragment.isVisible()) {
            // add your code here
            onReturn(secFragment.getResult());
        } else super.onBackPressed();
    }
}
