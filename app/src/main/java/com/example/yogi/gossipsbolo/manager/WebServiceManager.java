package com.example.yogi.gossipsbolo.manager;

import java.util.HashMap;
import java.util.Map;


public class WebServiceManager {
    private Map<String, String> header = new HashMap<String, String>();

    /**
     * This method will add the headers
     */
    protected Map<String, String> getHeaders() {
        // put key and value
        return header;
    }
}
