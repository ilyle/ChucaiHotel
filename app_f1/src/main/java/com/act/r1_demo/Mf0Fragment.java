package com.act.r1_demo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class Mf0Fragment extends Fragment {

    View mView;
    EditText mEdtPageAddr;
    EditText mEdtPageData;

    public Mf0Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_mf0, null);
        mEdtPageAddr = (EditText)mView.findViewById(R.id.edtMf0PageAddr);
        mEdtPageData = (EditText)mView.findViewById(R.id.edtMf0PageData);
        mEdtPageAddr.setText("0");
       // Application.hexkbd.registerEditText(mEdtPageData);

       /* final Button SelectButton = (Button) mView.findViewById(R.id.btnMf0Select);
        SelectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Application.cr.mf0Select();
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        final Button ReadUIDButton = (Button) mView.findViewById(R.id.btnMf0ReadUID);
        ReadUIDButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    byte[] data = Application.cr.mf0GetUID();
                    String uid = Utils.bytesToHexString(data);
                    Application.showInfo(uid, "UID", mView.getContext());
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        final Button ReadButton = (Button) mView.findViewById(R.id.btnMf0Read);
        ReadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    int addr = getPageAddress();
                    byte[] data = Application.cr.mf0Read(addr);
                    String text = Utils.bytesToHexString(data);
                    mEdtPageData.setText(text);
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        final Button WriteButton = (Button) mView.findViewById(R.id.btnMf0Write);
        WriteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    int addr = getPageAddress();
                    byte[] data = getPageData();
                    Application.cr.mf0Write(addr, data);
                    mEdtPageData.setText("");
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });
        */

        return mView;
    }

    private int getPageAddress() throws Exception {
        String text = mEdtPageAddr.getText().toString();
        text = text.replaceAll(" ", "");
        if (text.length() == 0)
            throw new Exception("未输入页地址。");
        int addr = Integer.parseInt(text);
        if (addr > 15)
            throw new Exception("输入的页地址无效。（有效值：0～15)");

        return addr;
    }

    private byte[] getPageData() throws Exception {
        String text = mEdtPageData.getText().toString();
        byte[] data = Utils.hexStringToBytes(text);
        if (data == null) {
            throw new Exception("未输入页数据。");
        }
        if (data.length != 4) {
            throw new Exception("输入的数据应为 4 个字节。");
        }
        return data;
    }
}
