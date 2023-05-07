package feed.rssApp.service;

import feed.rssApp.dto.NewsItemDto;
import java.util.List;

public interface FeedService {

    List<NewsItemDto> parse() throws Exception;
}
