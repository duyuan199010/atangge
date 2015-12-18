package com.strod.yssl.bean.main;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/30.
 */
@DatabaseTable(tableName = "collect")
public class Collect implements Serializable{
    /**id*/
    @DatabaseField(generatedId = true)
    private int id;
    /** articleId */
    @DatabaseField(columnName = "articleId")
    private int articleId;
    /** img url */
    @DatabaseField(columnName = "imgUrl")
    private String imgUrl;
    /** title */
    @DatabaseField(columnName = "title")
    private String title;
    /** content */
    @DatabaseField(columnName = "content")
    private String content;
    /** publish time */
    @DatabaseField(columnName = "time")
    private long time;
    /** content url */
    @DatabaseField(columnName = "contentUrl")
    private String contentUrl;


    public Collect() {
        super();
    }

    public Collect(int articleId, String imgUrl, String title, String content, long time, String contentUrl) {
        super();
        this.articleId = articleId;
        this.imgUrl = imgUrl;
        this.title = title;
        this.content = content;
        this.time = time;
        this.contentUrl = contentUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }


    @Override
    public String toString() {
        return "Collect{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", contentUrl='" + contentUrl + '\'' +
                '}';
    }
}
