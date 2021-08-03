package org.halk.webcrawler.lsp;

import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * @Author halk
 * @Date 2021/1/14 9:32
 */
public class ConsolePipeline implements Pipeline {

    static final String fileAddress = "E:\\pythonimg\\yishu\\";

    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println("get page: " + resultItems.getRequest().getUrl());
        Map<String, String> imgMap = (Map) resultItems.getAll().get("imgMap");
        if (null == imgMap || imgMap.size() == 0) {
            return;
        }

        String imgUrl = imgMap.get("imgUrl");
        String jpgTotalName = imgMap.get("jpgTotalName");
        String imgName = imgMap.get("imgName");

        if (StringUtils.isEmpty(jpgTotalName) || StringUtils.isEmpty(imgUrl)) {
            return;
        }
        File folder = new File(fileAddress + jpgTotalName);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            //构造url、打开链接
            URLConnection urlConnection = new URL(imgUrl).openConnection();
            urlConnection.setConnectTimeout(5 * 1000);

            // 输入流
            inputStream = urlConnection.getInputStream();

            // 输出的文件流
            String fileName = fileAddress + jpgTotalName + "\\" + imgName;
            File file = new File(fileName);
            outputStream = new FileOutputStream(file, true);
            // 1K的数据缓冲
            byte[] bytes = new byte[512 * 1024];
            int len;
            //开始读取
            while ((len = inputStream.read(bytes)) != -1) {
                //保存
                outputStream.write(bytes, 0, len);
            }
            System.out.println("下载图片： " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        /*ArrayList jpgList = (ArrayList) resultItems.getAll().get("jpg");
        ArrayList jpgNameList = (ArrayList) resultItems.getAll().get("jpgName");
        for (int i = 0; i < jpgList.size(); i++) {
            System.out.println(jpgNameList.get(i));
            System.out.println(jpgList.get(i));
        }*/
    }
}
