package com.example.mystica.fragments;

import android.content.Intent;
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
import androidx.fragment.app.FragmentTransaction;

import com.example.mystica.activities.MainActivity;
import com.example.mystica.R;
import com.example.mystica.firebase.FirebaseManager;

public class LoginFragment extends Fragment {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private View tvGoToRegister; // burayı ekle

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        tvGoToRegister = view.findViewById(R.id.tvGoToRegister); // TextView yakala

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Boş alan bırakma 😢", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseManager.login(email, password, new FirebaseManager.FirebaseAuthCallback() {

                @Override
                public void onSuccess() {
                    Toast.makeText(getContext(), "Giriş başarılı 💖", Toast.LENGTH_SHORT).show();

                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).loadHomeFragment(); // Ana fragment'a geçiş
                    }
                }


                @Override
                public void onFailure(String error) {
                    Toast.makeText(getContext(), "Hata: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        });

        // ⭐ Register sayfasına geçiş
        tvGoToRegister.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new RegisterFragment()); // container id doğruysa
            transaction.addToBackStack(null);
            ((FragmentTransaction) transaction).commit();
        });


        return view;
    }
}