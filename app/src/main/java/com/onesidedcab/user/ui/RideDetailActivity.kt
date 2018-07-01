package com.onesidedcab.user.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.onesidedcab.user.R
import kotlinx.android.synthetic.main.activity_ride_detail.*
import kotlinx.android.synthetic.main.cancel_dialog.*

class RideDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_detail)

        btnCancel.setOnClickListener(View.OnClickListener { openDialog(); })
    }

    private fun openDialog() {
        var dialog = Dialog(this@RideDetailActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.cancel_dialog)

        dialog.btnDialogCancel.setOnClickListener(View.OnClickListener { dialog.dismiss() })

        dialog.show();
    }


    private fun getCityDialog(): Dialog {


        var dialog = Dialog(this@RideDetailActivity)

        var window = dialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.cancel_dialog)

        val params: WindowManager.LayoutParams = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;


        window.setAttributes(params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        val layoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        layoutParams.topMargin = 10 * window.getWindowManager().getDefaultDisplay().getHeight() / 100;
        layoutParams.bottomMargin = 10 * window.getWindowManager().getDefaultDisplay().getHeight() / 100;
        layoutParams.leftMargin = 6 * window.getWindowManager().getDefaultDisplay().getWidth() / 100;
        layoutParams.rightMargin = 6 * window.getWindowManager().getDefaultDisplay().getWidth() / 100;


        var linearDialogue = dialog.findViewById<LinearLayout>(R.id.linar_cancel)

        linearDialogue.setOrientation(LinearLayout.VERTICAL)
        linearDialogue.setLayoutParams(layoutParams)
//
//        var ivClose = dialog.findViewById<ImageView>(R.id.ChativDriverClose);
//        MyTextViewRegular tvTitle = (MyTextViewRegular) dialog.findViewById(R.id.ChattvDriverTitle);
//        final RecyclerView rvList = (RecyclerView) dialog.findViewById(R.id.ChatrvDriverList);
//
//        ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.Chatprogress);
//
        //val llm : LinearLayoutManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.VERTICAL, false);
        // rvList.setLayoutManager(llm);
//BindDriverMessageData(rvList,dialog);

        // tvTitle.setText(R.string.showLocation);
        // getDriverMessageData(progressBar);

//        final List<String> list = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.chatSampleList)));
//        listPosition = -1;

//        btnSelect.setText(getString(R.string.send));
        // tvTitle.setText("Report Trip");

/*        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etChat.setText(messageStatusList.get(listPosition).getMessage_status());


//                chatList.add(list.get(listPosition));
//                chatAdapter.notifyItemInserted(chatList.size() - 1);
//                rvChat.scrollToPosition(chatList.size() - 1);
                dialog.dismiss();
            }
        });*/
//        ivClose.setOnClickListener {
//            // your code to perform when the user clicks on the button
//            dialog.dismiss();
//        }



        return dialog


    }

}
