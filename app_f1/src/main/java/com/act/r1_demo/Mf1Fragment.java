package com.act.r1_demo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class Mf1Fragment extends Fragment {

    View mView;
    EditText mEdtSecNo;
    Spinner mSpinKeyTypes;
    EditText mEdtKeyData;
    EditText mEdtBlkNo;
    EditText mEdtBlkData;
    EditText mEdtValue;
    boolean mIsS50 = false;

    public Mf1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_mf1, null);
        mEdtSecNo = (EditText) mView.findViewById(R.id.edtMf1SectorNo);
        mSpinKeyTypes = (Spinner) mView.findViewById(R.id.spinMf1KeyTypes);
        mEdtKeyData = (EditText) mView.findViewById(R.id.edtMf1KeyData);
        mEdtBlkNo = (EditText) mView.findViewById(R.id.edtMf1BlockNo);
        mEdtBlkData = (EditText) mView.findViewById(R.id.edtMf1RawData);
        mEdtValue = (EditText) mView.findViewById(R.id.edtMf1Value);

        mEdtSecNo.setText("1");
        mEdtBlkNo.setText("1");
        mEdtKeyData.setText("FF FF FF FF FF FF");
        mEdtValue.setText("00000001");
/*
        Application.hexkbd.registerEditText(mEdtKeyData);
        Application.hexkbd.registerEditText(mEdtBlkData);
        Application.hexkbd.registerEditText(mEdtValue);
*/
        Vector<Mf1KeyItem> vec = new Vector<Mf1KeyItem>();
        vec.add(new Mf1KeyItem("Key A", true));
        vec.add(new Mf1KeyItem("Key B", false));
        ArrayAdapter<Mf1KeyItem> adapter = new ArrayAdapter<Mf1KeyItem>
                (Application.mainView, R.layout.support_simple_spinner_dropdown_item, vec);
        mSpinKeyTypes.setAdapter(adapter);

/*
        final Button SelectButton = (Button) mView.findViewById(R.id.btnMf1Select);
        SelectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Application.cr.mf1Select();
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        final Button ReadUIDButton = (Button) mView.findViewById(R.id.btnMf1ReadUID);
        ReadUIDButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    byte[] data = Application.cr.mf1GetUID();
                    String text = Utils.bytesToHexString(data);
                    Application.showInfo(text, "UID", mView.getContext());
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        final Button AuthButton = (Button) mView.findViewById(R.id.btnMf1Auth);
        AuthButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    int secNo = getSectorNo();
                    byte[] keyData = getKeyData();
                    boolean isKeyA = ((Mf1KeyItem)mSpinKeyTypes.getSelectedItem()).isKeyA();
                    Application.cr.mf1Auth(secNo, keyData, isKeyA );
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        final Button UpdateButton = (Button) mView.findViewById(R.id.btnMf1Update);
        UpdateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    int secNo = getSectorNo();
                    byte[] keyData = getKeyData();
                    Application.cr.mf1UpdateKeyA(secNo, keyData);
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        final Button ReadButton = (Button) mView.findViewById(R.id.btnMf1Read);
        ReadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    int secNo = getSectorNo();
                    int blkNo = getBlockNo(secNo);
                    byte[] data = Application.cr.mf1Read(secNo, blkNo);
                    String text = Utils.bytesToHexString(data);
                    mEdtBlkData.setText(text);
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        final Button WriteButton = (Button) mView.findViewById(R.id.btnMf1Write);
        WriteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    int secNo = getSectorNo();
                    int blkNo = getBlockNo(secNo);
                    byte[] data = getRawData();
                    Application.cr.mf1Write(secNo, blkNo, data);
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        final Button SetValueButton = (Button) mView.findViewById(R.id.btnMf1SetValue);
        SetValueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    int secNo = getSectorNo();
                    int blkNo = getBlockNo(secNo);
                    int value = getValue();
                    int addr = getAddress(secNo, blkNo);

                    byte[] data = new byte[16];
                    data[8] = data[0] = (byte) (value & 0xFF);
                    data[9] = data[1] = (byte) ((value >> 8) & 0xFF);
                    data[10] = data[2] = (byte) ((value >> 16) & 0xFF);
                    data[11] = data[3] = (byte) ((value >> 24) & 0xFF);
                    data[4] = (byte) ~data[0];
                    data[5] = (byte) ~data[1];
                    data[6] = (byte) ~data[2];
                    data[7] = (byte) ~data[3];
                    data[12] = data[14] = (byte) addr;
                    data[13] = data[15] = (byte) ~addr;

                    Application.cr.mf1Write(secNo, blkNo, data);
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        final Button ReadValueButton = (Button) mView.findViewById(R.id.btnMf1ReadValue);
        ReadValueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    int secNo = getSectorNo();
                    int blkNo = getBlockNo(secNo);
                    int addr = getAddress(secNo, blkNo);
                    byte[] data = Application.cr.mf1Read(secNo, blkNo);

                    if ((data[0] != data[8])
                            || (data[1] != data[9])
                            || (data[2] != data[10])
                            || (data[3] != data[11]))
                        throw new Exception("无效值格式");

                    if (((byte) ~data[0] != data[4])
                            || ((byte) ~data[1] != data[5])
                            || ((byte) ~data[2] != data[6])
                            || ((byte) ~data[3] != data[7]))
                        throw new Exception("无效值格式");

                    if ((data[12] != addr)
                            && (data[14] != addr))
                        throw new Exception("无效值格式");

                    if ((data[13] != (byte) ~addr)
                            && (data[15] != (byte) ~addr))
                        throw new Exception("无效值格式");
                    if (data[12] != (byte)~data[13])
                        throw new Exception("无效值格式");

                    int value = data[0] | (data[1] << 8) | (data[2] << 16) | (data[3] << 24);
                    String text = String.format("%08X", value);
                    mEdtValue.setText(text);
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        final Button IncrementButton = (Button) mView.findViewById(R.id.btnMf1Increment);
        IncrementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    int secNo = getSectorNo();
                    int blkNo = getBlockNo(secNo);
                    int value = getValue();
                    Application.cr.mf1Increment(secNo, blkNo, value);
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        final Button DecrementButton = (Button) mView.findViewById(R.id.btnMf1Decrement);
        DecrementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    int secNo = getSectorNo();
                    int blkNo = getBlockNo(secNo);
                    int value = getValue();
                    Application.cr.mf1Decrement(secNo, blkNo, value);
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });
        */

        return mView;
    }

    private int getSectorNo() throws Exception {
        String text = mEdtSecNo.getText().toString();
        text = text.replaceAll(" ", "");
        if (text.length() == 0)
            throw new Exception("请输入扇区号。");
        int secNo = Integer.parseInt(text);
       if (mIsS50) {
            if (secNo > 15)
                throw new IllegalArgumentException("输入的扇区号大于15。（有效值：0～15)");
        } else {
            if (secNo >= 40)
                throw new IllegalArgumentException("输入的扇区号大于39。（有效值：0～39)");
        }
        return secNo;
    }

    private int getBlockNo(int secNo) throws Exception {
        String text = mEdtBlkNo.getText().toString();
        text = text.replaceAll(" ", "");
        if (text.length() == 0)
            throw new Exception("未输入块号。");
        int blkNo = Integer.parseInt(text);
        if (mIsS50) {
            if (blkNo > 3)
                throw new IllegalArgumentException("输入的块号大于3。（有效值：0～3)");
        } else {
            if (secNo <= 31) {
                if (blkNo > 3)
                    throw new IllegalArgumentException("输入的块号大于3。（有效值：0～3)");
            } else {
                if (blkNo > 15)
                    throw new IllegalArgumentException("输入的块号大于15。（有效值：0～15)");
            }
        }
        return blkNo;
    }

    private int getAddress(int sectorNo, int blockNo) {
        int address = sectorNo * 4 + blockNo;
        if (!mIsS50) {
            if (sectorNo > 31) {
                address = (sectorNo - 31) * 16 + blockNo + 128;
            }
        }
        return address;
    }

    private byte[] getKeyData() throws Exception {
        String text = mEdtKeyData.getText().toString();
        byte[] data = Utils.hexStringToBytes(text);
        if (data == null) {
            throw new Exception("未输入密钥。");
        }
        if (data.length != 6) {
            throw new Exception("输入的密钥应为 6 个字节。");
        }
        return data;
    }

    private byte[] getRawData() throws Exception {
        String text = mEdtBlkData.getText().toString();
        byte[] data = Utils.hexStringToBytes(text);
        if (data == null) {
            throw new Exception("未输入块数据。");
        }
        if (data.length != 16) {
            throw new Exception("输入的块数据应为 16 个字节。");
        }
        return data;
    }

    private int getValue() throws Exception {
        String text = mEdtValue.getText().toString();
        text = text.replaceAll(" ", "");
        if (text.length() == 0)
            throw new Exception("未输入值。");
        return Integer.parseInt(text, 16);
    }
}
