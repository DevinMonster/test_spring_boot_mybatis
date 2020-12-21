package com.devin.controller;

import com.devin.dto.APIResult;
import com.devin.dto.ResultGenerator;
import com.devin.utils.QiniuUtils;
import com.devin.utils.Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/qiniu")
public class QiNiuController {

    public static final String prefix = "http://qlgqf1fbm.hn-bkt.clouddn.com/";

    /**
     * 用来上传文件, 允许跨域
     * @param file 前端传过来的文件
     * @return 统一JSON结果
     */
    @PostMapping("/upload")
    @CrossOrigin
    public APIResult upload(@RequestBody MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String newName = QiniuUtils.byteUpLoad(file.getBytes(), Utils.generateUUName(originalFilename));
        return ResultGenerator.genSuccess(prefix + newName);
    }
}
