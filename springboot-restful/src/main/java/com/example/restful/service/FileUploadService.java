package com.example.restful.service;

import com.example.restful.bean.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Slf4j
@Service
public class FileUploadService {

    //multipart/form-data 格式发送数据时各个部分分隔符的前缀,必须为 --
    private static final String BOUNDARY_PREFIX = "--";

    //回车换行,用于一行的结尾
    private static final String LINE_END = "\r\n";

    /**
     * 获得连接对象
     *
     * @param urlStr
     * @param headers
     * @return
     * @throws IOException
     */
    public HttpURLConnection getHttpURLConnection(String urlStr, Map<String, Object> headers) throws IOException {
        //创建连接
        URL url = new URL(urlStr);
        //声明一个抽象类URLConnection的引用urlConnection,此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类的子类HttpURLConnection,故此处最好将其转化为HttpURLConnection类型的对象,以便用到HttpURLConnection更多的API.如下:
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //设定请求方式为"POST"，默认为"GET"
        connection.setRequestMethod(String.valueOf(HttpMethod.POST));
        //发送post请求必须设置如下两行
        //允许输出流.设置是否向HttpUrlConnction输出，因为这个是POST请求，参数要放在http正文内，因此需要设为true，默认情况下是false
        connection.setDoOutput(true);
        //允许输入流.设置是否向HttpUrlConnection读入，默认情况下是true
        connection.setDoInput(true);
        //POST请求不能使用缓存（POST不能被缓存）
        connection.setUseCaches(false);
        //设置超时时间
        connection.setConnectTimeout(50000);
        connection.setReadTimeout(50000);
        //设置请求头参数
        //设置编码 utf-8
        connection.setRequestProperty("Charset", "UTF-8");
        //设置为长连接
        connection.setRequestProperty("connection", "keep-alive");

        //设置其他自定义 headers
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, Object> header : headers.entrySet()) {
                connection.setRequestProperty(header.getKey(), header.getValue().toString());
            }
        }

        return connection;
    }

    /**
     * 写文件类型的表单参数
     *
     * @param paramName 参数名
     * @param fileName  文件路径
     * @param boundary  分隔符
     * @param out
     * @throws IOException
     */
    public void writeFile(String paramName, String fileName, String boundary,
                                  DataOutputStream out, DataInputStream in) {
        StringBuilder sb = new StringBuilder();
        // 写分隔符--${boundary}，并回车换行
        sb.append(BOUNDARY_PREFIX).append(boundary).append(LINE_END);

        /**
         * 写描述信息(文件名设置为上传文件的文件名)：
         * 写 Content-Disposition: form-data; name="参数名"; filename="文件名"，并回车换行
         * 写 Content-Type: application/octet-stream，并两个回车换行  参数头设置完以后需要两个换行，然后才是参数内容
         */
        sb.append(String.format("Content-Disposition: form-data; name=\"%s\"; filename=\"%s\"", paramName, fileName))
          .append(LINE_END)
          .append("Content-Type:application/octet-stream")
          .append(LINE_END)
          .append(LINE_END);
        try {
            // 将参数头的数据写入到输出流中
            out.write(sb.toString().getBytes());

            byte[] bufferOut = new byte[1024*8];
            int bytes = 0;
            // 每次读8KB数据,并且将文件数据写入到输出流中
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            // 最后添加换行
            out.write(LINE_END.getBytes());
            // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
            StringBuilder end_data = new StringBuilder();
            end_data.append(BOUNDARY_PREFIX).append(boundary).append(BOUNDARY_PREFIX).append(LINE_END);
            // 写上结尾标识
            out.write(end_data.toString().getBytes());
        } catch (IOException e) {
            log.error("写文件类型的表单参数异常", e);
        }
    }

    /**
     * 定义BufferedReader输入流来读取URL的响应
     *
     *
     * @author: QL Zhang
     * @time: 2020/7/28 18:14
     */
    public HttpResponse getHttpResponse(HttpURLConnection conn) {
        HttpResponse response;
        //对outputStream的写操作，又必须要在inputStream的读操作之前
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            int responseCode = conn.getResponseCode();
            StringBuilder responseContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                ////这里读取的是上边url对应的上传文件接口的返回值，读取出来后，然后接着返回到前端，实现接口中调用接口的方式
                responseContent.append(line);
            }
            log.info("文件上传响应结果：{}", responseContent.toString());
            response = new HttpResponse(responseCode, responseContent.toString());
        } catch (Exception e) {
            log.error("文件上传接口获取HTTP响应异常！", e);
            response = new HttpResponse(500, e.getMessage());
        }
        return response;
    }

}
