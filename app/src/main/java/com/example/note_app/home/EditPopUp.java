package com.example.note_app.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.note_app.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;



public class EditPopUp extends BottomSheetDialogFragment {


    private NoteModel noteModel;
    public EditPopUp(){

    }

    public EditPopUp(NoteModel noteModel) {
        this.noteModel = noteModel;
    }

    private OnEditClickListener mItemEditClickListener;
    private OnDeleteClickListener mItemDeleteClickListener;


    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_item_edit, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));

        Button editBtn=(Button) contentView.findViewById(R.id.btnEdit);
        Button deleteBtn=(Button) contentView.findViewById(R.id.btnDelete);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
//                Log.d("post","from postEditActivity and edit clicked post: "+post);
                mItemEditClickListener.onItemEditClickListener(noteModel);
            }
        });


         deleteBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 dismiss();
//                 Log.d("post","from postEditActivity and delete clicked post: "+post);
                 mItemDeleteClickListener.onItemtDeleteClickListener(noteModel);
             }
         });


    }

    public interface OnEditClickListener {
        void onItemEditClickListener(NoteModel post);
    }

    public void setItemEditListener(OnEditClickListener onItemClickListener) {
        mItemEditClickListener = onItemClickListener;
    }

    public interface OnDeleteClickListener {
        void onItemtDeleteClickListener(NoteModel post);
    }

    public void setItemDeleteListener(OnDeleteClickListener onItemClickListener) {
        mItemDeleteClickListener = onItemClickListener;
    }



}