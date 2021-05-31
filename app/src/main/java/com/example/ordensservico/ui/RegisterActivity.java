package com.example.ordensservico.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ordensservico.R;
import com.example.ordensservico.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputLayout et_user;
    private TextInputLayout et_email;
    private TextInputLayout et_password;
    private TextInputLayout et_password_again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        et_user = findViewById(R.id.et_user);
        et_email = findViewById(R.id.et_email_login);
        et_password = findViewById(R.id.et_password_login);
        et_password_again = findViewById(R.id.et_password_again);

        Button register = findViewById(R.id.btn_register);
        register.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String user = et_user.getEditText().getText().toString();
        String email = et_email.getEditText().getText().toString();
        String password = et_password.getEditText().getText().toString();
        String password_again = et_password_again.getEditText().getText().toString();

        if (user.isEmpty()) {
            et_user.setError("Campo usuário nulo");
            et_user.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            et_email.setError("Campo e-mail nulo");
            et_email.requestFocus();
            return;
        }

        if (password.isEmpty() || password.length() < 6) {
            et_password.setError("Campo senha nulo ou menor que 6");
            et_password.requestFocus();
            return;
        }

        if (!et_password.getEditText().getText().toString()
                .equals(et_password_again.getEditText().getText().toString()) ||
            password_again.isEmpty()) {
            et_password_again.setError("Campo repetir senha invalido");
            et_password_again.requestFocus();
            return;
        }

        //Fazer a criação do usuário para entrar no app

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    User user1 = new User(user, email);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Usuário criado", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }  else {
                                Toast.makeText(RegisterActivity.this,
                                        "Erro ao criar usuário", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Erro ao criar usuário",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}