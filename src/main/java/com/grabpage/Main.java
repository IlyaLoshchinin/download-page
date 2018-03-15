package com.grabpage;

import com.beust.jcommander.JCommander;
import com.sun.corba.se.impl.naming.cosnaming.NamingUtils;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;

/**
 * Created by Illia Loshchinin on 06.03.2018.
 */
public class Main {


  public static void main(String[] argv) {

    Args args = new Args();
    JCommander.newBuilder()
        .addObject(args)
        .build()
        .parse(argv);

    Optional.of(args.urls).ifPresent(System.out::println);
    Optional.of(args.directory).ifPresent(System.out::println);
    Optional.of(args.dResources).ifPresent(System.out::println);

    // System.out.println( Jsoup.parse("<link href=\"/css/metrum.css\">").outerHtml());

    final File outputDir = args.directory;
    final String[] disableResources = args.dResources.toArray(new String[args.dResources.size()]);

    for (URL url : args.urls) {

      if (DownloadPage.loadWebPage(url, outputDir, disableResources)) {
        System.out.println("Page " + url.getPath() + " grabbed successfully!!");

      }
      System.out.println("Pages grabbed successfully! Exit!");

    }
  }
}
