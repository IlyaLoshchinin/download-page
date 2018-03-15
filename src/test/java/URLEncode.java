import com.beust.jcommander.JCommander;
import com.beust.jcommander.internal.Lists;
import com.grabpage.Args;
import com.grabpage.DownloadPage;
import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Illia Loshchinin on 15.03.2018.
 */
public class URLEncode extends Assert {

  URL[] urls;

  @Before
  public void before() {
    Args args = Args.getInstance();
    JCommander.newBuilder()
        .addObject(args)
        .build()
        .parse("-url",
            "https://www.olx.pl/praca/warszawa/q-wyk%C5%82adanie-towaru/?search%5Bfilter_enum_type%5D%5B0%5D=parttime&search%5Bfilter_enum_requirements%5D%5B0%5D=no,"
                + "https://www.olx.pl/,https://www.olx.pl/sport-hobby/kolekcje/warszawa/,"
                + "https://stackoverflow.com/questions/15075890/replacing-illegal-character-in-filename,"
                + "https://www.google.pl/search?num=20&newwindow=1&ei=OriqWqSSAcuNkgWBxq6ADA&q=%D0%BF%D1%80%D0%B8%D0%B2%D0%B5%D1%82&oq=%D0%BF%D1%80%D0%B8%D0%B2%D0%B5%D1%82&gs_l=psy-ab.3..0j0i20i263k1j0j0i20i263k1j0l6.3131.3711.0.4005.6.6.0.0.0.0.157.530.4j1.5.0....0...1c.1.64.psy-ab..1.5.528...35i39k1.0.gpJu7auhqSo,"
                + "https://planzajec.pjwstk.edu.pl/Logowanie.aspx",
            "-out", "D:\\Desktop\\tmp.1", "-d", "js");
    urls = args.urls.toArray(new URL[args.urls.size()]);
  }

  @Test
  public void getSafeFileNameFromURL() throws IOException {
    List<String> safeName = Arrays
        .asList("Wykladanie_Towaru_Praca_w_Warszawa_OLX.pl", "Ogloszenia_Sprzedam_kupie_na_OLX.pl",
            "Warszawa_Obrazy_antyki_znaczki_monety_figurki_maszyny_do_szycia_na_sprzedaz_OLX.pl_Warszawa",
            "java_Replacing_illegal_character_in_fileName_Stack_Overflow",
            "privet_Szukaj_w_Google",
            "Logowanie");
    List<String> nameURL = new ArrayList<>();
    for (URL url : urls) {
      nameURL.add(DownloadPage.getNameFile(url, Jsoup.connect(url.toString()).get()));
    }
    for (int i = 0; i < nameURL.size(); i++) {
      assertEquals(nameURL.get(i), safeName.get(i));
    }
  }


}
