package com.qingsw.naming;

public class Shijing {

  private String content;
  private String book;
  private String title;
  private String dynasty;

  public Shijing(String content, String book, String title, String dynasty) {
    this.content = content;
    this.book = book;
    this.title = title;
    this.dynasty = dynasty;
  }

  public Shijing() {

  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getBook() {
    return book;
  }

  public void setBook(String book) {
    this.book = book;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDynasty() {
    return dynasty;
  }

  public void setDynasty(String dynasty) {
    this.dynasty = dynasty;
  }
}
