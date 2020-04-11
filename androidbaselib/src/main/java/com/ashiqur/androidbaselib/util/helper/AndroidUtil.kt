package com.ashiqur.androidbaselib.util.helper

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.ashiqur.androidbaselib.BuildConfig
import com.ashiqur.androidbaselib.R
import com.ashiqur.androidbaselib.base.BaseApplication
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.math.sqrt

object AndroidUtil {

    fun setTransparentStatusBar(activity: Activity, @ColorInt color: Int) {
        val win = activity.window

        if (Build.VERSION.SDK_INT >= 19) { // 19, 4.4, KITKAT
            win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        if (Build.VERSION.SDK_INT >= 21) { // 21, 5.0, LOLLIPOP
            win.attributes.systemUiVisibility =
                win.attributes.systemUiVisibility or (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            win.statusBarColor = color
        }
    }

    fun hideSoftKeyboard(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun setSystemBarTheme(activity: Activity, isDark: Boolean) {

        if (Build.VERSION.SDK_INT >= 23) { // 23, 6.0, MarshMallow
            val lFlags = activity.window.decorView.systemUiVisibility

            activity.window.decorView.systemUiVisibility =
                if (isDark) lFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                else lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    fun showAutoCompleteSuggestions(et: AutoCompleteTextView, show: Boolean = true) {

        if (et.adapter == null) return

        if (show) {
            if (!et.isPopupShowing) {
                et.showDropDown()
            }
        } else {
            if (et.isPopupShowing) {
                et.dismissDropDown()
            }
        }
    }

    fun copyToClipboard(text: String) {
        val clipboard = BaseApplication.getApplicationContext()
            .getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?

        val clip = ClipData.newPlainText("REFERRAL CODE", text)
        clipboard?.setPrimaryClip(clip)
    }

    fun getBitmap(@DrawableRes drawableRes: Int): Bitmap? {

        val context = BaseApplication.getApplicationContext()

        val drawable = ContextCompat.getDrawable(context, drawableRes) ?: return null
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas()
        canvas.setBitmap(bitmap)

        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)

        return bitmap
    }

    fun isTablet(activity: Activity): Boolean {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)

        val yInches = metrics.heightPixels / metrics.ydpi
        val xInches = metrics.widthPixels / metrics.xdpi
        val diagonalInches = sqrt(xInches * xInches + yInches * yInches)

        return diagonalInches >= 6.5
    }

    fun isValidPhone(phone: CharSequence): Boolean {
        return !TextUtils.isEmpty(phone)
                && Patterns.PHONE.matcher(phone).matches()
    }

    fun isValidEmail(email: CharSequence): Boolean {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

    fun shareText(
        context: Context,
        title: String,
        textToShare: String,
        subject: String = BaseApplication.getApplicationContext().getString(R.string.app_name)
    ) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, textToShare)

        context.startActivity(Intent.createChooser(sharingIntent, title))

    }

    fun logKeyHash() {
        try {
            val info = BaseApplication.getApplicationContext().packageManager
                .getPackageInfo(
                    BaseApplication.getApplicationContext().packageName,
                    PackageManager.GET_SIGNATURES
                )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KeyHash:%s", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            //something
            Log.e("KeyHash:%s", e.message)
        } catch (ex: NoSuchAlgorithmException) {
            //something
            Log.e("KeyHash:%s", ex.message)
        }
    }

    fun setTextFromHtml(textView: TextView, htmlText: String) {
        val spannedText: Spanned
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spannedText = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)

        } else {
            spannedText = Html.fromHtml(htmlText)
        }

        val formattedText = spannedText.replace("<!--.*?-->".toRegex(), "")
        textView.text = formattedText
    }

    fun storeImage(bitmap: Bitmap, context: Context) {

        val path: String = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            bitmap,
            "${BuildConfig.APPLICATION_ID}${Date()}",
            null
        )
        val uri: Uri = Uri.parse(path)

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        context.startActivity(Intent.createChooser(intent, "Share Image"))
    }

    fun takescreenshotOfRootView(v: View): Bitmap {
        v.rootView.isDrawingCacheEnabled = true
        v.rootView.buildDrawingCache(true)
        val b = Bitmap.createBitmap(v.rootView.drawingCache)
        v.rootView.isDrawingCacheEnabled = false
        return b

    }
}