package com.wcf.server.controller;

import com.wcf.server.base.log.Log;
import com.wcf.server.base.response.ResultBody;
import com.wcf.server.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {
    private AttachmentService attachmentService;

    @Autowired
    private void autowired(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("images")
    @Log(value = "上传图片附件", saveParams = false)
    public ResultBody addImage(@RequestParam("file") MultipartFile file) {
        return ResultBody.success(attachmentService.addImage(file));
    }
}