package com.example.mystica.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mystica.R;
import com.example.mystica.databinding.FragmentRegisterBinding;
import com.example.mystica.firebase.FirebaseManager;
import com.example.mystica.utils.SharedPrefManager;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // ViewBinding'i başlat
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.btnCreateAccount.setOnClickListener(v -> {
            String firstName = binding.etFirstName.getText().toString().trim();
            String lastName = binding.etLastName.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)
                    || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseManager.register(email, password, new FirebaseManager.FirebaseAuthCallback() {
                @Override
                public void onSuccess() {
                    String fullName = firstName + " " + lastName;

                    // Ad soyad SharedPreferences'e kaydediliyor
                    SharedPrefManager.saveUsername(requireContext(), fullName);
                    Log.d("RegisterFragment", "Saved user full name: " + fullName);

                    Toast.makeText(getContext(), "Welcome, " + fullName, Toast.LENGTH_SHORT).show();

                    // LoginFragment'e geçiş
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new LoginFragment())
                            .addToBackStack(null)
                            .commit();
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Bellek sızıntısı engellenir
    }
}
