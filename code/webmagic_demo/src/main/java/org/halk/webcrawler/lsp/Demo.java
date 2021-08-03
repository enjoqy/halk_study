package org.halk.webcrawler.lsp;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static us.codecraft.webmagic.selector.Selectors.xpath;

/**
 * @Author halk
 * @Date 2021/1/13 20:26
 */
public class Demo implements PageProcessor {

    static final String baseUrl = "https://www.tupianzj.com/meinv/yishu/list_178_1.html";

    static int count = 0;

    public static void main(String[] args) {
        Spider.create(new Demo())
                .addUrl(baseUrl)
//                .addPipeline(new JsonFilePipeline("D:\\webmagic\\"))
                .addPipeline(new ConsolePipeline())
                .thread(3)
                .run();
    }

    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(100)
            .setCharset("GBK");

    @Override
    public void process(Page page) {

        if (page.getUrl().toString().matches(".*list.*")) {
            //对列表页的下一页的链接进行处理，加入到队列
            processGetNextPageUrl(page);
            //处理第一页的跳转连接
            processLinks(page);
        } else {
            //处理详情页，获取图片链接
            processImgUrl(page);
        }
    }

    /**
     * 处理第一页的跳转连接
     *
     * @param page
     */
    private void processLinks(Page page) {
        ///meinv/20200629/212861.html
        //  //*[@id="container"]/div/div/div[3]/div/ul/li[1]/a
        List<String> preprocessUrlList = page.getHtml().xpath("//div[@class='list_con_box']/ul[@class='list_con_box_ul']/li/a[@href]").all();

        for (String preprocessUrl : preprocessUrlList) {
            //https://www.tupianzj.com/meinv/20200629/212861.html
            String jpgTotalName = xpath("//a/img/@alt").select(preprocessUrl);
            String jpgTotalUrl = xpath("//a/@href").select(preprocessUrl);
            jpgTotalUrl = "https://www.tupianzj.com/" + jpgTotalUrl;
            Request request = new Request(jpgTotalUrl).putExtra("jpgTotalName", jpgTotalName);
            page.addTargetRequest(request);
        }
    }

    /**
     * 处理详情页，获取图片链接
     *
     * @param page
     */
    private void processImgUrl(Page page) {
        Html html = page.getHtml();

        //存入图片的名称、链接 //*[@id="bigpicimg"]
        String imgUrl = html.xpath("//img[@id='bigpicimg']/@src").get();
        String imgName = html.xpath("//li[@class='thisclass']/a/text()").get();
        if (StringUtils.isEmpty(imgName) || StringUtils.isEmpty(imgUrl)) {
            return;
        }
        Map<String, String> imgMap = new HashMap<>();
        imgMap.put("imgUrl", imgUrl);
        imgMap.put("imgName", imgName + imgUrl.substring(imgUrl.length() - 4));
        imgMap.put("jpgTotalName", page.getRequest().getExtra("jpgTotalName"));
        page.putField("imgMap", imgMap);

        //对下一页的url进行拼接
        String url = page.getUrl().toString();
        String[] split = url.split("\\/");
        String urlRoot = url.substring(0, url.length() - url.split("\\/")[split.length - 1].length());
        List<String> nextPageUrlPreprocess = html.xpath("//div[@class='pages']/ul/li/a/@href").all();
        if (CollectionUtils.isNotEmpty(nextPageUrlPreprocess)) {
            String nextPageUrl = nextPageUrlPreprocess.get(nextPageUrlPreprocess.size() - 1);
            if (!nextPageUrl.matches("javascript\\.+")) {
                Request request = new Request(urlRoot + nextPageUrl).putExtra("jpgTotalName", page.getRequest().getExtra("jpgTotalName"));
                page.addTargetRequest(request);
            }
        }

    }

    /**
     * 对详情页下一页的链接进行处理，加入到队列
     *
     * @param page
     */
    private void processGetNextPageUrl(Page page) {

        count++;
        if (count > 50) {
            return;
        }

        //  //*[@id="container"]/div/div/div[3]/div/div[2]/ul/li[14]/a
//        String nextUrlHtml = page.getHtml().xpath("//*[@id='container']/div/div/div[3]/div/div[2]/ul/li[14]/a[@href]").get();
        List<String> nextUrlHtmlList = page.getHtml().xpath("//div[@class='pages']/ul/li/a/@href").all();
        if (CollectionUtils.isNotEmpty(nextUrlHtmlList)) {
            String nextUrlHtml = nextUrlHtmlList.get(nextUrlHtmlList.size() - 2);
            Request request = new Request(baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1) + nextUrlHtml);
            page.addTargetRequest(request);
        }

    }

    @Override
    public Site getSite() {
        return site;
    }


}
