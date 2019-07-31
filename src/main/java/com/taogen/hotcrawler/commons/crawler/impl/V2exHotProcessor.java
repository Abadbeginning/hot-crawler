package com.taogen.hotcrawler.commons.crawler.impl;

import com.taogen.hotcrawler.commons.crawler.HotProcessor;
import com.taogen.hotcrawler.commons.entity.Info;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("V2exHotProcessor")
public class V2exHotProcessor implements HotProcessor
{
    private static final Logger log = LoggerFactory.getLogger(V2exHotProcessor.class);

    @Autowired
    private BaseHotProcessor baseHotProcessor;

    public static final String DOMAIN = "https://v2ex.com";
    public static final String HOT_PAGE_URL = "https://v2ex.com/?tab=hot";
    public static final String ITEM_KEY = "item_title";

    @Override
    public List<Info> crawlHotList()
    {
        List<Info> list = new ArrayList<>();

        // document
        Document doc = baseHotProcessor.getDoc(HOT_PAGE_URL, null, log);
        if (doc == null)
        {
            return list;
        }

        // elements
        Elements elements = doc.getElementsByClass(ITEM_KEY);

        int i = 0;
        for (Element element : elements)
        {
            Elements elements1 = element.getElementsByTag("a");
            String infoTitle = elements1.html();
            StringBuilder infoUrl = new StringBuilder(DOMAIN);
            infoUrl.append(DOMAIN);
            infoUrl.append(elements1.attr("href"));
            infoUrl.substring(0, infoUrl.indexOf("#"));
            String id = String.valueOf(++i);
            list.add(new Info(id, infoTitle, infoUrl.toString()));
        }

        log.debug("return list size is {}", list.size());
        return list;
    }
}
