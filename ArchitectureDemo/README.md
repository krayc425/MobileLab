# 说明

从**可选需求**和**可选架构**中各选其一，对我们的代码进行修改

[项目地址](https://github.com/LittleLittleLittleBoy/ArchitectureDemo)

代码源码为 `src/main` 目录下面的所有内容

### 可选需求

1. **计数器的数字奇偶数颜色不同**
2. 手动修改计数器数字
3. 计数器的数字中包含数字3的时候 Toast 提示出当前含 3 数字是什么

### 可选架构

1. **MVP**
2. MVVM
3. FRP

### 可选

使用 Dagger2 改写代码，把类与类解耦

# 文档

### 1. 运行截图（真机或者虚拟机均可）

<table>
    <td>
        <img src="https://ws1.sinaimg.cn/large/006tNc79ly1frdeiffkx5j30u01hcdgm.jpg"></img>
    </td>
    <td>
        <img src="https://ws1.sinaimg.cn/large/006tNc79ly1frdeietgb9j30u01hcwf9.jpg"></img>
    </td>
</table>

### 2. 修改的部分

`IMVPFragment.java` 文件中添加方法接口

```Java
void changeColor(int color);
```

`MVPFragment.java` 文件中添加方法实现

```Java
@Override
public void changeColor(int color) {
   numText.setTextColor(color);
}
```

`NumberPresenter.java` 文件中增加对 `IMVPFragment` 接口的调用

```Java
public void loadNumber() {
   ...
   new Handler().postDelayed(new Runnable() {
       @Override
       public void run() {
           ...
           mView.changeColor(num % 2 == 0 ? Color.RED : Color.BLUE);
           ...
       }
   }, 1000);
}
```

### 3. 感想

由于 MVP 是从经典的模式 MVC 演变而来，所以它们的基本思想有相通的地方：Controller/Presenter 负责逻辑的处理，Model 提供数据，View 负责显示。

使用 MVP，可以将模型和视图彻底分隔开，并仅提供视图方法的接口达到解耦的效果。并且 View 层的代码逻辑将变得很简单。

![](https://upload-images.jianshu.io/upload_images/1233754-eb5b4bc4fbf757be.png!web?imageMogr2/auto-orient/strip%7CimageView2/2/w/550)




