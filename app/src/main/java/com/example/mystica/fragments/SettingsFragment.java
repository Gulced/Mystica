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
import com.example.mystica.utils.SharedPrefManager;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment {

    private EditText etFullName, etEmail, etPassword;
    private Button btnUpdateInfo, btnDeleteProfile;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        etFullName = view.findViewById(R.id.etFullName);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);
        btnDeleteProfile = view.findViewById(R.id.btnDeleteProfile);

        loadUserInfo();

        btnUpdateInfo.setOnClickListener(v -> updateUserInfo());
        btnDeleteProfile.setOnClickListener(v -> deleteProfile());

        return view;
    }

    private void loadUserInfo() {
        if (currentUser != null) {
            String email = currentUser.getEmail();
            String fullName = SharedPrefManager.getUsername(requireContext());

            if (!TextUtils.isEmpty(email)) {
                etEmail.setText(email);
            }

            if (!TextUtils.isEmpty(fullName)) {
                etFullName.setText(fullName);
            }
        }
    }

    private void updateUserInfo() {
        String newEmail = etEmail.getText().toString().trim();
        String newFullName = etFullName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(newEmail) || TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Please enter your email and password.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(newFullName)) {
            SharedPrefManager.saveUsername(requireContext(), newFullName);
            Toast.makeText(getContext(), "Name updated successfully.", Toast.LENGTH_SHORT).show();
        }

        if (currentUser != null && !newEmail.equals(currentUser.getEmail())) {
            AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), password);

            currentUser.reauthenticate(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            currentUser.updateEmail(newEmail)
                                    .addOnCompleteListener(updateTask -> {
                                        if (updateTask.isSuccessful()) {
                                            Toast.makeText(getContext(), "Email updated successfully.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), "Failed to update email.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(getContext(), "Reauthentication failed. Please check your password.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void deleteProfile() {
        if (currentUser != null) {
            currentUser.delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseAuth.getInstance().signOut();
                            Toast.makeText(getContext(), "Profile deleted successfully.", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, new LoginFragment())
                                    .commit();
                        } else {
                            Toast.makeText(getContext(), "Failed to delete profile.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
