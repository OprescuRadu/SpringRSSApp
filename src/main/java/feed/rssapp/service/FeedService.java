package feed.rssapp.service;

import feed.rssapp.dto.NewsItemDto;
import java.util.List;

public interface FeedService {

    List<NewsItemDto> parse() throws Exception;
}
