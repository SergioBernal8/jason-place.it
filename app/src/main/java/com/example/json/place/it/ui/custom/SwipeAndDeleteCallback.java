package com.example.json.place.it.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.json.place.it.R;

public class SwipeAndDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private Drawable icon;
    private final ColorDrawable background;

    public SwipeAndDeleteCallback( Context context) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        background = new ColorDrawable(context.getResources().getColor(R.color.red));
        icon = ContextCompat.getDrawable(context, R.mipmap.ic_delete);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        final View itemView = viewHolder.itemView;

        int backgroundCornerOffset = 20;

        int iconWidth = (int) (icon.getIntrinsicWidth() * 0.9);
        int iconHeight = (int) (icon.getIntrinsicHeight() * 0.5);

        int iconMargin = (itemView.getHeight() - iconHeight) / 2;
        int iconTop = itemView.getTop() + iconMargin;

        int iconBottom = iconTop + iconHeight;

        if (dX > 0) {
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = itemView.getLeft() + iconMargin + (iconWidth / 2);
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());
        } else if (dX < 0) {
            int iconLeft = itemView.getRight() - iconMargin - (iconWidth / 2);
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else
            background.setBounds(0, 0, 0, 0);


        background.draw(c);
        icon.draw(c);
    }
}