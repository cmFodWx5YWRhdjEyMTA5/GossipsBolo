package com.example.yogi.gossipsbolo.exception;
public class GossipsBoloException extends Exception {
    public final static int ERROR_NETWORK_NOT_FOUND = 404;
    private int code;
    private String message;

    public GossipsBoloException(int code) {
        this(code, "Something wrong!!!.Please try after sometime.");
    }

    public GossipsBoloException(String message) {
        this(-1, message);
    }

    public GossipsBoloException(int code, String message) {
        this.code = code;
        this.message = message != null ? message : this.message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
