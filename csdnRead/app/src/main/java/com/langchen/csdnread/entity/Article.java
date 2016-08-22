package com.langchen.csdnread.entity;

/**
 * Created by admin on 2016/8/18.
 */
public class Article {
    /*
    "PostTime":"\/Date(1471510190000)\/","UpdateTime":"\/Date(1471510195581)\/","Digg":0,"Bury":0,"ChannelId":6,"Type":2,"Status":0,"ViewCount":0,"CommentCount":0,"CommentAuth":2,"IsTop":false,"Level":0,"OutlinkCount":0,"Note":null,"IP":null,"Categories":null,"Tags":[],"ColumnAlias":null,"ColumnTitle":null,"MarkDownContent":null,"MarkDownDirectory":null,"ArticleEditType":0,"ArticleMore":null}
     */
    private long ArticleId;
    private long BlogId;
    private String UserName;
    private String Title;
    private String Description;

    public long getArticleId() {
        return ArticleId;
    }

    public void setArticleId(long articleId) {
        ArticleId = articleId;
    }

    public long getBlogId() {
        return BlogId;
    }

    public void setBlogId(long blogId) {
        BlogId = blogId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "Article{" +
                "ArticleId=" + ArticleId +
                ", BlogId=" + BlogId +
                ", UserName='" + UserName + '\'' +
                ", Title='" + Title + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
