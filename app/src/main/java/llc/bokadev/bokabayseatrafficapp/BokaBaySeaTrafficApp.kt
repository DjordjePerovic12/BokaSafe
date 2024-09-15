package llc.bokadev.bokabayseatrafficapp

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BokaBaySeaTrafficApp : Application() {
    override fun onCreate() {
        super.onCreate()

//        if (BuildConfig.DEBUG) {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
                .detectNetwork().penaltyLog().build()
        )

        // Initialize strict mode
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog()
                .penaltyDeath().build()
        )

        Timber.plant(Timber.DebugTree())
//        }
    }
}