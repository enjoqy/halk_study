package org.halk.webcrawler;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;

public class MonitorExample {

    public static void main(String[] args) throws Exception {

        Spider githubSpider = Spider.create(new GithubRepoPageProcessor())
                .setDownloader(new HttpClientDownloader())
                .addUrl("https://github.com/code4craft");

        SpiderMonitor.instance().register(githubSpider);
        githubSpider.start();
    }
}
