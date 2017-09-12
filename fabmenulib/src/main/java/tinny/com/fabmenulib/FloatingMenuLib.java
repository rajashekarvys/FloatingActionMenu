package tinny.com.fabmenulib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajashekar on 21/08/17.
 */

public class FloatingMenuLib extends RelativeLayout {

    private ArrayList<View> listViews;
    private boolean isVisible;

    private RelativeLayout mFabMenuContainer;
    private ViewGroup mFabCard;
    private View mBackGroundScreen;
    private ImageView fabIcon;
    private OnFabItemClick onFabItemClick;
    private OnFabOpenCloseListener onFabOpenCloseListener;

    private boolean isBackGroundAnimation;
    private boolean isFabItemAnimation;
    private boolean removeBackGround;


    public FloatingMenuLib(Context context) {
        super(context);
        initViews();
    }

    public FloatingMenuLib(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public FloatingMenuLib(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.floating_action_button, this);
        mFabMenuContainer = (RelativeLayout) findViewById(R.id.fab_container);
        mFabCard = (ViewGroup) findViewById(R.id.card);
        mBackGroundScreen = findViewById(R.id.cover);
        fabIcon = (ImageView) findViewById(R.id.fab_img);
        listViews = new ArrayList<>();

        mFabCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFabItemAnimation || isBackGroundAnimation)
                    return;

                if (isVisible) {
                    isVisible = false;
                    rotateFabIcon(false);
                    toggleFabMenu(false);
                    backGroundViewAnimation(false);
                    if (onFabOpenCloseListener!=null)
                    onFabOpenCloseListener.onClose();
                } else {
                    isVisible = true;
                    backGroundViewAnimation(true);
                    rotateFabIcon(true);
                    toggleFabMenu(true);
                    if (onFabOpenCloseListener!=null)
                    onFabOpenCloseListener.onOpen();
                }

            }
        });
    }

    public void removeBackGroundScreen(boolean removeBackGround){
        this.removeBackGround = removeBackGround;

    }

    /* This method is helpful to add items to fab menu, this method will show images with text.
    *  @param List<ImagesPojo> fab menu items @images and @text
    * */
    public void setUpFabMenu(List<ImagesPojo> imagesPojos) {

        for (int i = 0; i < imagesPojos.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_view, null);

            listViews.add(view);
            mFabMenuContainer.addView(view, 1);

            TextView textView = (TextView) view.findViewById(R.id.txt_name);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            img.setImageResource(imagesPojos.get(i).getDrawable());

            if (null != imagesPojos.get(i).getName()) {
                textView.setText(imagesPojos.get(i).getName());
            } else {
                textView.setVisibility(GONE);
            }

            if (0 != imagesPojos.get(i).getItemTextColor()) {
                textView.setTextColor(imagesPojos.get(i).getItemTextColor());
            }

            addRules(view, i);
        }
    }


    /* This method is helpful to add items to fab menu, this method will show images with background like fab mFabCards with text.
    *  @param List<ImagesPojo> fab menu items @images and @text
    *
    * */
    public void setFabBackgroundToMenuItems(List<ImagesPojo> imagesPojos) {

        listViews.clear();

        for (int i = 0; i < imagesPojos.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.fab_background_item, null);
            listViews.add(view);
            mFabMenuContainer.addView(view);

            TextView textView = (TextView) view.findViewById(R.id.txt_name);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            CardView cardView = (CardView) view.findViewById(R.id.card);
            if (null != imagesPojos.get(i).getName()) {
                textView.setText(imagesPojos.get(i).getName());
            } else {
                textView.setVisibility(GONE);
            }
            if (0 != imagesPojos.get(i).getFabItemBackgroundColor()) {
                cardView.setCardBackgroundColor(imagesPojos.get(i).getFabItemBackgroundColor());
            }

            if (0 != imagesPojos.get(i).getItemTextColor()) {
                textView.setTextColor(imagesPojos.get(i).getItemTextColor());
            }

            img.setImageResource(imagesPojos.get(i).getDrawable());
            addRules(view, i);
        }
    }

    private void addRules(View view, int i) {
        LayoutParams params = (LayoutParams) view.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        if (Build.VERSION.SDK_INT >= 17) params.addRule(RelativeLayout.ALIGN_PARENT_END);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        view.setLayoutParams(params);
        view.setVisibility(GONE);
        view.setTag(i);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabItemClick.onClikFab((int) v.getTag());
                mFabCard.performClick();
            }
        });
    }


    /*
    * Use this method to close fab menu
    * */
    public void closeFabMenu(){
        if (isVisible)
        mFabCard.performClick();
    }

    /*
    * Use this method to open fab menu
    * */

    public void openFabMEnu()
    {
        if (!isVisible)
        mFabCard.performClick();
    }

   /*
    * Use this method to get fab menu status
    * */

    public boolean isFabMenuOpened() {
        return isVisible;
    }

    private void toggleFabMenu(final boolean visible) {


        isFabItemAnimation = true;
        int distance = mFabCard.getHeight();
        for (int i = 0, n = listViews.size(); i < n; ++i) {
            final View v = listViews.get(i);

            if (visible) {
                v.setVisibility(VISIBLE);
            }

            float count = ((i + 1) * distance * -1f) - (distance/8f);

            v.animate().translationY(visible ? count : 0F).alpha(visible ? 1F : 0F).setDuration(200).setInterpolator(new AccelerateDecelerateInterpolator()).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (!visible)
                        v.setVisibility(GONE);
                    isFabItemAnimation = false;
                }
            });
        }
    }


    private void rotateFabIcon(boolean visible) {
        fabIcon.animate().rotation(visible ? 45F : 0F).setDuration(200);
    }

    /* click listener for fab menu items
    * */
    public void setOnFabItemClick(OnFabItemClick onFabItemClick) {
        this.onFabItemClick = onFabItemClick;
    }

    /*
    * listener callback for fab menu open and close
    * */
    public void setOnFabOpenCloseListener(OnFabOpenCloseListener onFabOpenCloseListener) {
        this.onFabOpenCloseListener = onFabOpenCloseListener;
    }

    /*
    * set fab icon @param int -> resource
    *
    * */

    public void setFabIcon(int resource) {
        fabIcon.setImageResource(resource);
    }

    /*
    * set fab icon @param  Drawable
    * */
    public void setFabIcon(Drawable drawable) {
        fabIcon.setImageDrawable(drawable);
    }

    /*
    * set fab icon @param Bitmap
    * */
    public void setFabIcon(Bitmap bitmap) {
        fabIcon.setImageBitmap(bitmap);
    }

    /*
    * set background color @param int color code
    * */
    public void setBackgroundScreenColor(int color) {
        mBackGroundScreen.setBackgroundColor(color);
    }

    /*
    * set background screen transparent float value from 0 t 1
    * */

    public void setBackGroundTransparent(float transparent) {
        mBackGroundScreen.setAlpha(transparent);
    }

    private void backGroundViewAnimation(boolean b) {

        if (removeBackGround){
            mBackGroundScreen.setVisibility(GONE);
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            isBackGroundAnimation = true;
            int w = mBackGroundScreen.getWidth();
            int h = mBackGroundScreen.getHeight();

            int endRadius = (int) Math.hypot(w, h);
            int cx = (int) (mFabCard.getX() + (mFabCard.getWidth() / 2));
            int cy = (int) (mFabCard.getY()) + mFabCard.getHeight() + 56;


            if (b) {
                mBackGroundScreen.setVisibility(View.VISIBLE);
                Animator revealAnimator = ViewAnimationUtils.createCircularReveal(mBackGroundScreen, cx, cy, 0, endRadius);
                revealAnimator.setDuration(300);
                revealAnimator.start();
                revealAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isBackGroundAnimation = false;
                    }
                });

            } else {

                Animator revealAnimator = ViewAnimationUtils.createCircularReveal(mBackGroundScreen, cx, cy, endRadius, 0);

                revealAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isBackGroundAnimation = false;
                        mBackGroundScreen.setVisibility(View.GONE);

                    }
                });
                revealAnimator.setDuration(300);
                revealAnimator.start();
            }

        } else {
            if (b) {
                mBackGroundScreen.setVisibility(VISIBLE);
            } else {
                mBackGroundScreen.setVisibility(GONE);
            }

        }

    }

}
