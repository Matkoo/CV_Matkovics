package matko.cv.fragments;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Locale;

import matko.cv.R;

/**
 * @author Matkovics Gergely<br>
 * E-mail: <a href=
 * "mailto:gergelymatkovics82@gmail.com">gergelymatkovics82@gmail.com</a>
 */

@EFragment(R.layout.fragment_personal_info)
public class PersonalInfoFragment extends Fragment {



    @ViewById(R.id.btHun)
    protected Button btHun;

    @ViewById(R.id.btEng)
    protected Button btEng;

    private String countryCode;




    @Click(R.id.btEng)
    public void clickEng() {
        countryCode = "en";
        changeLanguage(countryCode);
    }



    @Click(R.id.btHun)
    public void clickHun() {
        countryCode = "hu";
        changeLanguage(countryCode);
    }

    private void changeLanguage(String countryCode) {

        Locale locale = new Locale(countryCode);
        Locale.setDefault(locale);

        Resources resources = getActivity().getBaseContext().getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
        getActivity().recreate();

    }


}
