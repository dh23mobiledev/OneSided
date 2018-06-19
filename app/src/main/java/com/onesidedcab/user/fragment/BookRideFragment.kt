package com.onesidedcab.user.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onesidedcab.user.R
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.view.Window
import android.view.Gravity
import android.view.WindowManager
import android.widget.*
import com.onesidedcab.user.ui.MainActivity
import com.onesidedcab.user.ui.RideDetailsActivity
import android.support.design.widget.BottomSheetBehavior
import android.support.annotation.NonNull






class BookRideFragment : Fragment(),View.OnClickListener {




    lateinit var linearCity:LinearLayout
    lateinit var layoutBottomSheet:LinearLayout
    lateinit var bookNow:Button

    var sheetBehavior: BottomSheetBehavior<*>? = null
    // TODO: Rename and change types of parameters


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_book_ride, container, false)
        initViews(view)
        return view;
    }

    fun initViews(view: View) {

        linearCity = view.findViewById(R.id.linearCity)
        layoutBottomSheet = view.findViewById(R.id.layoutBottomSheet)
        bookNow = view.findViewById(R.id.bookNow)

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        linearCity.setOnClickListener(this)
        bookNow.setOnClickListener(this)


//
//        sheetBehavior!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                when (newState) {
//                    BottomSheetBehavior.STATE_HIDDEN -> {
//                    }
//                    BottomSheetBehavior.STATE_EXPANDED -> {
//                        btnBottomSheet.setText("Close Sheet")
//                    }
//                    BottomSheetBehavior.STATE_COLLAPSED -> {
//                        btnBottomSheet.setText("Expand Sheet")
//                    }
//                    BottomSheetBehavior.STATE_DRAGGING -> {
//                    }
//                    BottomSheetBehavior.STATE_SETTLING -> {
//                    }
//                }
//            }
//
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//
//            }
//        })



    }



    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.linearCity -> {
                getCityDialog().show()
            }

            R.id.bookNow ->{

                selectCarType()

            }
//            else -> {
//                // else condition
//            }

        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    private fun getCityDialog(): Dialog {


        var dialog = Dialog(activity)

        var window = dialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.city_list_dialog)

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


        var linearDialogue = dialog.findViewById<LinearLayout>(R.id.linearDialogue)

        linearDialogue.setOrientation(LinearLayout.VERTICAL)
        linearDialogue.setLayoutParams(layoutParams)

       var ivClose = dialog.findViewById<ImageView>(R.id.ChativDriverClose);
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
        ivClose.setOnClickListener {
            // your code to perform when the user clicks on the button
            dialog.dismiss();
        }



        return dialog


    }


    private fun selectCarType(){



        //startActivity(Intent(activity, RideDetailsActivity::class.java))
    }

}


