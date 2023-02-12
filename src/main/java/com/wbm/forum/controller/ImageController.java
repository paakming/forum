package com.wbm.forum.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

/**
 * @Author：Ming
 * @Date: 2022/12/9 22:08
 */
@RestController
@RequestMapping("/img")
public class ImageController {

    @PostMapping("/upload")
    public Object ImageUpload(@RequestParam("file") MultipartFile file){
        String path = "http://localhost:8080/image/";
        JSONObject obj = new JSONObject();
        if (file.isEmpty()){
            obj.set("errno",1);
            obj.set("message","失败信息");
            return obj;
        }
        String uploadPath = "F://fileData";
        File folder = new File(uploadPath);
        if (!folder.exists()){
            folder.mkdir();
        }
        String originName = file.getOriginalFilename();
        String type = FileUtil.extName(originName);
        String uuid = IdUtil.fastSimpleUUID() + StrUtil.DOT+type;
        try {
            file.transferTo(new File(folder,uuid));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = path+uuid;
        obj.set("errno",0);
        JSONObject data = new JSONObject();
        data.set("url",url);
        data.set("href",url);
        obj.set("data",data);
        return obj;
    }
}
