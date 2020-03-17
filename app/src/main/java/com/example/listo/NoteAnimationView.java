package com.example.listo;

import android.animation.TimeAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

import androidx.core.content.ContextCompat;

public class NoteAnimationView extends View {

    /**
     * Class representing the state of a dollars
     */
    private static class Note {
        private float x;
        private float y;
        private float scale;
        private float alpha;
        private float speed;
    }

    private static final int BASE_SPEED_DP_PER_S = 200;
    private static final int COUNT = 32;
    private static final int SEED = 1337;

    /** The minimum scale of a dollar */
    private static final float SCALE_MIN_PART = 0.45f;
    /** How much of the scale that's based on randomness */
    private static final float SCALE_RANDOM_PART = 0.55f;
    /** How much of the alpha that's based on the scale of the star */
    private static final float ALPHA_SCALE_PART = 0.5f;
    /** How much of the alpha that's based on randomness */
    private static final float ALPHA_RANDOM_PART = 0.5f;

    private final Note[] mNotes = new Note[COUNT];
    private final Random mRnd = new Random(SEED);

    private TimeAnimator mTimeAnimator;
    private Drawable mDrawable;

    private float mBaseSpeed;
    private float mBaseSize;
    private long mCurrentPlayTime;


    public NoteAnimationView(Context context) {
        super(context);
        init();
    }

    /** @see View#View(Context, AttributeSet) */
    public NoteAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /** @see View#View(Context, AttributeSet, int) */
    public NoteAnimationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_music_note);
        mBaseSize = Math.max(mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight()) / 2f;
        mBaseSpeed = BASE_SPEED_DP_PER_S * getResources().getDisplayMetrics().density;
    }


    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);

        // The starting position is dependent on the size of the view,
        // which is why the model is initialized here, when the view is measured.
        for (int i = 0; i < mNotes.length; i++) {
            final Note note = new Note();
            initializeNote(note, width, height);
            mNotes[i] = note;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int viewHeight = getHeight();
        for (final Note note : mNotes) {
            // Ignore the note if it's outside of the view bounds
            final float noteSize = note.scale * mBaseSize;
            if (note.y + noteSize < 0 || note.y - noteSize > viewHeight) {
                continue;
            }

            // Save the current canvas state
            final int save = canvas.save();

            // Move the canvas to the center of the star
            canvas.translate(note.x, note.y);

            // Rotate the canvas based on how far the note has moved ////////////
//            final float progress = (note.y + noteSize) / viewHeight;
//            canvas.rotate(360 * progress);

            // Prepare the size and alpha of the drawable
            final int size = Math.round(noteSize);
            mDrawable.setBounds(-size, -size, size, size);
            mDrawable.setAlpha(Math.round(255 * note.alpha));

            // Draw the note to the canvas
            mDrawable.draw(canvas);

            // Restore the canvas to it's previous position and rotation
            canvas.restoreToCount(save);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mTimeAnimator = new TimeAnimator();
        mTimeAnimator.setTimeListener(new TimeAnimator.TimeListener() {
            @Override
            public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
                if (!isLaidOut()) {
                    // Ignore all calls before the view has been measured and laid out.
                    return;
                }
                updateState(deltaTime);
                invalidate();
            }
        });
        mTimeAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTimeAnimator.cancel();
        mTimeAnimator.setTimeListener(null);
        mTimeAnimator.removeAllListeners();
        mTimeAnimator = null;
    }

    /**
     * Pause the animation if it's running
     */
    public void pause() {
        if (mTimeAnimator != null && mTimeAnimator.isRunning()) {
            // Store the current play time for later.
            mCurrentPlayTime = mTimeAnimator.getCurrentPlayTime();
            mTimeAnimator.pause();
        }
    }

    /**
     * Resume the animation if not already running
     */
    public void resume() {
        if (mTimeAnimator != null && mTimeAnimator.isPaused()) {
            mTimeAnimator.start();
            // Why set the current play time?
            // TimeAnimator uses timestamps internally to determine the delta given
            // in the TimeListener. When resumed, the next delta received will the whole
            // pause duration, which might cause a huge jank in the animation.
            // By setting the current play time, it will pick of where it left off.
            mTimeAnimator.setCurrentPlayTime(mCurrentPlayTime);
        }
    }

    /**
     * Progress the animation by moving the stars based on the elapsed time
     * @param deltaMs time delta since the last frame, in millis
     */
    private void updateState(float deltaMs) {
        // Converting to seconds since PX/S constants are easier to understand
        final float deltaSeconds = deltaMs / 1000f;
        final int viewWidth = getWidth();
        final int viewHeight = getHeight();

        for (final Note note : mNotes) {
            // Move the star based on the elapsed time and it's speed
            note.y -= note.speed * deltaSeconds;

            // If the star is completely outside of the view bounds after
            // updating it's position, recycle it.
            final float size = note.scale * mBaseSize;
            if (note.y + size < 0) {
                initializeNote(note, viewWidth, viewHeight);
            }
        }
    }

    /**
     * Initialize the given star by randomizing it's position, scale and alpha
     * @param note the star to initialize
     * @param viewWidth the view width
     * @param viewHeight the view height
     */
    private void initializeNote(Note note, int viewWidth, int viewHeight) {
        // Set the scale based on a min value and a random multiplier
        note.scale = SCALE_MIN_PART + SCALE_RANDOM_PART * mRnd.nextFloat();

        // Set X to a random value within the width of the view
        note.x = viewWidth * mRnd.nextFloat();

        // Set the Y position
        // Start at the bottom of the view
        note.y = viewHeight;
        // The Y value is in the center of the note, add the size
        // to make sure it starts outside of the view bound
        note.y += note.scale * mBaseSize;
        // Add a random offset to create a small delay before the
        // note appears again.
        note.y += viewHeight * mRnd.nextFloat() / 4f;

        // The alpha is determined by the scale of the note and a random multiplier.
        note.alpha = ALPHA_SCALE_PART * note.scale + ALPHA_RANDOM_PART * mRnd.nextFloat();
        // The bigger and brighter a note is, the faster it moves
        note.speed = mBaseSpeed * note.alpha * note.scale;
    }
}