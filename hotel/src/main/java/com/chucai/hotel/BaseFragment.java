//package com.chucai.hotel;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.Spinner;
//
//import com.act.F1_api.F1CardReader;
//import com.act.F1_api.F1CardType;
//
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Vector;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class BaseFragment extends Fragment {
//
//    View mView;
//    Spinner mSpinTtype = null;
//    private List<String> Ttypelist = new ArrayList<String>();
//    private ArrayAdapter<String> Ttypeadapter;
//
//    public BaseFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        mView = inflater.inflate(R.layout.fragment_base , null);
//        mSpinTtype = (Spinner) mView.findViewById(R.id.spinOpType);
//        initiSpinner();
//
//        final Button ResetButton = (Button) mView.findViewById(R.id.btnReset);
//        ResetButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                try {
//                     Application.cr.reset();
//                    Application.showInfo("成功", "发卡机复位", mView.getContext());
//                } catch (Exception e) {
//                    Application.showError(e.getMessage(), mView.getContext());
//                }
//            }
//        });
//
//        final Button EjectToFront = (Button) mView.findViewById(R.id.btnEjectToFront);
//        EjectToFront.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                try {
//                    Application.cr.F1_Dispense((byte)0x30);
//                    Application.showInfo("成功", "发卡到出卡口", mView.getContext());
//                } catch (Exception e) {
//                    Application.showError(e.getMessage(), mView.getContext());
//                }
//
//            }
//        });
//
//
//        final Button ReturnToFront = (Button) mView.findViewById(R.id.btnReturnToFront);
//        ReturnToFront.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                try {
//                    Application.cr.F1_Dispense((byte)0x34);
//                    Application.showInfo("成功", "发卡到前端持卡位", mView.getContext());
//                } catch (Exception e) {
//                    Application.showError(e.getMessage(), mView.getContext());
//                }
//
//            }
//        });
//
//
//        final Button Reader = (Button) mView.findViewById(R.id.btnReader);
//        Reader.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                try {
//                    Application.cr.F1_Dispense((byte)0x36);
//                    Application.showInfo("成功", "发卡到读卡位", mView.getContext());
//                } catch (Exception e) {
//                    Application.showError(e.getMessage(), mView.getContext());
//                }
//
//            }
//        });
//
//        final Button Capture = (Button) mView.findViewById(R.id.btnCapture);
//        Capture.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                try {
//                    Application.cr.F1_Capture();
//                    Application.showInfo("成功", "回收", mView.getContext());
//                } catch (Exception e) {
//                    Application.showError(e.getMessage(), mView.getContext());
//                }
//
//            }
//        });
//
//
//
//        final Button GetStatus = (Button) mView.findViewById(R.id.btnGetStatus);
//        GetStatus.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                try {
//                    String info = "状态信息：\r\n" ,str = null;
//                    int bStatus = Application.cr.F1_GetStatus();
//
//                    if ( ( bStatus & Application.cr.STATUS_CAPTURE_BOX_FULL) == (int) Application.cr.STATUS_CAPTURE_BOX_FULL )
//                    {
//                        info += "回收箱满\r\n";
//
//                    }
//                    if ( ( bStatus & Application.cr.STATUS_DISPENSING) == Application.cr.STATUS_DISPENSING ) {
//                        info += "正在发卡\r\n";
//
//                    }
//                    if ( ( bStatus & Application.cr.STATUS_CAPTURING) ==  Application.cr.STATUS_CAPTURING ) {
//                        info += "正在回收卡\r\n";
//
//                    }
//                    if ( ( bStatus & Application.cr.STATUS_DISPENSE_ERROR) == Application.cr.STATUS_DISPENSE_ERROR ) {
//                        info += "发卡错误\r\n";
//
//                    }
//
//                    if ( ( bStatus & Application.cr.STATUS_CAPTURE_ERROR) == Application.cr.STATUS_CAPTURE_ERROR ) {
//                        info += "回收错误\r\n";
//
//                    }
//
//                    if ( ( bStatus & Application.cr.STATUS_CARD_JAMMED) == Application.cr.STATUS_CARD_JAMMED ) {
//                        info += "卡片堵塞\r\n";
//
//                    }
//                    if ( ( bStatus & Application.cr.STATUS_CARD_INSUFFICIENT) == Application.cr.STATUS_CARD_INSUFFICIENT ) {
//                        info += "卡箱卡少\r\n";
//                    }
//
//                    if ( ( bStatus & Application.cr.STATUS_CARD_BOX_EMPTY) == Application.cr.STATUS_CARD_BOX_EMPTY ) {
//                        info += "卡箱空\r\n";
//
//                    }
//
//                    if ( ( bStatus & Application.cr.STATUS_S3_CARD_IN) == Application.cr.STATUS_S3_CARD_IN ) {
//                        info += "S3:有卡\r\n";
//
//                    }
//                    if ( ( bStatus & Application.cr.STATUS_S2_CARD_IN) == Application.cr.STATUS_S2_CARD_IN ) {
//                        info += "S2:有卡\r\n";
//
//                    }
//                    if ( ( bStatus & Application.cr.STATUS_S1_CARD_IN) == Application.cr.STATUS_S1_CARD_IN ) {
//                        info += "S1:有卡\r\n";
//
//                    }
//                    Application.showInfo(info, "获取卡机状态", mView.getContext());
//                } catch (Exception e) {
//                    Application.showError(e.getMessage(), mView.getContext());
//                }
//
//            }
//        });
//
//
//
//        final Button InsertTpe = (Button) mView.findViewById(R.id.btnInsertTpe);
//        InsertTpe.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                int iSelect = mSpinTtype.getSelectedItemPosition();
//                byte bType = 0x30;
//                try {
//                    if(iSelect == 0) bType = 0x30;
//                    if(iSelect == 1) bType = 0x31;
//                    if(iSelect == 2) bType = 0x32;
//
//                    Application.cr.F1_SetEntryMode(bType);
//                    Application.showInfo("成功", "设置进卡模式", mView.getContext());
//                } catch (Exception e) {
//                    Application.showError(e.getMessage(), mView.getContext());
//                }
//
//            }
//        });
//
//
//
//        return mView;
//    }
//
//    public void initiSpinner() {
//
//        Ttypelist.clear();
//        Ttypelist.add("禁止进卡");
//        Ttypelist.add("允许进卡并回收");
//        Ttypelist.add("允许进卡并移动到读卡位");
//        Ttypeadapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item, Ttypelist);
//        Ttypeadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        mSpinTtype.setAdapter(Ttypeadapter);
//
//    }
//
//
//}
