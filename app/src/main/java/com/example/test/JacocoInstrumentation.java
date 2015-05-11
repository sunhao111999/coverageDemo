package com.example.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

public class JacocoInstrumentation extends Instrumentation implements FinishListener {
    public static String TAG = "JacocoInstrumentation:";
    private static String DEFAULT_COVERAGE_FILE_PATH = "/mnt/sdcard/coverage.ec";

    private final Bundle mResults = new Bundle();

    private Intent mIntent;
    private static final boolean LOGD = true;

    private boolean mCoverage = true;

    private String mCoverageFilePath;


    /**
     * Constructor
     */
    public JacocoInstrumentation() {

    }

    @Override
    public void onCreate(Bundle arguments) {
        Log.d(TAG, "onCreate(" + arguments + ")");
        super.onCreate(arguments);
        DEFAULT_COVERAGE_FILE_PATH = getContext().getFilesDir().getPath().toString() + "/coverage.ec";

        File file = new File(DEFAULT_COVERAGE_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.d(TAG, "异常 : " + e);
                e.printStackTrace();
            }
        }
        if (arguments != null) {
            //mCoverage = getBooleanArgument(arguments, "coverage");
            mCoverageFilePath = arguments.getString("coverageFile");
        }

        mIntent = new Intent(getTargetContext(), InstrumentedActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        start();
    }

    @Override
    public void onStart() {
        if (LOGD)
            Log.d(TAG, "onStart()");
        super.onStart();

        Looper.prepare();
        InstrumentedActivity activity = (InstrumentedActivity) startActivitySync(mIntent);
        activity.setFinishListener(this);
    }

    private boolean getBooleanArgument(Bundle arguments, String tag) {
        String tagString = arguments.getString(tag);
        return tagString != null && Boolean.parseBoolean(tagString);
    }


    private void generateCoverageReport() {
        Log.d(TAG, "generateCoverageReport():" + getCoverageFilePath());
        OutputStream out = null;
        try {
            out = new FileOutputStream(getCoverageFilePath(), false);
            Object agent = Class.forName("org.jacoco.agent.rt.RT")
                    .getMethod("getAgent")
                    .invoke(null);

            out.write((byte[]) agent.getClass().getMethod("getExecutionData", boolean.class)
                    .invoke(agent, false));
        } catch (Exception e) {
            Log.d(TAG, e.toString(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getCoverageFilePath() {
        if (mCoverageFilePath == null) {
            return DEFAULT_COVERAGE_FILE_PATH;
        } else {
            return mCoverageFilePath;
        }
    }

    private boolean setCoverageFilePath(String filePath){
        if(filePath != null && filePath.length() > 0) {
            mCoverageFilePath = filePath;
            return true;
        }
        return false;
    }

    private void reportEmmaError(Exception e) {
        reportEmmaError("", e);
    }

    private void reportEmmaError(String hint, Exception e) {
        String msg = "Failed to generate emma coverage. " + hint;
        Log.e(TAG, msg, e);
        mResults.putString(Instrumentation.REPORT_KEY_STREAMRESULT, "\nError: "
                + msg);
    }

    @Override
    public void onActivityFinished() {
        if (LOGD)
            Log.d(TAG, "onActivityFinished()");
        if (mCoverage) {
            generateCoverageReport();
        }
        finish(Activity.RESULT_OK, mResults);
    }

    @Override
    public void dumpIntermediateCoverage(String filePath){
        // TODO Auto-generated method stub
        if(LOGD){
            Log.d(TAG,"Intermidate Dump Called with file name :"+ filePath);
        }
        if(mCoverage){
            if(!setCoverageFilePath(filePath)){
                if(LOGD){
                    Log.d(TAG,"Unable to set the given file path:"+filePath+" as dump target.");
                }
            }
            generateCoverageReport();
            setCoverageFilePath(DEFAULT_COVERAGE_FILE_PATH);
        }
    }

}
