/**
 * Author: 张俊杰
 * Create Time: 2020/7/10
 * Update Time: 2020/7/15
 */

package com.edu.whu.xiaomaivideo_backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/rest") // 通过服务器URL/rest http://……:8088/rest/
public class FileController {
    @Value("${constant.base-url}")
    private String url;

    @Value("${constant.pic-file-resource-path}")
    private String path;

    @CrossOrigin
    @PostMapping("api") // http://……:8088/rest/api 发POST请求 参数名file
    public @ResponseBody
    String fileUpload(@RequestParam(value = "file")MultipartFile file) throws Exception {
        File Folder = new File(path);
        File f = new File(Folder, UUID.randomUUID()
                + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));

        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String URL = url+"/api/file/" + f.getName();
            return URL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
