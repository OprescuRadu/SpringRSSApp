package feed.rssapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import feed.rssapp.dto.NewsItemDto;
import feed.rssapp.dto.Validate;
import feed.rssapp.model.NewsItem;
import feed.rssapp.repositories.NewsRepository;
import feed.rssapp.service.FeedService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.util.Comparator.comparing;

@RestController
@RequestMapping("/news")
public class NewsController {

    Logger logger = LoggerFactory.getLogger(NewsController.class);
    @Autowired
    private ObjectMapper objectMapper;
    private final FeedService news;
    private final NewsRepository newsRepository;

    @Autowired
    public NewsController(FeedService news, NewsRepository newsRepository) {
        this.news = news;
        this.newsRepository = newsRepository;
    }

    @GetMapping("/sort")
    public ResponseEntity<?> getNewsSortedByDate(@RequestParam("parameter") String parameter,
                                                 @RequestParam(value = "order", required = false) String order) throws Exception {
        List<NewsItemDto> newsFeed = new ArrayList<>();
        List<NewsItemDto> parsedNews = news.parse();
        for (NewsItemDto newsItem : parsedNews) {
            NewsItemDto newsItemDto = new NewsItemDto();
            newsItemDto.setTitle(newsItem.getTitle());
            newsItemDto.setDescription(newsItem.getDescription());
            newsItemDto.setPublicationDate(newsItem.getPublicationDate());
            newsItemDto.setLink(newsItem.getLink());
            newsFeed.add(newsItemDto);
        }
        switch (parameter.toLowerCase()) {
            case "date" -> newsFeed.sort(comparing(NewsItemDto::getPublicationDate));
            case "title" -> newsFeed.sort(comparing(NewsItemDto::getTitle));
            default -> {
                logger.warn("Invalid sort parameter: " + parameter + ".");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("News can be sorted by date or title!");
            }
        }
        if (order != null) {
            if (order.equalsIgnoreCase("desc"))
                Collections.reverse(newsFeed);
        }
        logger.info("Retrieving list of " + newsFeed.size() + " news.");
        return new ResponseEntity<>(newsFeed, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchNews(@RequestParam("keyword") String keyword) throws Exception {
        Validate keywordValidator = validateKeyword(keyword);
        if (!keywordValidator.isValid()) {
            logger.warn("Invalid keyword: " + keyword );
            return new ResponseEntity<>(keywordValidator.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        List<NewsItemDto> newsFeed = news.parse();
        List<NewsItemDto> matchingNews = new ArrayList<>();
        for (NewsItemDto item : newsFeed) {
            if (item.getTitle().contains(keyword) || item.getDescription().contains(keyword)) {
                matchingNews.add(item);
            }
        }
        return new ResponseEntity<>(matchingNews, HttpStatus.OK);
    }

    private Validate validateKeyword(String keyword) {
        if (keyword.isEmpty())
            return new Validate(false, "Keyword must not be empty!");
        if (keyword.length() > 25)
            return new Validate(false, "Keyword must be less than 25 characters!");
        return new Validate(true, "OK");
    }

    @GetMapping("/save-to-db")
    public ResponseEntity<?> saveNewsToDb() throws Exception {
        List<NewsItem> newsItems = news.parse().stream()
                .map(newsItemDto -> objectMapper.convertValue(newsItemDto, NewsItem.class)).toList();
        List<NewsItem> newNewsItems = new ArrayList<>();
        for (NewsItem newsItem : newsItems) {
            if (newsRepository.findNewsItemByLink(newsItem.getLink()).isEmpty()) {
                newNewsItems.add(newsItem);
            }
        }
        if (newNewsItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("There are no new news to save to database.");
        } else {
            newsRepository.saveAll(newNewsItems);
            logger.info("New news added to database.");
            return ResponseEntity.status(HttpStatus.OK).body("News saved to database!");
        }
    }
}
