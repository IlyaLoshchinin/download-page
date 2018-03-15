package com.grabpage;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IParameterValidator2;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterDescription;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.CommaParameterSplitter;
import com.beust.jcommander.converters.FileConverter;
import com.beust.jcommander.converters.IParameterSplitter;
import com.beust.jcommander.converters.PathConverter;
import com.beust.jcommander.converters.URLConverter;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;

/**
 * Created by Illia Loshchinin on 13.03.2018.
 */
public class Args {

  @Parameter(names = "-help", help = true)
  private boolean help;

  @Parameter(names = {
      "-url"}, required = true, description = "The URLs of page to download", listConverter = URLListConverter.class)
  List<URL> urls;

  @Parameter(names = {"-out",
      "--outputDirectory"}, help = true, description = "The output directory of page(-s). Default -> current app folder", converter = FileConverter.class, validateValueWith = DirectoryValidator.class)
  File directory = new File("").getAbsoluteFile();


  @Parameter(names = {"-d",
      "--disableResources"},help = true,splitter = SemiSplitter.class,description = "The resources(css,js,img) which is not going to download. Exp.: '-d css,js,img'",validateValueWith = ResourcesValidator.class)
  List<String> dResources;


  public static class SemiSplitter implements IParameterSplitter {
    public List<String> split(String value) {
      return Arrays.asList(value.split(","));
    }
  }

  public static class URLListConverter implements IStringConverter<List<URL>> {

    @Override
    public List<URL> convert(String value) {
      String[] arg = value.split(",");
      List<URL> tmp = new ArrayList<>();
      for (String url : arg) {
        if (!url.matches("^https?://(-\\.)?([^\\s/?\\.#-]+\\.?)+(/[^\\s]*)?$")) {
          throw new ParameterException(
              "This URL:(" + url + ") is incorrect! Please verify the URL and try again!");
        }
        try {
          tmp.add(new URL(url));
        } catch (MalformedURLException e) {
          e.printStackTrace();
        }
      }
      return tmp;
    }
  }

  public static class ResourcesValidator implements IValueValidator<List<String>> {

    @Override
    public void validate(String name, List<String> value) throws ParameterException {
      if (value.stream().anyMatch(s -> !s.matches("css|js|img"))) {
        throw new ParameterException(
            "What resources would you like to skip? Please verify writing! Allows: css,js,img");
      }
    }
  }

  public static class DirectoryValidator implements IValueValidator {

    @Override
    public void validate(String name, Object value) throws ParameterException {
      File directory = (File) value;
      try {
        FileUtils.forceMkdir(directory);
      } catch (IOException e) {
        throw new ParameterException(
            "The directory cannot be created or the file already exists but is not a directory! Please verify the path of directory and try again");
      }
    }
  }
}
