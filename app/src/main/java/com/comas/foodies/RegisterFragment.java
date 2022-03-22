package com.comas.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


//check if can delete this fragment


public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        TextView title = view.findViewById(R.id.titel_registerFragment_tv);
        TextView email = view.findViewById(R.id.email_registerFragment_tv);
        TextView password = view.findViewById(R.id.password_registerFragment_tv);
        TextView repeatPassword = view.findViewById(R.id.repeatPassword_registerFragment_tv);
        EditText emailInput = view.findViewById(R.id.editTextTextEmailAddress_registerFragment);
        EditText passwordInput = view.findViewById(R.id.editTextTextPassword_registerFragment);
        EditText repeatPasswordInput = view.findViewById(R.id.editTextTextRePassword_registerFragment);
        Button registerBtn = view.findViewById(R.id.resgisterBtn_registerFragment);



        return view;
    }
}