package com.example.restful.controller;

import com.example.restful.bean.HttpResponse;
import com.example.restful.exception.FileNotFoundException;
import com.example.restful.exception.FileStorageException;
import com.example.restful.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    @Value("${file.uploadUrl}")
    private String uploadUrl;

    //multipart/form-data 格式发送数据时各个部分分隔符的前缀,必须为 --
    private static final String BOUNDARY_PREFIX = "--";

    //回车换行,用于一行的结尾
    private static final String LINE_END = "\r\n";

    @javax.annotation.Resource
    private FileUploadService fileUploadService;

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

    /**
     * 此功能接口是调用另一个服务中上传文件的RESTFUL接口来实现文件上传
     * 另一个接口协议： post请求，接口方法类似于 public String upload(@PathVariable @NotEmpty String path, @RequestParam("file") @NotNull MultipartFile multipartFile){……}
     *
     * 主要参考：https://blog.csdn.net/wohaqiyi/article/details/77621517
     * 相关资料：
     * https://blog.csdn.net/liqing0013/article/details/95514125
     * https://blog.csdn.net/qq_41117947/article/details/79361094
     * https://stackoverflow.com/questions/34276466/simple-httpurlconnection-post-file-multipart-form-data-from-android-to-google-bl
     *
     * @author: QL Zhang
     * @time: 2020/7/23 16:40
     */
    @PostMapping("/crossupload")
    public String crossUpload(@RequestParam("file") @NotNull MultipartFile multipartFile){
        // check upload file
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String result = "";
        final String newLine = "\r\n";
        final String boundaryPrefix = "--";
        //定义数据分割线
        String BOUNDARY = "========7d4a6d158c9";
        //服务器的域名
        try {
            //创建连接
            URL url = new URL(uploadUrl);
            //声明一个抽象类URLConnection的引用urlConnection,此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类的子类HttpURLConnection,故此处最好将其转化为HttpURLConnection类型的对象,以便用到HttpURLConnection更多的API.如下:
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设定请求方式为"POST"，默认为"GET"
            connection.setRequestMethod(String.valueOf(HttpMethod.POST));
            //发送post请求必须设置如下两行
            //设置是否向HttpUrlConnction输出，因为这个是POST请求，参数要放在http正文内，因此需要设为true，默认情况下是false
            connection.setDoOutput(true);
            //设置是否向HttpUrlConnection读入，默认情况下是true
            connection.setDoInput(true);
            //POST请求不能使用缓存（POST不能被缓存）
            connection.setUseCaches(false);
            //设置请求头参数 长连接、字符编码、
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);

            //connect()函数会根据HttpURLConnection对象的配置值 生成http头部信息，因此在调用connect函数之前，就必须把所有的配置准备好
            //connection.connect();
            //HttpURLConnection是基于HTTP协议的，其底层通过socket通信实现。如果不设置超时（timeout），在网络异常的情况下，可能会导致程序僵死而不继续往下执行。
            //connection.setConnectTimeout(20*1000);//设置连接主机超时（单位：毫秒）
            //connection.setReadTimeout(20*1000);//设置从主机读取数据超时（单位：毫秒）
            //正文的内容是通过outputStream流写入的，实际上outputStream不是一个网络流，充其量是个字符串流，往里面写入的东西不会立即发送到网络，
            //而是存在于内存缓冲区中，待outputStream流关闭时，根据输入的内容生成http正文。至此，http请求的东西已经全部准备就绪
            OutputStream out = new DataOutputStream(connection.getOutputStream());

            //上传文件
            StringBuilder sb = new StringBuilder();
            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);

            // 文件参数,photo参数名可以随意修改
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + fileName
                    + "\"" + newLine);
            sb.append("Content-Type:application/octet-stream");
            // 参数头设置完以后需要两个换行，然后才是参数内容
            sb.append(newLine);
            sb.append(newLine);

            // 将参数头的数据写入到输出流中
            out.write(sb.toString().getBytes());

            // 数据输入流,用于读取文件数据
            DataInputStream in = new DataInputStream(multipartFile.getInputStream());
            byte[] bufferOut = new byte[1024*8];
            int bytes = 0;
            // 每次读8KB数据,并且将文件数据写入到输出流中
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            // 最后添加换行
            out.write(newLine.getBytes());
            in.close();

            // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
            byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine)
                    .getBytes();
            // 写上结尾标识
            out.write(end_data);
            out.flush();
            out.close();

            // 定义BufferedReader输入流来读取URL的响应
            // 对outputStream的写操作，又必须要在inputStream的读操作之前
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));// <===注意，实际发送请求的代码段就在这里
            String line;
            while ((line = reader.readLine()) != null) {
                result += line; //这里读取的是上边url对应的上传文件接口的返回值，读取出来后，然后接着返回到前端，实现接口中调用接口的方式
            }
            log.info("文件上传响应结果：{}", result);
        } catch (IOException e) {
            log.error("上传文件的POST请求出现异常", e);
        }
        return result;
    }

    @PostMapping("/crossupload2")
    public HttpResponse crossUpload2(@RequestParam("file") @NotNull MultipartFile multipartFile){
        HttpResponse response;
        // check upload file
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        HttpURLConnection conn;
        //定义数据分割线，可以任意设置，这里设置为 MyBoundary+ 时间戳（尽量复杂点，避免和正文重复）
        String boundary = "MyBoundary" + System.currentTimeMillis();
        try {
            conn = fileUploadService.getHttpURLConnection(uploadUrl, null);
            //设置 Content-Type 为 multipart/form-data; boundary=${boundary}
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            // 数据输入流,用于读取文件数据
            DataInputStream in = new DataInputStream(multipartFile.getInputStream());
            //写文件类型的表单参数
            fileUploadService.writeFile("file", fileName, boundary, out, in);
            out.flush();
            in.close();
            out.close();
        } catch (IOException e) {
            log.error("上传文件的POST请求出现异常", e);
            response = new HttpResponse(500, e.getMessage());
            return response;
        }
        return fileUploadService.getHttpResponse(conn);
    }


    @PostMapping("/crossdownload")
    public Object crossDownload(@RequestParam("file") @NotNull MultipartFile multipartFile){
        return null;
    }
}
