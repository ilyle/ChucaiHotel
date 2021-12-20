package com.act.r1_demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Vector;


public class CpuFragment extends Fragment {

    View mView;
    Spinner mSpinCmds = null;
    TextView mTextAtr = null;
    TextView mTextResp = null;

    public CpuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_cpu, null);
        mSpinCmds = (Spinner) mView.findViewById(R.id.spinApduCmds);
        mTextAtr = (TextView) mView.findViewById(R.id.txtATR);
        mTextResp = (TextView) mView.findViewById(R.id.txtApduResp);
        mTextAtr.setText("");

        Vector<APDUCmdItem> vec = Application.getPredCmdItems();
        ArrayAdapter<APDUCmdItem> adapter = new ArrayAdapter<APDUCmdItem>
                (mView.getContext(), R.layout.support_simple_spinner_dropdown_item, vec);
        mSpinCmds.setAdapter(adapter);


        /*final Button SelectAButton = (Button) mView.findViewById(R.id.btnSelectA);
        SelectAButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    byte[] atrData = Application.cr.typeACpuSelect();
                    String strAtr = Utils.bytesToHexString(atrData);
                    mTextAtr.setText("ATR: " + strAtr);
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        final Button SelectBButton = (Button) mView.findViewById(R.id.btnSelectB);
        SelectBButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    byte[] atrData = Application.cr.typeBCpuSelect();
                    String strAtr = Utils.bytesToHexString(atrData);
                    mTextAtr.setText("ATR: " + strAtr);
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        final Button DeselectButton = (Button) mView.findViewById(R.id.btnDeselect);
        DeselectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Application.cr.typeABCpuDeselect();
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        final Button ReadUIDButton = (Button) mView.findViewById(R.id.btnReadUID);
        ReadUIDButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    byte[] data = Application.cr.typeACpuGetUID();
                    String text = Utils.bytesToHexString(data);
                    Application.showInfo(text, "UID", mView.getContext());
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        final Button TransmitButton = (Button) mView.findViewById(R.id.btnTransmit);
        TransmitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    APDUCmdItem cmd = (APDUCmdItem) mSpinCmds.getSelectedItem();
                    byte[] respData = Application.cr.typeABCpuTransmit(cmd.getCmdData());
                    String strResp = Utils.bytesToHexString(respData);
                    mTextResp.setText(strResp);
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });
        */
        return mView;
    }

}
