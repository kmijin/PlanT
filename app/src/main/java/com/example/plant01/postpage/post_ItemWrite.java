package com.example.plant01.postpage;

import java.util.Date;

public class post_ItemWrite {

    private String content;
    private String contentImg;
    private String userID;
    private String board;
    private Date postDate;


    public post_ItemWrite(){ }

    public post_ItemWrite(String content, String contentImg, String userID, String board) {
        this.content = content;
        this.contentImg = contentImg;
        this.userID = userID;
        this.board = board;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
}


