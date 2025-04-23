package com.example.mystica.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mystica.R;
import com.example.mystica.fragments.AngelCardFragment;
import com.example.mystica.fragments.DailyEmotionFragment;
import com.example.mystica.fragments.ForgotPasswordFragment;
import com.example.mystica.fragments.HomeFragment;
import com.example.mystica.fragments.LoginFragment;
import com.example.mystica.fragments.ProfileFragment;
import com.example.mystica.fragments.RegisterFragment;
import com.example.mystica.fragments.SettingsFragment;
import com.example.mystica.fragments.TarotDrawFragment;

public class MainActivity extends AppCompatActivity {

    private LinearLayout bottomNavbar;

    private ImageButton btnHome, btnProfile, btnSettings, btnAngel, btnTarot, btnThought;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // activity_main.xml'e göre

        // Navbar ve butonları bağla
        bottomNavbar = findViewById(R.id.bottomNavbar);
        btnHome = findViewById(R.id.btnHome);
        btnProfile = findViewById(R.id.btnProfile);
        btnSettings = findViewById(R.id.btnSettings);
        btnAngel = findViewById(R.id.btnAngel);
        btnTarot = findViewById(R.id.btnTarot);
        btnThought = findViewById(R.id.btnThought);

        // Tıklama olayları
        btnHome.setOnClickListener(v -> loadFragment(new HomeFragment()));
        btnProfile.setOnClickListener(v -> loadFragment(new ProfileFragment()));
        btnSettings.setOnClickListener(v -> loadFragment(new SettingsFragment()));
        btnAngel.setOnClickListener(v -> loadFragment(new AngelCardFragment()));
        btnTarot.setOnClickListener(v -> loadFragment(new TarotDrawFragment()));
        btnThought.setOnClickListener(v -> loadFragment(new DailyEmotionFragment()));

        // Uygulama ilk açıldığında login ekranı yüklensin
        if (savedInstanceState == null) {
            loadFragment(new LoginFragment());
        }
    }

    // Fragment'ları yükleyen ana fonksiyon
    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

        // Giriş ekranlarında navbar gizli, diğerlerinde görünür
        if (fragment instanceof LoginFragment ||
                fragment instanceof RegisterFragment ||
                fragment instanceof ForgotPasswordFragment) {
            bottomNavbar.setVisibility(View.GONE);
        } else {
            bottomNavbar.setVisibility(View.VISIBLE);
        }
    }

    // Girişten sonra Home'a geçmek için
    public void loadHomeFragment() {
        loadFragment(new HomeFragment());
    }
}
