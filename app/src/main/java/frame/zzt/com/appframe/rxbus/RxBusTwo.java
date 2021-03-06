package frame.zzt.com.appframe.rxbus;


import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


/**
 * RxJava 实现 RxBus 功能的实现
 * <p>
 * // 发送绑定数据
 * RxBusTwo.getInstance().post(eventMsg);
 * <p>
 * // 注册
 * protected void onCreate() {
 * Disposable register = RxBusTwo.getInstance().register( EventMsg.class , new Consumer<EventMsg>() {
 *
 * @Override public void accept(EventMsg eventMsg) throws Exception {
 * }
 * });
 * // 添加绑定
 * RxBusTwo.getInstance().addSubscription(mContext,register);
 * }
 * <p>
 * // 销毁绑定
 * protected void onDestroy() {
 * RxBusTwo.getInstance().unSubscribe( mContext );
 * }
 */

public class RxBusTwo {
    private final Subject<Object> mBus;
    private static volatile RxBusTwo instance;
//    private final FlowableProcessor<Object> mBus;//背压测试

    /**
     * 默认私有化构造函数
     * 当前这个地方没有进行背压
     */
    private RxBusTwo() {
        mBus = PublishSubject.create().toSerialized();
    }

    /**背压测试*/
    /*
    private RxBus(){
        mBus = PublishProcessor.create().toSerialized();
    }
    */

    /**
     * 单例模式
     */
    public static RxBusTwo getInstance() {
        if (instance == null) {
            synchronized (RxBusTwo.class) {
                if (instance == null) {
                    instance = new RxBusTwo();
                }
            }
        }
        return instance;
    }

    /**
     * 将数据添加到订阅
     * 这个地方是再添加订阅的地方。最好创建一个新的类用于数据的传递
     */
    public void post(@NonNull Object obj) {
        if (mBus.hasObservers()) {//判断当前是否已经添加订阅
            mBus.onNext(obj);
        }
    }

    /**
     * 这个是传递集合如果有需要的话你也可以进行更改
     */
    public void post(@NonNull List<Object> obj) {
        if (mBus.hasObservers()) {//判断当前是否已经添加订阅
            mBus.onNext(obj);
        }
    }

    /**
     * 注册，传递tClass的时候最好创建一个封装的类。这对数据的传递作用
     * 新更改仅仅抛出生成类和解析
     */
    public <T> Disposable register(Class<T> tClass, Consumer<T> consumer) {
        return mBus.ofType(tClass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }


    /**
     * 确定接收消息的类型
     * @param aClass
     * @param <T>
     * @return
     * 下面为背压使用方式
     */
/*    public <T> Flowable<T> toFlowable(Class<T> aClass) {
        return mBus.ofType(aClass);
    }*/

    /**
     * 保存订阅后的disposable
     *
     * @param o
     * @param disposable
     */
    private HashMap<String, CompositeDisposable> mSubscriptionMap;

    public void addSubscription(Object o, Disposable disposable) {
        if (mSubscriptionMap == null) {
            mSubscriptionMap = new HashMap<>();
        }
        String key = o.getClass().getName();
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).add(disposable);
        } else {
            //一次性容器,可以持有多个并提供 添加和移除。
            CompositeDisposable disposables = new CompositeDisposable();
            disposables.add(disposable);
            mSubscriptionMap.put(key, disposables);
        }
    }

    /**
     * 取消订阅
     *
     * @param o 这个是你添加到订阅的的对象
     */
    public void unSubscribe(Object o) {
        if (mSubscriptionMap == null) {
            return;
        }

        String key = o.getClass().getName();
        if (!mSubscriptionMap.containsKey(key)) {
            return;
        }
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).dispose();
        }

        mSubscriptionMap.remove(key);
    }
}

