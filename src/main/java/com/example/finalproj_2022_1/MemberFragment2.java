package com.example.finalproj_2022_1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class MemberFragment2 extends Fragment {
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    ArrayList<Users1> users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_member, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        String url1 = "http://172.30.1.43:8080/AndroidAppEx/android2/memberList2.jsp";

        requestQueue = Volley.newRequestQueue(getContext());
        users = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        response = response.trim().substring(3);
                        try {
                            JSONObject jobj = new JSONObject(response);
                            JSONArray jArray = jobj.getJSONArray("users");

                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject jObj = jArray.getJSONObject(i);
                                String name = jObj.getString("name");
                                String mail = jObj.getString("mail");
                                users.add(new Users1("u00" + (i + 1), name, mail));
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setAdapter(new MemberRecyclerViewAdapter(users));
                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "error:======= " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(stringRequest);

        return view;
    }

    class MemberRecyclerViewAdapter extends RecyclerView.Adapter<MemberRecyclerViewAdapter.ViewHolder> {
        ArrayList<Users1> data;

        public MemberRecyclerViewAdapter(ArrayList<Users1> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.getTvUserName().setText(data.get(position).getName());
            holder.getTvUserMail().setText(data.get(position).getMail());

            //????????? ????????? ????????? ??? ?????????
            Random random = new Random();
            if (random.nextInt() % 2 == 0)
                holder.getIvUserFace().setImageResource(R.drawable.pokemonball);
            else
                holder.getIvUserFace().setImageResource(R.drawable.pngwing);
        }
        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final ImageView ivUserFace;
            private final TextView tvUserName;
            private final TextView tvUserMail;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                this.ivUserFace = (ImageView) itemView.findViewById(R.id.ivUserFace);
                this.tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
                this.tvUserMail = (TextView) itemView.findViewById(R.id.tvUserMail);
            }

            public ImageView getIvUserFace() {
                return ivUserFace;
            }

            public TextView getTvUserName() {
                return tvUserName;
            }

            public TextView getTvUserMail() {
                return tvUserMail;
            }
        }
    }
}