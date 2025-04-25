package com.example.mystica.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mystica.R;
import com.example.mystica.utils.SharedPrefManager;

public class HomeFragment extends Fragment {

    private EditText etUserThought;
    private TextView tvDailyThought, tvDailyMessageContent;
    private Button btnSubmitThought;

    private final String[] randomDailyMessages = {
            "Today is a beautiful opportunity to start fresh. 🌱",
            "Let go of yesterday. Focus on what you can create today. ✨",
            "You are worthy of love, peace, and joy. 🌼",
            "Take a deep breath, you are doing your best. 💛",
            "Trust the timing of your life. Everything unfolds as it should. ⏳",
            "Be gentle with yourself. You are growing. 🌸",
            "Small steps every day lead to big changes. 🚶‍♀️"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        etUserThought = view.findViewById(R.id.etUserThought);
        tvDailyThought = view.findViewById(R.id.tvDailyThought);
        btnSubmitThought = view.findViewById(R.id.btnSubmitThought);
        tvDailyMessageContent = view.findViewById(R.id.tvDailyMessageContent);

        // Rastgele günlük mesajı seç ve göster
        int randomIndex = (int) (Math.random() * randomDailyMessages.length);
        tvDailyMessageContent.setText(randomDailyMessages[randomIndex]);

        // Kullanıcı düşüncesini kaydet
        btnSubmitThought.setOnClickListener(v -> {
            String thought = etUserThought.getText().toString().trim();

            if (!thought.isEmpty()) {
                SharedPrefManager.addThought(requireContext(), thought);
                tvDailyThought.setText(thought);
                etUserThought.setText("");
            } else {
                tvDailyThought.setText("Please enter a thought.");
            }
        });

        return view;
    }
}
