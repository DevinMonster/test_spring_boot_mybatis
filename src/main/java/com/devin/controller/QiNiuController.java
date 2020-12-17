package com.devin.controller;

import com.devin.dto.APIResult;
import com.devin.dto.ResultGenerator;
import com.devin.utils.QiniuUtils;
import com.devin.utils.Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/qiniu")
public class QiNiuController {

    /**
     * 用来上传文件
     * @param file 前端传过来的文件
     * @return 统一JSON结果
     */
    @PostMapping("/upload")
    public APIResult upload(@RequestBody MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String newName = QiniuUtils.byteUpLoad(file.getBytes(), Utils.generateUUName(originalFilename));
        return ResultGenerator.genSuccess(newName);
    }
}
