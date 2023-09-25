package com.wcf.server.service;

import com.wcf.server.base.response.BizException;
import com.wcf.server.base.response.CommonEnum;
import com.wcf.server.model.Attachment;
import com.wcf.server.model.User;
import com.wcf.server.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AttachmentService {
    private final UserService userService;
    private final AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentService(UserService userService, AttachmentRepository attachmentRepository) {
        this.userService = userService;
        this.attachmentRepository = attachmentRepository;
    }

    @Value("${spring.servlet.attachment.path}")
    private String attachmentPath;
    @Value("${spring.servlet.attachment.url}")
    private String attachmentUrl;

    public Attachment addImage(MultipartFile file) {
        // 原始文件名
        String filename = file.getOriginalFilename();
        Set<String> allowSuffix = new HashSet<>(Arrays.asList("jpg", "jpeg", "png", "gif"));
        String suffix = checkAndGetSuffix(filename, allowSuffix);

        return saveFile(file, "image", filename, suffix);
    }

    public Attachment addExcel(MultipartFile file) {
        String filename = file.getOriginalFilename();
        Set<String> allowSuffix = new HashSet<>(Arrays.asList("xlsx","xls"));
        String suffix = checkAndGetSuffix(filename, allowSuffix);

        return saveFile(file, "excel", filename, suffix);
    }

    private Attachment saveFile(MultipartFile file, String type, String filename, String suffix) {
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            User user = userService.findMe();
            String folder = type + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
            String newFilename = UUID.randomUUID() + "." + suffix;

            // 若目录不存在则创建目录
            File fullFolder = new File(attachmentPath + folder);
            if (!fullFolder.exists()) {
                if (!fullFolder.mkdirs()) throw new BizException(CommonEnum.FAIL_TO_CREATE_DIR);
            }

            // 创建 model，同时通过获取图片宽高属性来进一步检查文件是否为图片文件
            Attachment attachment = new Attachment();
            attachment.setUploader(user);
            attachment.setFileName(filename);
            attachment.setFileType(type);
            if (type.equalsIgnoreCase("image")) {
                attachment.setImageWidth(bufferedImage.getWidth());
                attachment.setImageHeight(bufferedImage.getHeight());
            }
            attachment.setLink(attachmentUrl + folder + newFilename);

            // 保存文件并创建数据
            File newFile = new File(fullFolder, newFilename);
            file.transferTo(newFile);
            attachmentRepository.save(attachment);
            return attachment;
        } catch (Exception e) {
            throw new BizException(CommonEnum.INTERNAL_SERVER_ERROR);
        }
    }

    private String checkAndGetSuffix(String filename, Set<String> allowSuffix) {
        // 文件格式校验
        if (filename == null) {
            throw new BizException(CommonEnum.NOT_FOUND_FILE_NAME);
        }
        int lastPoint = filename.lastIndexOf(".");
        String suffix;// 后缀名
        if (lastPoint == -1 || (suffix = filename.substring(lastPoint + 1)).isEmpty()) {
            throw new BizException(CommonEnum.NOT_ALLOW_SUFFIX);
        }

        if (!allowSuffix.contains(suffix.toLowerCase())) {
            throw new BizException(CommonEnum.NOT_ALLOW_SUFFIX);
        }

        return suffix;
    }
}
