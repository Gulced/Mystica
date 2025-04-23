package com.example.mystica.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mystica.R;
import com.example.mystica.firebase.FirebaseManager;

public class ForgotPasswordFragment extends Fragment {

    private EditText etForgotEmail;
    private Button btnContinue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        etForgotEmail = view.findViewById(R.id.etForgotEmail);
        btnContinue = view.findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(v -> {
            String email = etForgotEmail.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getContext(), "Mail gir 😇", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseManager.resetPassword(email, new FirebaseManager.FirebaseAuthCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getContext(), "Şifre sıfırlama maili gönderildi ✉️", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(getContext(), "Hata: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }
}
