package cafe.lunarconcerto.runner;

import cafe.lunarconcerto.domain.entity.Article;
import cafe.lunarconcerto.mapper.ArticleMapper;
import cafe.lunarconcerto.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cafe.lunarconcerto.constants.SystemConstants.VIEW_COUNT_REDIS_KEY;

/**
 * @author LunarConcerto
 * @time 2023/12/29
 */
@Component
public class ViewCountRunner implements CommandLineRunner {


    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        // 查询博客ID
        List<Article> articles = articleMapper.selectList(null);

        Map<String , Integer> viewCountMap = articles.stream().collect(
                Collectors.toMap(
                        article -> String.valueOf(article.getId()),
                        article -> Math.toIntExact(article.getViewCount())
                )
        );

        // 存储到redis
        redisCache.setCacheMap(VIEW_COUNT_REDIS_KEY, viewCountMap);
    }

}
