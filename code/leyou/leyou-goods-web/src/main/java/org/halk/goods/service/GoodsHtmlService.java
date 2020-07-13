package org.halk.goods.service;

import com.leyou.common.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * @Author halk
 * @Date 2020/5/27 0027 9:59
 */
@Service
public class GoodsHtmlService {

    @Autowired
    private TemplateEngine engine;

    @Autowired
    private GoodsService goodsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsHtmlService.class);

    public void createHtml(Long spuId){

        //初始化运行上下文
        Context context = new Context();
        //设置数据模型
        context.setVariables(this.goodsService.loadData(spuId));

        PrintWriter printWriter = null;
        try {
            //把静态文件生成到服务器本地
            File file = new File("D:\\program\\nginx-1.16.1\\html\\item\\" + spuId + ".html");
            printWriter = new PrintWriter(file);

            this.engine.process("item", context,  printWriter);
        } catch (FileNotFoundException e) {
            LOGGER.error("页面静态化出错：{}" + e, spuId);
            e.printStackTrace();
        } finally {
            if (printWriter != null){
                printWriter.close();
            }
        }
    }

    /**
     * 新建线程处理页面
     * @param spuId
     */
    public void asyncExecute(Long spuId){
        ThreadUtils.execute(() -> createHtml(spuId));
    }

    /**
     * @Author halk
     * @Description 删除生成的详情页
     * @Date 2020/5/28 0028 17:23
     * @Param [id]
     * @return void
     **/
    public void deleteHtml(Long id) {
        File file = new File("D:\\program\\nginx-1.16.1\\html\\item\\" + id + ".html");
        file.deleteOnExit();
    }
}
