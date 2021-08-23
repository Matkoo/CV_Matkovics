package matko.cv.fragments;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import androidx.fragment.app.Fragment;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import matko.cv.R;


/**
 * @author Matkovics Gergely<br>
 * E-mail: <a href=
 * "mailto:gergelymatkovics82@gmail.com">gergelymatkovics82@gmail.com</a>
 */

@EFragment(R.layout.fragment_hobbys)
public class HobbysFragment extends Fragment {

    @ViewById(R.id.slide_trans_imageswitcher)
    protected ImageSwitcher switcher;

    private int animationCounter = 1;
    private Handler imageSwitcherHandler;
    private Runnable runAnimation;




    @Override
    public void onStart() {
        super.onStart();
        startAnimation();
    }

    protected void startAnimation() {

        Animation in  = AnimationUtils.loadAnimation(getContext(), R.anim.left_to_right_in);
        Animation out = AnimationUtils.loadAnimation(getContext(), R.anim.left_to_right_out);

        switcher.setInAnimation(in);
        switcher.setOutAnimation(out);
        switcher.setFactory(new ViewSwitcher.ViewFactory() {
                                     public View makeView() {
                                         ImageView myView = new ImageView(getContext());
                                         return myView;
                                     }
                                 });
        imageSwitcherHandler = new Handler(Looper.getMainLooper());
        runAnimation = new Runnable() {
            @Override
            public void run() {
                switch (animationCounter++) {
                    case 1:
                        switcher.setImageResource(R.drawable.topofthepeak);
                        break;
                    case 2:
                        switcher.setImageResource(R.drawable.withtent);
                        break;
                    case 3:
                        switcher.setImageResource(R.drawable.fixiewithbag);
                        break;
                    case 4:
                        switcher.setImageResource(R.drawable.snowlandscape);
                        break;
                    case 5:
                        switcher.setImageResource(R.drawable.winterpeak);
                        break;
                    case 6:
                        switcher.setImageResource(R.drawable.cloudywinter);
                        break;
                }
                animationCounter %= 7;
                if(animationCounter == 0 ) animationCounter = 1;

                imageSwitcherHandler.postDelayed(this, 3000);
            }
        };
        imageSwitcherHandler.post(runAnimation);



    }

    @Override
    public void onStop() {
        switcher.removeAllViews();
        imageSwitcherHandler.removeCallbacks(runAnimation);
        super.onStop();
    }
}
