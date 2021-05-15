package com.mehmetozanguven.springip2Location.handler;

public abstract class Ip2LocationDownloadHandler {

    private Ip2LocationDownloadHandler nextHandler;

    public abstract void handle(Ip2LocationDownloadHandlerObject handlerObject);

    public Ip2LocationDownloadHandler getNextHandler() {
        return nextHandler;
    }

    public void setNextHandler(Ip2LocationDownloadHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
