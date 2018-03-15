package com.grabpage;

import static org.apache.commons.io.FilenameUtils.*;

import com.ibm.icu.text.Transliterator;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;


import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Illia Loshchinin on 06.03.2018.
 */
public class DownloadPage {

  static Document page = null;
  static String id = null;


  public static boolean loadWebPage(URL loadPage, File outputDir, String... disableResources) {

    String resourceDir = outputDir + "resources" + File.separator;
    String imagesDir = outputDir + "images" + File.separator;

    try {

      //TODO Detect extension of loadPage if have not extension create index.html
      //get name for directory and main page
      page = Jsoup.parse(loadPage, 5000);

      getNameFile(loadPage, page);
//      File pageDir = new File(outputDir,
//          getName(loadPage.toString()));
//
//      try {
//        FileUtils.forceMkdir(pageDir);
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//
//      System.out.println(loadPage.toString());
//      System.out.println(pageDir.getAbsolutePath());
//
//      File mainPage = new File(outputDir +
//          getName(loadPage.getPath()));
//
//      System.out.println(mainPage.toString());
//
//      //download css
//      Elements selects = page.select("link[href$='.css']");
//      getResource(selects, resourceDir);

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

    return true;
  }

  private static void getResource(Elements selects, String resourceDir)
      throws IOException {

  }

  public static void getNameFolder(Document page) {

  }

  public static String getNameFile(URL loadPage, Document page) {
    String name = null;
    try {
      name = getName(URLDecoder.decode(loadPage.toString().replaceAll("\\?.*", ""), "UTF8"));
      if (!getExtension(name).equals("")) {
        return removeExtension(name);
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    //page without extension

    //System.out.println(page.title() + " !");
    return normalizeForFileName(page.title());
  }

  static public String normalizeForFileName(String str) {
    String nfdNormalizedString = Normalizer.normalize(str, Form.NFD).replace('ł', 'l')
        .replace('Ł', 'L');
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    nfdNormalizedString = Transliterator.getInstance("Any-Latin; Latin-ASCII")
        .transform(nfdNormalizedString);
    nfdNormalizedString = pattern.matcher(nfdNormalizedString).replaceAll("").trim()
        .replaceAll("\\W+(?=\\s)", "");
    return nfdNormalizedString.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
  }

}
