package com.example.finalproj_2022_1;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoginFragment extends Fragment {
    EditText tvId ;
    EditText tvPwd;
    Button btnLogin;
    SharedPreferences preferences;
    TextView tvUname;
    View navHeader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        tvId = (EditText) view.findViewById(R.id.tvId);
        tvPwd = (EditText) view.findViewById(R.id.tvPwd);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = "http://172.30.1.43:8080/AndroidAppEx/android2/login.jsp";

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String url2 = url + "?id=" + tvId.getText().toString() + "&pwd=" + tvPwd.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        url2,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.trim().equals("아이디비번확인")) {
                                    Toast.makeText(getContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                                } else {
                                    preferences = getContext().getSharedPreferences("userInfo", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("name", response.trim());
                                    editor.commit();

                                    Toast.makeText(getContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                /*에러부분*/
                            }
                        });
                requestQueue.add(stringRequest);
            }
        });
        return view;
    }
}