package feed.rssApp.service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import feed.rssApp.dto.NewsItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedClientService feed;

    @Override
    public List<NewsItemDto> parse() throws Exception {
        SyndFeed syndFeed = feed.getFeed();
        List<NewsItemDto> newsList = new ArrayList<>();
        for (SyndEntry entry : syndFeed.getEntries()) {
            NewsItemDto news = new NewsItemDto(entry.getTitle(), entry.getLink(), entry.getDescription().getValue(),
                    entry.getPublishedDate());
            newsList.add(news);
        }
        return newsList;
    }
}
