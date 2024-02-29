package cafe.lunarconcerto.constants;

public class SystemConstants {

    /**
     * 文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;

    /**
     * 文章是发布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    public static final String STATUS_NORMAL = "0" ;

    /**
     * 友链审核通过
     */
    public static final String LINK_STATUS_NORMAL = "0" ;

    /**
     * 评论类型: 文章评论
     */
    public static final String ARTICLE_COMMENT = "0";

    /**
     * 评论类型: 友链评论
     */
    public static final String LINK_COMMENT = "1";

    public static final String VIEW_COUNT_REDIS_KEY = "article:viewCount";

    public static final Integer STATUS_DELETE = 1;

    public static final String  NORMAL = "0";
    public static final String ADMIN = "1";
}
