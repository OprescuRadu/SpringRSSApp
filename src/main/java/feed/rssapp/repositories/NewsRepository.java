package feed.rssapp.repositories;

import feed.rssapp.model.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NewsRepository extends JpaRepository<NewsItem, Long> {

    List<NewsItem> findNewsItemByLink(String link);
}
