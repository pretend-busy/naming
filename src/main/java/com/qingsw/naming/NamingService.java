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

  private int extractSpan = 10;

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
    List<String> keys = new ArrayList<>();
    for ( ; ; ) {
      int index = random.nextInt(contentLength);
      if (!StringUtils.isEmpty(content.charAt(index))
          && content.charAt(index) != ','
          && content.charAt(index) != '，'
          && content.charAt(index) != '。'
          && content.charAt(index) != '；'
          && content.charAt(index) != '？'
          && content.charAt(index) != '！'
      ) {
        result += content.charAt(index);
        keys.add(content.charAt(index) + "");
      }
      if (result.length() == size) {
        break;
      }
    }
    Name name = new Name();
    name.setFullName(givenName + result);
    name.setSource(textAbstract(shijing.getContent(), keys));
    name.setSourceComment(shijing.getBook());
    return name;
  }

  private String textAbstract(String source, List<String> keys) {
    if (StringUtils.isEmpty(source) || keys==null || keys.size() == 0) {
      return "";
    }
    int startIndex = Integer.MAX_VALUE;
    int endIndex = Integer.MIN_VALUE;
    for (String key : keys) {
      int indexOfKey = source.indexOf(key);
      if (indexOfKey == -1) {
        continue;
      } else {
        if (indexOfKey < startIndex) {
          startIndex = indexOfKey;
        }
        if (indexOfKey > endIndex) {
          endIndex = indexOfKey;
        }
      }
    }
    startIndex = (startIndex - extractSpan) < 0 ? 0 : startIndex - extractSpan;
    endIndex = (endIndex + extractSpan) >= source.length() ? source.length() - 1 : endIndex + extractSpan;
    return source.substring(startIndex, endIndex);
  }
}
