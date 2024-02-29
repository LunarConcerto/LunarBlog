package cafe.lunarconcerto.job;

import cafe.lunarconcerto.constants.SystemConstants;
import cafe.lunarconcerto.domain.entity.Article;
import cafe.lunarconcerto.service.ArticleService;
import cafe.lunarconcerto.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LunarConcerto
 * @time 2023/12/29
 */
@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateViewCount(){
        // 获取redis 中的浏览量
        Map<String, Integer> cacheMap = redisCache.getCacheMap(SystemConstants.VIEW_COUNT_REDIS_KEY);

        List<Article> articleList = cacheMap.entrySet().stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .toList();

        // 更新到数据库中
        articleService.updateBatchById(articleList);
    }

}
