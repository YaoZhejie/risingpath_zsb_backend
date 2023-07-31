package com.yzj.risingpath_zsb_backend.controller;

import com.yzj.risingpath_zsb_backend.common.BaseResponse;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.common.ResultUtils;
import com.yzj.risingpath_zsb_backend.domain.User;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import com.yzj.risingpath_zsb_backend.service.UserService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;




@RestController
public class ImageController {
    private static final String UPLOAD_DIR = "static/";
    @javax.annotation.Resource
    private UserService userService;
    /**
     * 更新用户头像
     */
    @PostMapping(value = "user/updateUserPic")
    public BaseResponse<User> updateUserPic(@RequestParam("file") MultipartFile image, @RequestParam("id") Long id) throws Exception {
        if (image.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //加上时间防止重复
        String fileName = System.currentTimeMillis() + StringUtils.cleanPath(image.getOriginalFilename());
        String uploadPath = UPLOAD_DIR + fileName;
        // 通过传入的上传路径创建一个路径对象
        Path filePath = Paths.get(uploadPath).toAbsolutePath().normalize();
        // 将路径对象转换为文件对象
        File destFile = filePath.toFile();
        // 将上传的图像文件保存到目标文件中
        image.transferTo(destFile);

        //存储到数据库里的文件地址
        String storeAvatorPath = fileName;
        //头像文件实际地址
        String PicUrl = "static/" + userService.selectById(id).getAvatarUrl();
        User user = null;
        try {
            File file_pic = new File(PicUrl);
            //如果文件存在则删除
            if (file_pic.exists()) {
                file_pic.delete();
            }
            user = new User();
            user.setUserId(id);
            user.setAvatarUrl(storeAvatorPath);
            boolean flag = userService.updateById(user);
            if (flag) {
                return ResultUtils.success(user);
            }

        } catch (Exception e) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "上传失败");
        } finally {
            return ResultUtils.success(user);
        }
    }

    @GetMapping("/avatorImages/{imageName}")
    public byte[] getImage(@PathVariable("imageName") String imageName) throws IOException {
        Resource resource = new ClassPathResource("static/" + imageName);
        resource.getURI();
        InputStream inputStream = resource.getInputStream();
        return FileCopyUtils.copyToByteArray(inputStream);
    }
}