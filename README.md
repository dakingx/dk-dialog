# 简介
基于DialogFragment进行扩展，封装出便于使用的Dialog组件。

[![](https://jitpack.io/v/dakingx/dk-dialog.svg)](https://jitpack.io/#dakingx/dk-dialog)

# 基本用法
## 添加JitPack仓库
在工程项目根目录下的`build.gradle`中添加以下内容：

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

## 添加依赖库

```groovy
dependencies {
	implementation 'com.github.dakingx:dk-dialog:<请查看已发布版本>'
}
```

## 使用DKDialog
创建DKDialog并配置。

```kotlin
private val errorTipDialog by lazy {
    DKDialog().apply {
        fmName = "error_tip_dialog" // fragment名称
        layoutId = R.layout.dialog_message // dialog布局
        fmTheme = R.style.DKDialogStyle // dialog主题
        width = WindowManager.LayoutParams.MATCH_PARENT // dialog宽度
        height = WindowManager.LayoutParams.WRAP_CONTENT // dialog高度
        horizontalMargin = 20 // dialog的水平间距，仅当width为MATCH_PARENT时有效
        verticalMargin = 0 // dialog的垂直间距，仅当height为MATCH_PARENT时有效
        gravity = Gravity.CENTER // dialog的位置，默认为居中
        dimAmount = 0.5F // 灰色背景透明度，默认为0.5F，范围为0~1.0
        transitionAnim = R.style.FadeTransitionAnim // dialog的入场和出场动画
        cancelOutside = true // 点击dialog布局外是否可取消，默认为false
    }
}
```

DKDialog所在的Activity实现DKDialogListener接口。

```kotlin
class MainActivity : AppCompatActivity(), DKDialogListener {
    ...
    override fun convertView(holder: ViewHolder, dialog: DKDialog) {
        if (dialog === errorTipDialog) {
            Log.d(TAG, "errorTipDialog#convertView()")
        }
    }
}
```

显示DKDialog。

```kotlin
errorTipDialog.show(supportFragmentManager)
```

# 自定义Dialog
可继承BaseDialogFragment来实现自定义的Dialog组件，具体可参考custom包下的LoadingDialogFragment.kt和PhotoDialogFragment.kt等。

# custom dialog
## loading dialog
LoadingDialogFragment是一个居中的表示加载中的dialog组件。

直接创建LoadingDialogFragment并显示。

```kotlin
LoadingDialogFragment().show(supportFragmentManager)
```

## photo dialog
PhotoDialogFragment是一个底部弹出的显示相册、拍照和取消等按钮的dialog组件。

首先，创建PhotoDialogFragment实例。

```kotlin
private val photoDialog by lazy {
    PhotoDialogFragment()
}
```

PhotoDialogFragment所在的Activity实现PhotoDialogListener接口。

```kotlin
override fun onPhotoAction(action: PhotoDialogAction, dialog: PhotoDialogFragment) {
    when (action) {
        PhotoDialogAction.Capture -> {
            // 点击了“拍照”
        }
        PhotoDialogAction.Gallery -> {
            // 点击了“相册”
        }
        PhotoDialogAction.Cancel -> {
            // 点击了“取消”
        }
    }
}
```

显示PhotoDialogFragment。

```kotlin
photoDialog.show(supportFragmentManager)
```
