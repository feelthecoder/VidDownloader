/*
 * *
 *  * Created by Debarun Lahiri on 3/2/23, 12:11 AM
 *  * Copyright (c) 2023 . All rights reserved.
 *  * Last modified 3/1/23, 11:30 PM
 *
 */

package com.feelthecoder.viddownloader.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.feelthecoder.viddownloader.R;
import com.feelthecoder.viddownloader.databinding.ActivityAllSupportedBinding;
import com.feelthecoder.viddownloader.models.RecDisplayAllWebsitesModel;
import com.feelthecoder.viddownloader.utils.Constants;
import com.feelthecoder.viddownloader.utils.GlideApp;
import com.feelthecoder.viddownloader.utils.LocaleHelper;
import com.feelthecoder.viddownloader.utils.iUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class AllSupportedApps extends AppCompatActivity {

    ArrayList<RecDisplayAllWebsitesModel> recDisplayAllWebsitesModelArrayList;
    ArrayList<RecDisplayAllWebsitesModel> recDisplayAllWebsitesModelArrayListOtherWebsites;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAllSupportedBinding binding = ActivityAllSupportedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        try {
            if (iUtils.isNewUi) {
                binding.tt.toolbar.setVisibility(View.VISIBLE);
                setSupportActionBar(binding.tt.toolbar);
            } else {
                binding.tt1.toolbar1.setVisibility(View.VISIBLE);
                setSupportActionBar(binding.tt1.toolbar1);
            }

            Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.listandstatus));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


        recDisplayAllWebsitesModelArrayList = new ArrayList<>();
        recDisplayAllWebsitesModelArrayListOtherWebsites = new ArrayList<>();


        try {
            JSONArray jsonArray = new JSONArray(loadWebsiteJSONFromAsset());

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObjectdata = jsonArray.getJSONObject(i);


                System.out.println("dsakdjasdjasd " + jsonObjectdata.getString("website_name"));


                if (i < 8) {

                    if (!Constants.showyoutube && jsonObjectdata.getString("website_name").contains("YTD")) {
                        continue;
                    }

                    recDisplayAllWebsitesModelArrayList
                            .add(new RecDisplayAllWebsitesModel(jsonObjectdata.getString("website_pic_url"), jsonObjectdata.getString("website_name"), jsonObjectdata.getString("website_status"), jsonObjectdata.getString("show_status")));

                } else {

                    recDisplayAllWebsitesModelArrayListOtherWebsites
                            .add(new RecDisplayAllWebsitesModel(jsonObjectdata.getString("website_pic_url"), jsonObjectdata.getString("website_name"), jsonObjectdata.getString("website_status"), jsonObjectdata.getString("show_status")));
                }

            }

        } catch (Exception e) {
            System.out.println("dsakdjasdjasd " + e.getMessage());
        }


        RecDisplayAllWebsitesAdapter recDisplayAllWebsitesAdapter = new RecDisplayAllWebsitesAdapter(this, recDisplayAllWebsitesModelArrayList);

        binding.recviewSocialnetwork.setAdapter(recDisplayAllWebsitesAdapter);
        binding.recviewSocialnetwork.setLayoutManager(new GridLayoutManager(this, 4));


        RecDisplayAllWebsitesAdapter recDisplayAllWebsitesAdapter_otherwesites = new RecDisplayAllWebsitesAdapter(this, recDisplayAllWebsitesModelArrayListOtherWebsites);

        binding.recviewOthernetwork.setAdapter(recDisplayAllWebsitesAdapter_otherwesites);
        binding.recviewOthernetwork.setLayoutManager(new GridLayoutManager(this, 4));


        binding.videomorelistBtn.setOnClickListener(v -> {
            try {
                if (!isFinishing()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AllSupportedApps.this);
                    builder.setTitle("All Supported List");
                    builder.setMessage(loadAllSupportedFromAsset());
                    builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
                    builder.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }


    public String loadAllSupportedFromAsset() {
        String json = null;
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("allsupported.txt")));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            reader.close();
            json = sb.toString();

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }


    public String loadWebsiteJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("supported_websites.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleHelper.onAttach(newBase);
        super.attachBaseContext(newBase);
    }

    class RecDisplayAllWebsitesAdapter extends RecyclerView.Adapter<RecDisplayAllWebsitesAdapter.RecDisplayAllWebsitesViewHolder> {

        Activity context;
        ArrayList<RecDisplayAllWebsitesModel> recDisplayAllWebsitesModelArrayList;

        public RecDisplayAllWebsitesAdapter(Activity context, ArrayList<RecDisplayAllWebsitesModel> recDisplayAllWebsitesModelArrayList) {
            this.context = context;
            this.recDisplayAllWebsitesModelArrayList = recDisplayAllWebsitesModelArrayList;
        }

        @NonNull
        @Override
        public RecDisplayAllWebsitesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecDisplayAllWebsitesViewHolder(LayoutInflater.from(context).inflate(R.layout.recdisplayallwebsites_item, null, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecDisplayAllWebsitesViewHolder holder, @SuppressLint("RecyclerView") int position) {

            if (recDisplayAllWebsitesModelArrayList.get(position).getWebsitesshowtatue().equals("true")) {
                GlideApp.with(context)
                        .load(recDisplayAllWebsitesModelArrayList
                                .get(position)
                                .getWebsiteurl())
                        .placeholder(R.drawable.ic_appicon)
                        .into(holder.imgRecDisplayAllWebsites);

                holder.txtviewRecDisplayAllWebsites.setText(recDisplayAllWebsitesModelArrayList.get(position).getWebsitename());

                if (recDisplayAllWebsitesModelArrayList.get(position).getWebsitestatue().equals("false")) {
                    holder.statusimg.setVisibility(View.VISIBLE);
                } else {
                    holder.statusimg.setVisibility(View.INVISIBLE);

                }

                holder.imageIconView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, recDisplayAllWebsitesModelArrayList.get(position).getWebsitename(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return recDisplayAllWebsitesModelArrayList.size();
        }

        class RecDisplayAllWebsitesViewHolder extends RecyclerView.ViewHolder {

            private final ImageView imgRecDisplayAllWebsites;
            private final ImageView statusimg;
            private final TextView txtviewRecDisplayAllWebsites;
            private final LinearLayout imageIconView;

            public RecDisplayAllWebsitesViewHolder(View view) {
                super(view);
                imgRecDisplayAllWebsites = view.findViewById(R.id.img_RecDisplayAllWebsites);
                txtviewRecDisplayAllWebsites = view.findViewById(R.id.txtview_RecDisplayAllWebsites);
                statusimg = view.findViewById(R.id.statusimg);
                imageIconView = view.findViewById(R.id.layoutAppIcon);

            }


        }

    }
}