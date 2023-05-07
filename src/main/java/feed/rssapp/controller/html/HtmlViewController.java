package feed.rssapp.controller.html;

import feed.rssapp.dto.NewsItemDto;
import feed.rssapp.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/view")
public class HtmlViewController {

    private final FeedService feedService;

    @Autowired
    public HtmlViewController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/news")
    public String getNews(Model model) throws Exception {
        List<NewsItemDto> newsItems = feedService.parse();
        model.addAttribute("newsItems", newsItems);
        return "News";
    }
}
