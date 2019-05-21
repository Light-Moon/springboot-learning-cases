package demo.springboot.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("/testFile")
public class FileController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	/**
	 * 单文件上传
	 * 后端文件传输格式应为 multipart/form-data
	 * @param file
	 * @return
	 */
	@PostMapping(value = "/upload")
	public String uploadFile(@RequestParam("path") String destPath, @RequestParam("file") MultipartFile file) {
		try {
			if(file.isEmpty()) {
				return "文件为空";
			}
			String fileName = file.getOriginalFilename();// 获取文件名
			logger.info("上传的文件名为：" + fileName);
//			String suffixName = fileName.substring(fileName.lastIndexOf("."));// 获取文件的后缀名
//			logger.info("文件的后缀名为：" + suffixName);
		    String filePath = destPath;//"D://setup//";// 设置文件存储路径
		    String path = filePath + fileName;// + suffixName;
		    File dest = new File(path);
		    if(!dest.getParentFile().exists()) {//检测是否存在目录
		    	dest.getParentFile().mkdirs();//新建文件夹
		    }
		    file.transferTo(dest);//文件写入
		    return "上传成功";
		} catch (IllegalStateException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return "上传失败";
	}

	/**
	 * 文件批量上传
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/uploadMore")
	public String handleFileUpload(HttpServletRequest request) {
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
	    List<MultipartFile> files = multipartHttpServletRequest.getFiles("file");
	    MultipartFile file = null;
	    BufferedOutputStream stream = null;
	    for (int i = 0; i < files.size(); ++i) {
	        file = files.get(i);
//	        String filePath = "D://setup//";
	        String filePath = request.getParameter("path");//可以直接从request中取参数
	        logger.info("路径参数为：" + filePath);
	        String fileName = file.getOriginalFilename();
	        if (!file.isEmpty()) {
	            try {
	                byte[] bytes = file.getBytes();
	                stream = new BufferedOutputStream(new FileOutputStream(
	                        new File(filePath + fileName)));//设置文件路径及名字
	                stream.write(bytes);// 写入
	                stream.close();
	                logger.info("上传文件：" + fileName);
	            } catch (Exception e) {
	                stream = null;
	                return "[" + file.getOriginalFilename() +"]文件上传失败  ==> " + e.getMessage();
	            }
	        } else {
	            return "[" + file.getOriginalFilename() +"]文件上传失败因为文件为空";
	        }
	    }
	    return "批量上传成功";
	}

	/**
	 * Download file
	 * @param response
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downloadFile(HttpServletResponse response) {
	    String fileName = "dubbo.xsd";     //要下载的文件为 D://setup/dubbo.xsd
	    String filePath = "D:\\setup\\";
	    File file = new File(filePath, fileName);
	    if(file.exists()) {
	    	response.setHeader("content-type", "application/octet-stream");
		    response.setContentType("application/octet-stream");
		    response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		    byte[] buff = new byte[1024];
			FileInputStream fis = null;
		    BufferedInputStream bis = null;
		    OutputStream os = null;
		    try {
		    	os = response.getOutputStream();
		    	fis = new FileInputStream(file);
		    	bis = new BufferedInputStream(fis);
		    	int i = bis.read(buff);
		        while (i != -1) {
		            os.write(buff, 0, buff.length);
		            os.flush();
		            i = bis.read(buff);
		        }
		    }catch (IOException e) {
		        e.printStackTrace();
		    }finally {
		    	if(null != bis) {
		    		try {
		    			bis.close();
		    		}catch (IOException e) {
		    			e.printStackTrace();
					}
		    	}
		    	if(null != fis) {
		    		try {
		    			fis.close();
		    		}catch(IOException e){
		    			e.printStackTrace();
		    		}
		    	}
		    }
	    }
	    System.out.println("success");
	}
}
