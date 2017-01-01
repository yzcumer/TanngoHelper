package com.yuki.android.tanngohelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by fxf on 2016/10/24.
 */
public class TanngoFragment extends Fragment {
    private static final String ARG_TANNGO_ID = "tanngo_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;

    private Tanngo mTanngo;
    private EditText mWord;
    private EditText mMean;
    private Button mDate;
    private CheckBox mSolved;
    private Button mReportButton;

    TextToSpeech ttsObject;
    Button speakButton;
    int result;
    String text;
    private static final int MY_DATA_CHECK_CODE = 1234;

    public static TanngoFragment newInstance(UUID tanngoId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TANNGO_ID, tanngoId);

        TanngoFragment fragment = new TanngoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID tanngoId = (UUID) getArguments().getSerializable(ARG_TANNGO_ID);
        mTanngo = TanngoLab.get(getActivity()).getTanngo(tanngoId);
    }

    @Override
    public void onPause() {
        super.onPause();
        TanngoLab.get(getActivity()).updateTanngo(mTanngo);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tanngo, container, false);

        ttsObject = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = ttsObject.setLanguage(Locale.JAPANESE);
                } else {
                    Toast.makeText(getActivity(),
                            "FEATURE NOT SUPPORTED IN YOUR DEVICE",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mWord = (EditText) v.findViewById(R.id.tanngo_word);
        mWord.setText(mTanngo.getWord());
        mWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTanngo.setWord(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        speakButton = (Button) v.findViewById(R.id.speak_button);
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.speak_button:
                        if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                            Toast.makeText(getActivity(),
                                    "FEATURE NOT SUPPORTED IN YOUR DEVICE",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            text = mWord.getText().toString();
                            ttsObject.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                        }
                        break;
                }

            }
        });

        mMean = (EditText) v.findViewById(R.id.tanngo_mean);
        mMean.setText(mTanngo.getMean());
        mMean.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTanngo.setMean(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDate = (Button) v.findViewById(R.id.tanngo_date);
        updateDate();
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mTanngo.getDate());
                dialog.setTargetFragment(TanngoFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mSolved = (CheckBox) v.findViewById(R.id.tanngo_solved);
        mSolved.setChecked(mTanngo.isSolved());
        mSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTanngo.setSolved(isChecked);
            }
        });

        mReportButton = (Button) v.findViewById(R.id.tanngo_report);
        mReportButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getTanngoReport());
                i.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.tanngo_report_subject));
                i = Intent.createChooser(i, getString(R.string.send_report));
                startActivity(i);
            }
        });

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ttsObject != null) {
            ttsObject.stop();
            ttsObject.shutdown();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mTanngo.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDate.setText(mTanngo.getDate().toString());
    }

    private String getTanngoReport() {
        String solvedString = null;
        if (mTanngo.isSolved()) {
            solvedString = getString(R.string.tanngo_report_solved);
        } else {
            solvedString = getString(R.string.tanngo_report_unsolved);
        }

        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, mTanngo.getDate()).toString();

        String report = getString(R.string.tanngo_report,
                mTanngo.getWord(), dateString, solvedString);

        return report;
    }
}
