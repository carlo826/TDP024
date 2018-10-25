/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.impl.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import se.liu.ida.tdp024.account.logic.api.utils.HTTPGet;

/**
 *
 * @author frazz
 */
public class HTTPGetImpl implements HTTPGet {

    @Override
    public String get(String urlStr) throws Exception {
        StringBuilder res = new StringBuilder();
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            res.append(line);
        }
        rd.close();
        return res.toString();
    }
    
}
