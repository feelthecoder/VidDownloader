package com.feelthecoder.viddownloader.interfaces;

public interface VideoDownloader {

    String getVideoId(String link);

    void DownloadVideo();
}
