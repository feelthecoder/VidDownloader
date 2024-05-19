package com.feelthecoder.viddownloader.webservices;

import static com.feelthecoder.viddownloader.webservices.DownloadVideosMain.Mcontext;
import static com.feelthecoder.viddownloader.webservices.DownloadVideosMain.fromService;
import static com.feelthecoder.viddownloader.webservices.DownloadVideosMain.pd;

import android.content.Context;
import android.os.AsyncTask;

import com.feelthecoder.viddownloader.R;
import com.feelthecoder.viddownloader.utils.DownloadFileMain;
import com.feelthecoder.viddownloader.utils.iUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class KwaiDownloader {

    private final Context context;
    private final String VideoURL;

    public KwaiDownloader(Context context, String vid) {
        this.context = context;
        VideoURL = vid;
    }

    public void DownloadVideo() {
        new CallKwaiData().execute(VideoURL);
    }


    public static class CallKwaiData extends AsyncTask<String, Void, Document> {
        Document RoposoDoc;
        String VideoUrl = "";

        public Document doInBackground(String... strArr) {
            try {
                this.RoposoDoc = Jsoup.connect(strArr[0]).get();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return this.RoposoDoc;
        }

        public void onPostExecute(Document document) {
            boolean isSecon = false;

            try {

                if (!fromService) {

                    pd.dismiss();
                }
                System.out.println("myresponseis111 exp166 " + document);

                String data = "";
//                https://s.kw.ai/p/jJjBVdyQ
                Elements elements = document.select("video");
                for (Element element : elements) {
                    if (element.attr("src").contains("http")) {

                        String replaceString = element.getElementsByTag("video").attr("src");
                        System.out.println("myresponseis111 list_of_qualities" + replaceString);
                        DownloadFileMain.startDownloading(Mcontext, replaceString, "Kwai_" + System.currentTimeMillis(), ".mp4");


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
