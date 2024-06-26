package com.feelthecoder.viddownloader.webservices;

import static com.feelthecoder.viddownloader.webservices.DownloadVideosMain.Mcontext;
import static com.feelthecoder.viddownloader.webservices.DownloadVideosMain.fromService;
import static com.feelthecoder.viddownloader.webservices.DownloadVideosMain.pd;

import android.content.Context;
import android.os.AsyncTask;

import com.feelthecoder.viddownloader.R;
import com.feelthecoder.viddownloader.utils.DownloadFileMain;
import com.feelthecoder.viddownloader.utils.iUtils;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class CoubDownloader {

    private final Context context;
    private final String VideoURL;

    public CoubDownloader(Context context, String vid) {
        this.context = context;
        VideoURL = vid;
    }

    public void DownloadVideo() {
        new CallcoubData().execute(VideoURL);
    }


    public static class CallcoubData extends AsyncTask<String, Void, Document> {
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
                    if (element.attr("id").equals("coubPageCoubJson")) {
                        //Save As you want to

                        JSONObject obj = new JSONObject(element.html());

                        String jsonArray = obj.getJSONObject("file_versions").getJSONObject("share").getString("default");


                        DownloadFileMain.startDownloading(Mcontext, jsonArray, "Coub_" + System.currentTimeMillis(), ".mp4");


                    }
                }


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
