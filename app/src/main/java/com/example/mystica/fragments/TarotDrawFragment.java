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

public class TarotDrawFragment extends Fragment {

    private ImageView ivTarotCard;
    private Button btnPickCard;
    private TextView tvCardMeaning;

    private int[] tarotCards = {
            R.drawable.card_1, R.drawable.card_2, R.drawable.card_3,
            R.drawable.card_4, R.drawable.card_5, R.drawable.card_6,
            R.drawable.card_7, R.drawable.card_8, R.drawable.card_9
    };

    // Kart anlamları
    private final String[] tarotCardMeanings = {
            "This card represents new beginnings and adventures.",
            "This card represents power, skill, and creativity.",
            " Represents intuition, mystery, and wisdom.",
            " Symbolizes fertility, creativity, and nurturing energy.",
            "The Emperor - Represents authority, leadership, and structure.",
            "The Lovers - This card represents love, relationships, and choices.",
            " The Chariot - Represents determination, control, and overcoming obstacles.",
            " Strength - This card symbolizes courage, strength, and resilience.",
            "The Hermit - Represents introspection, solitude, and inner guidance."
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
        ivTarotCard.setImageResource(R.drawable.back);

        // Kart seçme butonuna tıklanıldığında rastgele bir kart seç
        btnPickCard.setOnClickListener(v -> pickRandomCard());

        return view;
    }

    private void pickRandomCard() {
        Random random = new Random();

        // Rastgele kart seç
        int randomCardIndex = random.nextInt(tarotCards.length);
        int randomCard = tarotCards[randomCardIndex];
        String cardMeaning = tarotCardMeanings[randomCardIndex];

        // Seçilen kartın görselini göster
        ivTarotCard.setImageResource(randomCard);

        // Kartın anlamını göster
        tvCardMeaning.setText(cardMeaning);

        // Kullanıcıya kartın seçildiğini bildiren bir Toast mesajı göster
        Toast.makeText(getContext(), "You have picked a tarot card!", Toast.LENGTH_SHORT).show();
    }
}
