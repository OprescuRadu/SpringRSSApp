package feed.rssApp.repositories;

import feed.rssApp.model.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NewsRepository extends JpaRepository<NewsItem, Long> {

    List<NewsItem> findNewsItemByLink(String link);
}
