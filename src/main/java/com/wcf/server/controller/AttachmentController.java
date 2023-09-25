package com.wcf.server.controller;

import com.wcf.server.base.log.Log;
import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.AttachmentService;
import com.wcf.server.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("images")
    @Log(value = "上传图片", saveParams = false)
    public ResultBody addImage(@RequestParam("file") MultipartFile file) {
        return ResultBody.success(attachmentService.addImage(file));
    }
}