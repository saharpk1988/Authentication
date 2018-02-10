package com.example.me.pinauthentication;

import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.EditText;


public class Dialog {
    /*
    //public Context dialogCtx;
    //public Context intentCtx;
    //public String title;
    //public String message;
    public String negButtonTxt;
    public String posButtonTxtOuter;
    public String posButtonTxtInner;
    public boolean isCancelable;
    public boolean hasPosBtn;
    public boolean hasNegBtn;
    public boolean hasIntent;

    public Dialog(boolean isCancelable, boolean hasPosBtn, boolean hasNegBtn, boolean hasIntent) {
        this.isCancelable = isCancelable;
        this.hasNegBtn = hasNegBtn;
        this.hasPosBtn = hasPosBtn;
        this.hasIntent = hasIntent;
    }

    public void showDialog(Context dialogCtxOuter, String title, String msg, Context dialogCtxInner, final String innerTitle, final String innerMsg,
                           final EditText decoder, final EditText editText, final Context intentCtx) {

        AlertDialog.Builder alertOuter= new AlertDialog.Builder(dialogCtxOuter);
        final AlertDialog.Builder alertInner= new AlertDialog.Builder(dialogCtxInner);
        alertOuter.setTitle(title);
        alertOuter.setMessage(msg);
        alertOuter.setCancelable(this.isCancelable);

        if(this.hasPosBtn && this.hasNegBtn) {
            alertOuter.setPositiveButton(posButtonTxtOuter, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    alertInner.setTitle(innerTitle);
                    alertInner.setMessage(innerMsg);
                    alertInner.setPositiveButton(posButtonTxtOuter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(hasIntent) {

                                Intent intent = new Intent(getApplicationContext(), intentCtx.class);
                                startActivity(intent);
                                finish();
                            } else {
                                decoder.setText("");
                                editText.setText("");
                            }
                        }
                    }).setCancelable(isCancelable).show();
                }
            });
            alertInner.setNegativeButton("", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // delete
                }
            }).setCancelable(isCancelable).show();
        }

        if(this.hasPosBtn && !this.hasNegBtn) {

            alertOuter.setPositiveButton("", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    alertInner.setTitle(innerTitle);
                    alertInner.setMessage(innerMsg);
                    alertInner.setPositiveButton("", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setCancelable(isCancelable).show();
                }
            });
        }
    }



*/

}

