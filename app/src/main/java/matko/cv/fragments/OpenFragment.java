package matko.cv.fragments;

import android.content.res.Configuration;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Locale;

import bbraun.matko.cv.R;

/**
 * @author Matkovics Gergely<br>
 * E-mail: <a href=
 * "mailto:matkovics.gergely@itqs.hu">matkovics.gergely@itqs.hu</a>
 */

@EFragment(R.layout.fragment_open)
public class OpenFragment extends Fragment {

    @ViewById(R.id.btHun)
    protected Button btHun;

    @ViewById(R.id.btEng)
    protected Button btEng;


//    @Click(R.id.btEng)
//    public void clickEng(){
//        Locale locale;
//        Sessions session = new Sessions(context);
//        //Log.e("Lan",session.getLanguage());
//        locale = new Locale(langCode);
//        Configuration config = new Configuration(context.getResources().getConfiguration());
//        Locale.setDefault(locale);
//        config.setLocale(locale);
//
//        context.getBaseContext().getResources().updateConfiguration(config,
//                context.getBaseContext().getResources().getDisplayMetrics());
//    }

}
