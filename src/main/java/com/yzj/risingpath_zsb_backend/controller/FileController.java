package com.yzj.risingpath_zsb_backend.controller;

import com.yzj.risingpath_zsb_backend.common.BaseResponse;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.common.ResultUtils;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import com.yzj.risingpath_zsb_backend.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

@Api(tags = "阿里云文件管理")
@CrossOrigin // 跨域
@RestController
@Slf4j
@RequestMapping("/api/file")
public class FileController {
    @Resource
    private FileService fileService;

    /**
     * 文件上传
     *
     * @param file
     * @param module
     * @return
     */
    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public BaseResponse<String> upload(@ApiParam(value = "文件", required = true) @RequestParam("file") MultipartFile file,
                                       @ApiParam(value = "模块", required = true) @RequestParam("module") String module) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String uploadUrl = fileService.upload(inputStream, module, originalFilename);
            //返回R对象
            return ResultUtils.success(uploadUrl, "文件上传成功！");
        } catch (IOException e) {
            log.error("文件上传错误：{}", ExceptionUtils.getStackTrace(e));
            throw new BusinessException(ErrorCode.UPLOAD_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "删除oss文件")
    @DeleteMapping("/remove")
    public BaseResponse<String> remove(@ApiParam(value = "要删除的文件路径", required = true) @RequestParam("url") String url) {

        fileService.remove(url);
        return ResultUtils.success("删除文件成功！");
    }

}
