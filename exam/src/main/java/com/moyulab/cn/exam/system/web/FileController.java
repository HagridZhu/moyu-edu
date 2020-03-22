package com.moyulab.cn.exam.system.web;

import com.moyulab.cn.exam.common.BaseController;
import com.moyulab.cn.exam.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Api(tags={"文件接口"})
@RequestMapping("exam/file")
@RestController
@Slf4j
public class FileController extends BaseController {

    private final String UPLOAD_PATH = "/haige/static/moyu-edu/upload/";
    private final String CONTEXT_PATH = "/home/zhuzhihai" + UPLOAD_PATH;
    private final String FILE_PATH_PRE_FIX = "http://www.moyulab.cn" + UPLOAD_PATH;
    private Random random = new Random();


    @ApiOperation(value = "上传文件",notes = "上传什么都行，文件现在存在服务器的文件夹下/home/zhuzhihai/haige/static/moyu-edu/upload")
    @PostMapping("upload")
    public Result uploadFile(@RequestParam("file") MultipartFile[] files) {
        List<String> urlList = new ArrayList<>();
        Long userId = getUserId();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            log.info("用户{},{},上传文件，fileName={},fileSize={}KB", getUserName(), userId, fileName, file.getSize()/1024);
            // 上传并返回新文件名称
            try {
                String path = getFilePath(userId, fileName);
                File serverFile = new File(this.CONTEXT_PATH + path);
                // 创建文件夹
                if (!serverFile.getParentFile().exists()) {
                    serverFile.getParentFile().mkdirs();
                }
                // 名称重复则不重复创建
                if (!serverFile.exists()) {
                    FileOutputStream fos = new FileOutputStream(serverFile);
                    InputStream is = file.getInputStream();
                    StreamUtils.copy(is, fos);
                }
                urlList.add(this.FILE_PATH_PRE_FIX + path);
            }catch (Exception e){
                log.error("上传文件失败,fileName=" + fileName, e);
                return Result.error("上传文件失败,fileName=" + fileName);
            }

        }
        return Result.success("urlList", urlList);
    }

    /**
     * 获取配置的上传目录下的文件路径，按照用户id分文件夹，文件名用时间重命名
     * 规则：用户id/日期_随机数.文件后缀
     * @param userId 用户id
     * @param fileName 原文件名
     * @return
     */
    private String getFilePath(Long userId, String fileName){
        String[] split = fileName.split("\\.");
        String fileSuffix = split[split.length - 1];
       return userId + "/" + LocalDateTime.now() + "_" + random.nextInt(1000) + "." + fileSuffix;
    }

}
