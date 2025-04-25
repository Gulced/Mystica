package com.example.mystica.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.mystica.utils.SharedPrefManager;

public class RegisterFragment extends Fragment {

    private EditText etEmail, etPassword, etFirstName, etLastName;
    private Button btnCreateAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnCreateAccount = view.findViewById(R.id.btnCreateAccount);

        btnCreateAccount.setOnClickListener(v -> {
            String firstName = etFirstName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)
                    || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseManager.register(email, password, new FirebaseManager.FirebaseAuthCallback() {
                @Override
                public void onSuccess() {
                    String fullName = firstName + " " + lastName;

                    // ✅ Save full name to SharedPreferences
                    SharedPrefManager.saveUsername(requireContext(), fullName);
                    Log.d("RegisterFragment", "Saved user full name: " + fullName);

                    // ✅ Optional debug toast
                    Toast.makeText(getContext(), "Welcome, " + fullName, Toast.LENGTH_SHORT).show();

                    // ✅ Navigate to LoginFragment
                    getActivity().getSupportFragmentManager()
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
}
