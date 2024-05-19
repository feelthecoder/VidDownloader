/*
 * *
 *  * Created by Debarun Lahiri on 2/27/23, 1:22 AM
 *  * Copyright (c) 2023 . All rights reserved.
 *  * Last modified 2/26/23, 11:10 PM
 *
 */

package com.feelthecoder.viddownloader.webservices;

import static com.feelthecoder.viddownloader.webservices.DownloadVideosMain.Mcontext;
import static com.feelthecoder.viddownloader.webservices.DownloadVideosMain.fromService;
import static com.feelthecoder.viddownloader.webservices.DownloadVideosMain.pd;

import android.content.Context;
import android.os.AsyncTask;

import androidx.appcompat.app.AlertDialog;

import com.feelthecoder.viddownloader.R;
import com.feelthecoder.viddownloader.utils.DownloadFileMain;
import com.feelthecoder.viddownloader.utils.iUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MashableDownloader {

    private final Context context;
    private final String VideoURL;

    public MashableDownloader(Context context, String vid) {
        this.context = context;
        VideoURL = vid;
    }

    public void DownloadVideo() {
        new CallmashableinData().execute(VideoURL);
    }


    public class CallmashableinData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36").get();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {


            try {

                if (!fromService) {

                    pd.dismiss();
                }

                String data = "";
                ArrayList<String> arrayList = new ArrayList<>();

                Elements elements = document.select("script");
                for (Element element : elements) {
                    if (element.attr("class").equals("playerMetadata")) {
                        //Save As you want to

                        JSONObject obj = new JSONObject(element.html());

                        JSONArray jsonArray = obj.getJSONObject("player").getJSONArray("sources");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject kk = jsonArray.getJSONObject(i);
                            if (kk.getString("file").contains(".mp4")) {

                                arrayList.add(kk.getString("file"));
                                System.out.println("myresponseis111 data " + kk.getString("file"));
                            }
                        }


                    }
                }


                CharSequence[] charSequenceArr = new CharSequence[arrayList.size()];

                charSequenceArr[0] = "1080p quality";
                charSequenceArr[1] = "720p quality";
                charSequenceArr[2] = "480p quality";


                    new AlertDialog.Builder(context).setTitle("Quality!").setItems(charSequenceArr, (dialogInterface, i) -> DownloadFileMain.startDownloading(context, arrayList.get(i), "Mashable_" + System.currentTimeMillis(), ".mp4")).setPositiveButton("OK", (dialogInterface, i) -> {
                        if (!fromService) {

                            pd.dismiss();
                        }
                    }).setCancelable(false).show();


            } catch (Exception unused) {
                System.out.println("myresponseis111 exp " + unused.getMessage());


                if (!fromService) {

                    pd.dismiss();
                }
                iUtils.ShowToast(Mcontext, Mcontext.getResources().getString(R.string.somthing));
            }
        }


    }


}
