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

public class RegisterFragment extends Fragment {

    private EditText etEmail, etPassword, etFirstName, etLastName;
    private Button btnCreateAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // EditText ve Button bileşenlerini tanımla
        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnCreateAccount = view.findViewById(R.id.btnCreateAccount);

        // Create Account butonuna tıklandığında yapılacak işlemler
        btnCreateAccount.setOnClickListener(v -> {
            String firstName = etFirstName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Eğer her hangi bir alan boşsa, kullanıcıyı uyar
            if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Lütfen tüm alanları doldurduğunuzdan emin olun!", Toast.LENGTH_SHORT).show();
                return;
            }

            // FirebaseManager üzerinden register işlemini başlat
            FirebaseManager.register(email, password, new FirebaseManager.FirebaseAuthCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getContext(), "Kayıt başarılı 🎉", Toast.LENGTH_SHORT).show();
                    // Kayıt işlemi başarılı ise, LoginFragment'a yönlendirme
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new LoginFragment()) // LoginFragment'a geçiş
                            .addToBackStack(null) // BackStack'e ekle
                            .commit();
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(getContext(), "Hata: " + error, Toast.LENGTH_SHORT).show(); // Hata mesajını göster
                }
            });
        });

        return view;
    }
}
