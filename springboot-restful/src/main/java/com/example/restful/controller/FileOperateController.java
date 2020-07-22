package com.example.restful.controller;

import com.example.restful.exception.FileNotFoundException;
import com.example.restful.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * 参考其他代码自己整理的文件上传下载接口
 * upload1和download1参考：https://github.com/HaiTang-8/File-Upload/tree/master
 * upload2和download2参考：https://github.com/callicoder/spring-boot-file-upload-download-rest-api-example
 * 下载文件名中文乱码解决方案参考 https://www.codeleading.com/article/29542194008/
 * @author: QL Zhang
 * @time: 2020/7/22 17:52
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileOperateController {

    /**
     * 文件上传（不友好不实用）
     *
     * @param desFile 上传后要将其内容写入的文件全路径
     *
     * @author: QL Zhang
     * @time: 2020/7/22 13:02
     */
    @PostMapping("/upload1")
    public void upload1(@RequestParam("file") @NotNull MultipartFile multipartFile, @RequestParam(value = "desFile", required = true) String desFile){
        //获取文件类型
        String fileContentType = multipartFile.getContentType();
        //获取文件的原始名称
        String originFileName = multipartFile.getOriginalFilename();
        //获取文件的后缀
        String extension = "." + FilenameUtils.getExtension(originFileName);
        //获取文件的大小
        long size = multipartFile.getSize();
        //fileContentType = application/x-sh, originFileName = daemon1.sh, extension = .sh, size = 5739
        log.info(String.format("fileContentType = %s, originFileName = %s, extension = %s, size = %d", fileContentType, originFileName, extension, size));
        try {
            //将上传的文件内容写入指定的文件中
            multipartFile.transferTo(new File(desFile));
            log.info("文件{}的内容上传并写入文件{}成功", originFileName, desFile);
            //multipartFile.transferTo(Paths.get(desFile));
        } catch (IOException e) {
            log.error("上传文件失败", e);
        }
    }

    /**
     * 下载文件
     *
     * @param filePath 所要下载的文件的目录
     *
     *
     * @author: QL Zhang
     * @time: 2020/7/22 15:28
     */ 
    @GetMapping("/download1")
    public void download1(@RequestParam(value = "filePath", required = true) String filePath, HttpServletResponse response) throws IOException {
        // 首先判断下载的文件是否存在
        if(!Files.exists(Paths.get(filePath))){
            log.error("下载的文件路径不存在");
            throw new FileNotFoundException("下载的文件路径不存在");
        }

        // 获取下载的文件名字
        String outFileName = Paths.get(filePath).getFileName().toString();
        log.info("下载文件的完整路径为{},文件名为{}", filePath, outFileName);

        //1、获取文件输入流
        FileInputStream is = new FileInputStream(new File(filePath));

        //2、设置头
        // 告诉浏览器输出内容为流
        response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
        // Content-Disposition中指定的类型是文件的扩展名，并且弹出的下载对话框中的文件类型图片是按照文件的扩展名显示的，点保存后，文件以filename的值命名，保存类型以Content中设置的为准。注意：在设置Content-Disposition头字段之前，一定要设置Content-Type头字段。
        // inline:默认值，表示回复中的消息体会以页面的一部分或者整个页面的形式展示; attachment:意味着消息体应该被下载到本地；大多数浏览器会呈现一个“保存为”的对话框，将filename的值预填为下载后的文件名，假如它存在的话。
        // 该方式下载的文件名存在中文乱码问题
        //response.setHeader("Content-Disposition","attachment;filename=" + outFileName);
        // 解决下载文件名中文乱码
        try {
            // 方法1： 设置下载的文件的名称-该方式已解决中文乱码问题，swagger,postman看到的是%...等，浏览器直接输url,OK
            //response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(outFileName, String.valueOf(StandardCharsets.UTF_8)));
            // 方法2： 设置下载的文件的名称-该方式已解决中文乱码问题，swagger,postman看到的是%...等，浏览器直接输url,OK （把文件名按UTF-8取出并按ISO8859-1编码，保证弹出窗口中的文件名中文不乱码，中文不要太多，最多支持17个中文，因为header有150个字节限制。）
            //response.setHeader("Content-Disposition", "attachment;filename=" + new String(outFileName.getBytes("UTF-8"), "ISO8859-1"));
            // 方法3：设置下载的文件的名称-该方式已解决中文乱码问题，postman可以，，swagger看到的是%...等，浏览器直接输url,OK
            response.setHeader("Content-Disposition", "attachment;filename=" + outFileName + ";filename*=utf-8''" + URLEncoder.encode(outFileName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("下载文件时导入数据失败", e);
        }

        //3、获取响应输出流
        ServletOutputStream os = response.getOutputStream();

        //4、文件拷贝
        IOUtils.copy(is,os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }

    /**
     * 通过指定路径直接上传文件（同upload1相比，是将上传的文件名取出然后拼接到指定路径上然后进行的流拷贝）
     * @param path 所要上传到的路径
     *
     *
     * @author: QL Zhang
     * @time: 2020/7/22 13:16
     */
    @PostMapping("/upload2")
    public void upload2(@RequestParam("file") @NotNull MultipartFile multipartFile, @RequestParam(value = "path", required = false) String path){
        // Normalize file name
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")){
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = Paths.get(path).resolve(fileName);
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("文件上传成功");
        } catch (IOException e) {
            log.error("上传文件失败", e);
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
    }

    /**
     * 下载文件
     * @param filePath 所要下载的文件的目录
     *
     * @author: QL Zhang
     * @time: 2020/7/22 17:51
     */
    @GetMapping("/download2")
    public ResponseEntity<Resource> download2(@RequestParam(value = "filePath", required = true) String filePath, HttpServletRequest request) {
        Resource resource = null;
        // Load file as Resource
        try {
            Path file = Paths.get(filePath).normalize();
            resource = new UrlResource(file.toUri());
            if (!resource.exists()){
                throw new FileNotFoundException("File not found " + filePath);
            }
        }catch (MalformedURLException e){
            throw new FileNotFoundException("File not found " + filePath, e);
        }

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        //下载文件名为中文时会出现乱码，，解决方法如上download1接口
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
