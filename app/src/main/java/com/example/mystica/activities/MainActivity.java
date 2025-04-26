package com.example.mystica.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import com.example.mystica.utils.NotificationHelper; // Günaydın bildirimi için Receiver'ı ekliyoruz

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private LinearLayout bottomNavbar;
    private ImageButton btnHome, btnProfile, btnSettings, btnAngel, btnTarot, btnThought;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // İlk açılışta login ekranı gelsin
        if (savedInstanceState == null) {
            loadFragment(new LoginFragment());
        }

        // Günaydın bildirimi için alarmı kur
        scheduleDailyGoodMorningNotification();
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

    // Sabah 09:00'da her gün Günaydın bildirimi kuran fonksiyon
    private void scheduleDailyGoodMorningNotification() {
        Intent intent = new Intent(this, NotificationHelper.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // Eğer saat 9:00 geçmişse, yarın için kur
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );
    }
}
