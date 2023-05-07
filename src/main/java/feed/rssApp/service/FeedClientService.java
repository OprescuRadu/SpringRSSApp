package feed.rssApp.service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Service
public class FeedClientService {

    @Value("${feed.url}")
    private String url;

    public SyndFeed getFeed() throws Exception {
        URL feedSource = new URL(url);
        SyndFeedInput input = new SyndFeedInput();
        URLConnection conn = feedSource.openConnection();
        InputStream inputStream = conn.getInputStream();
        return input.build(new XmlReader(inputStream));
    }
}
