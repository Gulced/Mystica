package com.example.mystica.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mystica.R;

import java.util.Random;

public class AngelCardFragment extends Fragment {

    private ImageView ivTarotCard;
    private Button btnPickCard;
    private TextView tvCardMeaning;

    private int[] tarotCards = {
            R.drawable.angel_1, R.drawable.angel_4, R.drawable.angel_7,
            R.drawable.angel_2, R.drawable.angel_5, R.drawable.angel_8,
            R.drawable.angel_3, R.drawable.angel_6, R.drawable.angel_9
    };

    // Kart anlamları
    private final String[] angelCardMeanings = {
            " Represents being surrounded by a shield of divine protection.",
            " Symbolizes unconditional love, compassion, and healing energy.",
            " Represents that everything happens at the right time and encourages patience.",
            "Symbolizes change, letting go of the old, and embracing new beginnings.",
            "Represents inner peace, calmness, and finding tranquility in chaos.",
            "Encourages creativity, fresh ideas, and taking action towards your dreams.",
            "Represents physical, emotional, or spiritual healing and restoration.",
            "Symbolizes spiritual growth, enlightenment, and a higher level of understanding.",
            " Represents achieving your goals, recognition, and success in your endeavors."
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarot, container, false);

        ivTarotCard = view.findViewById(R.id.ivTarotCard);
        btnPickCard = view.findViewById(R.id.btnPickCard);
        tvCardMeaning = view.findViewById(R.id.tvCardMeaning);

        // Başlangıçta kartın arkası gösterilsin
        ivTarotCard.setImageResource(R.drawable.angel_back);

        // Kart seçme butonuna tıklanıldığında rastgele bir kart seç
        btnPickCard.setOnClickListener(v -> pickRandomCard());

        return view;
    }

    private void pickRandomCard() {
        Random random = new Random();

        // Rastgele kart seç
        int randomCardIndex = random.nextInt(tarotCards.length);
        int randomCard = tarotCards[randomCardIndex];
        String cardMeaning = angelCardMeanings[randomCardIndex];

        // Seçilen kartın görselini göster
        ivTarotCard.setImageResource(randomCard);

        // Kartın anlamını göster
        tvCardMeaning.setText(cardMeaning);

        // Kullanıcıya kartın seçildiğini bildiren bir Toast mesajı göster
        Toast.makeText(getContext(), "You have picked a tarot card!", Toast.LENGTH_SHORT).show();
    }
}
