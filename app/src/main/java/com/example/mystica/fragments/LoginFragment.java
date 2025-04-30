package com.example.mystica.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mystica.R;
import com.example.mystica.activities.MainActivity;
import com.example.mystica.databinding.FragmentLoginBinding;
import com.example.mystica.firebase.FirebaseManager;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // ViewBinding nesnesini oluştur
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Giriş butonu click listener
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Boş alan bırakma 😢", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseManager.login(email, password, new FirebaseManager.FirebaseAuthCallback() {
                @Override
                public void onSuccess() {
                    // Toast mesajı
                    Toast.makeText(getContext(), "Giriş başarılı 💖", Toast.LENGTH_SHORT).show();

                    // AlertDialog göster
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Hoş Geldin!")
                            .setMessage("Başarıyla giriş yaptınız 🌟")
                            .setPositiveButton("Tamam", (dialog, which) -> {
                                if (getActivity() instanceof MainActivity) {
                                    ((MainActivity) getActivity()).loadHomeFragment();
                                }
                            })
                            .setCancelable(false)
                            .show();
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(getContext(), "Hata: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Kayıt ekranına geçiş
        binding.tvGoToRegister.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new RegisterFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // bellek sızıntısı önlenir
    }
}
