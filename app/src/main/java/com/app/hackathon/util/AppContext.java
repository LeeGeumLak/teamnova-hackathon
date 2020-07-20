package com.app.hackathon.util;

import android.app.Application;
import android.os.Environment;

import java.io.File;

/**
 * @author Darcy https://yedaxia.github.io/
 * @version 2018/2/4.
 */

public class AppContext {

    private static final String SD_ROOT_PATH = "MusicNotePlus";

    private static Application sApplication;

    /**
     * @return
     */
    public static File getAudioTempPath(){
        return FileUtils.mkDirs(new File(rootSdPath(), "Audio"));
    }

    /**
     * 音频输出目录
     * @return
     */
    public static File getAudioOutPath(){
        return FileUtils.mkDirs(new File(rootSdPath(), "Output"));

    }

    private static File rootSdPath(){
        return FileUtils.mkDirs(new File(Environment.getExternalStorageDirectory(), SD_ROOT_PATH));
    }

    /**
     * @return
     */
    public static Application getApplication(){
        return sApplication;
    }
}
