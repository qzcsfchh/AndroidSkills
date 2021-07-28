package io.github.qzcsfchh.androidskills;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import io.github.qzcsfchh.androidskills.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AndroidSkills);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
    }
}