package com.example.lostandfound.View.Authentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lostandfound.Controller.Authentication.AuthenticateController;
import com.example.lostandfound.R;

import static com.example.lostandfound.NameClass.logIn;

public class SignUpFragment extends Fragment {
    View rootView;
    EditText editemail;
    EditText editpass;
    EditText editConfirmPass;
    Button btnlogin;
    Button btnSignup;
    CallBackInterface callBackInterface;
    AuthenticateController controller;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.sign_up,container,false);
        initUi();
        return rootView;
    }

    public void setCallBackInterface(CallBackInterface callBackInterface)
    {
        this.callBackInterface=callBackInterface;
    }

    private void initUi()
    {
        editemail=rootView.findViewById(R.id.email);
        editpass=rootView.findViewById(R.id.password);
        editConfirmPass=rootView.findViewById(R.id.ConfirmPassword);

        btnlogin=rootView.findViewById(R.id.login);
        btnSignup=rootView.findViewById(R.id.SignUp);

        String emailText = editemail.getText().toString();
        String passwordText = editpass.getText().toString();

        controller = new AuthenticateController(getActivity(),emailText,passwordText);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callBackInterface!=null)
                {
                    callBackInterface.callBackMethod(logIn);
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.signUp();
            }
        });
    }
}