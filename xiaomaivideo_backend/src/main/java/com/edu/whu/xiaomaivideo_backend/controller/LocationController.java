/**
 * Author: 张俊杰
 * Create Time: 2020/7/14
 * Update Time: 2020/7/18
 */

package com.edu.whu.xiaomaivideo_backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.edu.whu.xiaomaivideo_backend.model.AjaxResponse;
import com.edu.whu.xiaomaivideo_backend.model.Movie;
import com.edu.whu.xiaomaivideo_backend.utils.HttpUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/rest")
public class LocationController {
    @GetMapping( "/location")
    public @ResponseBody
    AjaxResponse getLocation(@RequestParam String location) throws IOException {

        String host = "https://regeo.market.alicloudapi.com";
        String path = "/v3/geocode/regeo";
        String method = "GET";
        String appcode = "3d3fec4112c840c781cbf70561046104";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("batch", "false");
        querys.put("callback", "callback");
        querys.put("extensions", "base");
        querys.put("homeorcorp", "homeorcorp");
        querys.put("location", location);
        querys.put("output", "output");
        querys.put("radius", "1000");
        querys.put("roadlevel", "roadlevel");


        HttpResponse response = null;
        try {
            response = HttpUtils.doGet(host, path, method, headers, querys);
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json= EntityUtils.toString(response.getEntity());
        JsonObject returnData =new JsonParser().parse(json.substring(9,json.length()-2)).getAsJsonObject();
        String finalLocation= returnData.getAsJsonObject("regeocode").get("formatted_address").getAsString();

        return AjaxResponse.success(finalLocation);
    }
}
