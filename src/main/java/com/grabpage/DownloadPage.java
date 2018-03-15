package com.grabpage;

import java.io.File;
import java.io.IOException;
import java.net.URL;


import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Illia Loshchinin on 06.03.2018.
 */
public class DownloadPage {

  static Document page = null;
  static String id = null;


  public static boolean getWebPage(URL loadPage, File outputDir,String... disableResources) {

    String resourceDir = outputDir + "resources" + File.separator;
    String imagesDir = outputDir + "images" + File.separator;

    try {

      //TODO Detect extension of loadPage if have not extension create index.html

      File mainPage = new File(outputDir + FilenameUtils
          .getName(loadPage.getPath()));

      System.out.println(mainPage.toString());

      page = Jsoup.parse(loadPage,5000);

      //download css
      Elements selects = page.select("link[href$='.css']");
      getResource(selects, resourceDir);

//      //js
//      selects = page.select("src[href$='.js']");
//      getResource(selects, "src", sourceDir, false);
//
//      //images
//      selects = page.select("img[src]");
//      getResource(selects, "data-img-large", imagesDir, true);
//
//      FileUtils.writeStringToFile(mainPage, page.outerHtml(), "utf8");
    } catch (IOException e) {
      e.printStackTrace();
    }

    return false;
  }

   private static void getResource(Elements selects, String resourceDir)
      throws IOException {


    }

}
