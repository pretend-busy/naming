package com.qingsw.naming;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jdk.nashorn.internal.parser.JSONParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class GushiDataSource {

  @Autowired
  JdbcTemplate jdbcTemplate;

  public void insert(Shijing shijing) {
    String value = "'" + shijing.getContent() + "','" + shijing.getTitle() + "','" + shijing.getBook() + "','"
        + shijing.getDynasty() +"'";
    String sql = "insert into shijing (content, title, book, dynasty) values (" + value + ")";
    jdbcTemplate.execute(sql);
  }

  public List<Shijing> getAll() throws IOException {
    String urlBaidu = "https://www.toolfk.com/tools/js/gushi/json/chuci.json";
    OkHttpClient okHttpClient = new OkHttpClient(); // 创建OkHttpClient对象
    Request request = new Request.Builder().url(urlBaidu).build(); // 创建一个请求
    Response response = okHttpClient.newCall(request).execute(); // 返回实体
    String responseStr = "";
    if (response.isSuccessful()) { // 判断是否成功
      /**获取返回的数据，可通过response.body().string()获取，默认返回的是utf-8格式；
       * string()适用于获取小数据信息，如果返回的数据超过1M，建议使用stream()获取返回的数据，
       * 因为string() 方法会将整个文档加载到内存中。*/
      responseStr = response.body().string();
//      System.out.println(responseStr); // 打印数据
    }else {
      System.out.println("失败"); // 链接失败
    }
    Gson gson = new GsonBuilder().create();
    Shijing[] shijings = gson.fromJson(responseStr,Shijing[].class);
    List<Shijing> shijingList = new ArrayList<>();
    shijingList.addAll(Arrays.asList(shijings));
    return shijingList;
  }
}
