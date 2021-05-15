package com.mehmetozanguven.springip2Location.handler;

public class Ip2LocationDownloadHandlerObject {
    private boolean isFileDownloaded;
    private boolean isFileCanBeOpen;

    public Ip2LocationDownloadHandlerObject() {
        this.isFileDownloaded = false;
        this.isFileCanBeOpen = false;
    }

    public boolean isFileDownloaded() {
        return isFileDownloaded;
    }

    public void setFileDownloaded(boolean fileDownloaded) {
        isFileDownloaded = fileDownloaded;
    }

    public boolean isFileCanBeOpen() {
        return isFileCanBeOpen;
    }

    public void setFileCanBeOpen(boolean fileCanBeOpen) {
        isFileCanBeOpen = fileCanBeOpen;
    }
}
