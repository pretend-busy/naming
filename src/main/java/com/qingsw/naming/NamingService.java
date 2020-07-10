package com.qingsw.naming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class NamingService {

  @Autowired
  GushiDataSource gushiDataSource;

  private int size;

  public List<Name> naming(String givenName) throws IOException {
    List<Shijing> shijings = gushiDataSource.getAll();
    List<Name> names = new ArrayList<>();
    for (Shijing item : shijings) {
      names.add(fromShiJing(item, givenName));
    }
    return names;
  }

  public Name fromShiJing(Shijing shijing, String givenName) {
    String content = shijing.getContent();
    Random random = new Random();
    if (random.nextInt()%2==0) {
      size = 2;
    } else {
      size = 1;
    }
    int contentLength = content.length();
    String result = "";
    for ( ; ; ) {
      int index = random.nextInt(contentLength);
      if (!StringUtils.isEmpty(content.charAt(index))
          && content.charAt(index) != ','
          && content.charAt(index) != '，'
          && content.charAt(index) != '。'
          && content.charAt(index) != '；'
      ) {
        result += content.charAt(index);
      }
      if (result.length() == size) {
        break;
      }
    }
    Name name = new Name();
    name.setFullName(givenName + result);
    name.setSource(shijing.getContent());
    name.setSourceComment(shijing.getBook());
    return name;
  }
}
