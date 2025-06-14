package com.openjfxroot;

//import com.openjfxroot.base.Client;
import com.openjfxroot.base.Article;
import com.openjfxroot.base.DataArticleModelDB;
//import com.openjfxroot.base.DataArticleModelText;

public class ArticleModel {

   private Article articleObj;

   private int articleCount;

// getters/setters
   public Article getArticleObj() {
      return this.articleObj;
   }

   public void setArticleObj(Article articleObj) {
      this.articleObj = articleObj;
   }

   public int getArticleCount() {
      return this.articleCount;
   }

   public void setArticleCount(int articleCount) {
      this.articleCount = articleCount;
   }

   public void addArticleList() {
      this.articleCount = this.articleCount+1;
      //...
   }
}

