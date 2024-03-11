package com.yzj.risingpath_zsb_backend.controller;

import com.yzj.risingpath_zsb_backend.common.BaseResponse;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.common.ResultUtils;
import com.yzj.risingpath_zsb_backend.domain.User;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import com.yzj.risingpath_zsb_backend.oss.service.FileService;
import com.yzj.risingpath_zsb_backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;




@RestController
@Slf4j
public class ImageController {
    @Autowired
    private ResourceLoader resourceLoader;

//    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;




    /**
     * 更新用户头像
     */
    @PostMapping(value = "user/updateUserPic")
    public BaseResponse<User> updateUserPic(@RequestParam("file") MultipartFile image, @RequestParam("id") Long id) throws Exception {
        if (image.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        try {
            InputStream inputStream = image.getInputStream();
            String originalFilename = image.getOriginalFilename();
            String uploadUrl = fileService.upload(inputStream, "images", originalFilename);
            User user = new User();
            user.setUserId(id);
            user.setAvatarUrl(uploadUrl);
            boolean flag = userService.updateById(user);
            if (flag) {
                return ResultUtils.success(user);
            }

        } catch (IOException e) {
            log.error("文件上传错误：{}", ExceptionUtils.getStackTrace(e));
            throw new BusinessException(ErrorCode.UPLOAD_ERROR, e.getMessage());
        }
        throw new BusinessException(ErrorCode.PARAMS_ERROR,"头像上传失败！");
    }

//    @GetMapping("/avatorImages/{imageName}")
//    public byte[] getImage(@PathVariable("imageName") String imageName) throws IOException {
//        Resource resource = new ClassPathResource("/static/" + "images/" + imageName);
//        resource.getURI();
//        InputStream inputStream = resource.getInputStream();
//        return FileCopyUtils.copyToByteArray(inputStream);
//    }
}