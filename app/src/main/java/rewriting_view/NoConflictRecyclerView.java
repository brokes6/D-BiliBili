package rewriting_view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NoConflictRecyclerView  extends RecyclerView {
    private float mStartX;
    private float mStartY;


    public NoConflictRecyclerView(Context context) {
        super(context);
    }

    public NoConflictRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NoConflictRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mStartX = ev.getRawX();
                mStartY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endY = ev.getRawY();
                float endX = ev.getRawX();
                float x = endX - mStartX;
                float y = endY - mStartY;
                /* 左右滑动不拦截,上下滑动拦截*/
                if (Math.abs(y) > Math.abs(x))
                {
                    /* 已经在顶部了*/
                    if (y > 0 && !canScrollVertically(-1)){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else if (y < 0 && !canScrollVertically(1)){
                        // 不能再上滑了 ========================
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            default:
                break;

        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        Log.e("Rv正在滑动","-dx ="+dx+"---dy ="+dy);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
