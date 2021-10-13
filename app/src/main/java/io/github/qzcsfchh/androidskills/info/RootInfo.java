package io.github.qzcsfchh.androidskills.info;

import java.io.File;

/**
 * Check root info of device.
 *
 * @author huanghao
 * @version v1.0
 * @since 2021/7/21 22:28
 * @see <a href='https://juejin.cn/post/6987183668968030221?utm_source=gold_browser_extension'>一文带你了解android检测root方案</a>
 */
public class RootInfo {
    private RootInfo() {
        throw new UnsupportedOperationException();
    }

    public static boolean c() {
        String[] paths = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/","/su/bin/"};
        for (String path : paths) {
            try {
                if (new File(path + "su").exists()) {
                    return true;
                }
            } catch (Exception ignore) { }
        }
        return false;
    }


}
