package matko.cv.fragments;

import android.widget.Button;

import androidx.fragment.app.Fragment;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


import matko.cv.R;
import matko.cv.helper.LocaleHelper;

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




    @Click(R.id.btEng)
    public void clickEng() {
        System.out.println("Button was clicked");
        LocaleHelper.setLocale(getContext(),"en");
    }





}
